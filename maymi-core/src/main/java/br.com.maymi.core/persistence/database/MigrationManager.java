package br.com.maymi.core.persistence.database;

import br.com.maymi.core.persistence.migration.Migration;
import br.com.maymi.core.persistence.migration.V1CreatePlayersTable;
import br.com.maymi.core.persistence.migration.V2AddLoginCountToPlayers;
import br.com.maymi.core.persistence.migration.V3CreatePlayerStatisticsTable;
import br.com.maymi.core.persistence.migration.V4BackfillPlayerStatistics;
import br.com.maymi.core.persistence.migration.V5AddPlayTimeColumns;
import br.com.maymi.core.persistence.migration.V6CreateAchievementsTables;
import br.com.maymi.core.persistence.migration.V7CreatePlayerHistoryTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;

public final class MigrationManager {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(MigrationManager.class);

    private static final List<Migration> MIGRATIONS =
            List.of(
                    new V1CreatePlayersTable(),
                    new V2AddLoginCountToPlayers(),
                    new V3CreatePlayerStatisticsTable(),
                    new V4BackfillPlayerStatistics(),
                    new V5AddPlayTimeColumns(),
                    new V6CreateAchievementsTables(),
                    new V7CreatePlayerHistoryTable()
            );

    private MigrationManager() {
    }

    public static void migrate() {

        try (Connection connection = DatabaseManager.getConnection()) {

            createMigrationHistoryTable(connection);

            List<Migration> orderedMigrations = MIGRATIONS.stream()
                    .sorted(Comparator.comparingInt(Migration::version))
                    .toList();

            for (Migration migration : orderedMigrations) {
                applyMigrationIfNecessary(connection, migration);
            }

            LOGGER.info("Migrations do banco validadas com sucesso.");

        } catch (SQLException exception) {
            throw new IllegalStateException(
                    "Não foi possível executar as migrations.",
                    exception
            );
        }
    }

    private static void createMigrationHistoryTable(
            Connection connection
    ) throws SQLException {

        String sql = """
                CREATE TABLE IF NOT EXISTS schema_migrations (
                    version INTEGER PRIMARY KEY NOT NULL,
                    description TEXT NOT NULL,
                    applied_at TEXT NOT NULL
                )
                """;

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        }
    }

    private static void applyMigrationIfNecessary(
            Connection connection,
            Migration migration
    ) throws SQLException {

        if (isMigrationApplied(connection, migration.version())) {
            LOGGER.debug(
                    "Migration V{} já aplicada: {}",
                    migration.version(),
                    migration.description()
            );

            return;
        }

        boolean previousAutoCommit = connection.getAutoCommit();

        try {
            connection.setAutoCommit(false);

            migration.migrate(connection);
            registerMigration(connection, migration);

            connection.commit();

            LOGGER.info(
                    "Migration V{} aplicada: {}",
                    migration.version(),
                    migration.description()
            );

        } catch (SQLException exception) {

            connection.rollback();

            throw new SQLException(
                    "Erro ao executar migration V"
                            + migration.version()
                            + ": "
                            + migration.description(),
                    exception
            );

        } finally {
            connection.setAutoCommit(previousAutoCommit);
        }
    }

    private static boolean isMigrationApplied(
            Connection connection,
            int version
    ) throws SQLException {

        String sql = """
                SELECT 1
                FROM schema_migrations
                WHERE version = ?
                """;

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, version);

            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    private static void registerMigration(
            Connection connection,
            Migration migration
    ) throws SQLException {

        String sql = """
                INSERT INTO schema_migrations (
                    version,
                    description,
                    applied_at
                )
                VALUES (?, ?, ?)
                """;

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, migration.version());
            statement.setString(2, migration.description());
            statement.setString(3, Instant.now().toString());

            statement.executeUpdate();
        }
    }
}