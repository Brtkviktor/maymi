package br.com.maymi.core.mod.create.persistence;

import br.com.maymi.core.mod.create.CreateBlockAction;
import br.com.maymi.core.mod.create.CreateBlockCategory;
import br.com.maymi.core.persistence.database.DatabaseManager;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public final class CreateStatisticsRepository {

    public void registerAction(
            UUID playerUuid,
            CreateBlockCategory category,
            CreateBlockAction action,
            Instant occurredAt
    ) {

        Objects.requireNonNull(
                playerUuid,
                "UUID do jogador não pode ser nulo."
        );

        Objects.requireNonNull(
                category,
                "Categoria não pode ser nula."
        );

        Objects.requireNonNull(
                action,
                "Ação não pode ser nula."
        );

        Objects.requireNonNull(
                occurredAt,
                "Data do evento não pode ser nula."
        );

        ensurePlayerStatistics(
                playerUuid,
                occurredAt
        );

        ensureCategoryStatistics(
                playerUuid,
                category,
                occurredAt
        );

        updatePlayerStatistics(
                playerUuid,
                action,
                occurredAt
        );

        updateCategoryStatistics(
                playerUuid,
                category,
                action,
                occurredAt
        );
    }

    private void ensurePlayerStatistics(
            UUID playerUuid,
            Instant timestamp
    ) {

        String sql = """
                INSERT OR IGNORE INTO create_player_statistics (
                    player_uuid,
                    total_actions,
                    blocks_placed,
                    blocks_broken,
                    created_at,
                    updated_at
                )
                VALUES (?, 0, 0, 0, ?, ?)
                """;

        try (
                var connection =
                        DatabaseManager.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1,
                    playerUuid.toString()
            );

            statement.setString(
                    2,
                    timestamp.toString()
            );

            statement.setString(
                    3,
                    timestamp.toString()
            );

            statement.executeUpdate();

        } catch (SQLException exception) {

            throw new IllegalStateException(
                    "Não foi possível criar estatísticas Create para o jogador "
                            + playerUuid,
                    exception
            );
        }
    }

    private void ensureCategoryStatistics(
            UUID playerUuid,
            CreateBlockCategory category,
            Instant timestamp
    ) {

        String sql = """
                INSERT OR IGNORE INTO create_category_statistics (
                    player_uuid,
                    category,
                    blocks_placed,
                    blocks_broken,
                    updated_at
                )
                VALUES (?, ?, 0, 0, ?)
                """;

        try (
                var connection =
                        DatabaseManager.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1,
                    playerUuid.toString()
            );

            statement.setString(
                    2,
                    category.name()
            );

            statement.setString(
                    3,
                    timestamp.toString()
            );

            statement.executeUpdate();

        } catch (SQLException exception) {

            throw new IllegalStateException(
                    "Não foi possível criar estatísticas da categoria "
                            + category
                            + " para o jogador "
                            + playerUuid,
                    exception
            );
        }
    }

    private void updatePlayerStatistics(
            UUID playerUuid,
            CreateBlockAction action,
            Instant timestamp
    ) {

        String sql;

        if (action == CreateBlockAction.PLACE) {

            sql = """
                    UPDATE create_player_statistics
                    SET
                        total_actions = total_actions + 1,
                        blocks_placed = blocks_placed + 1,
                        updated_at = ?
                    WHERE player_uuid = ?
                    """;

        } else {

            sql = """
                    UPDATE create_player_statistics
                    SET
                        total_actions = total_actions + 1,
                        blocks_broken = blocks_broken + 1,
                        updated_at = ?
                    WHERE player_uuid = ?
                    """;
        }

        try (
                var connection =
                        DatabaseManager.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1,
                    timestamp.toString()
            );

            statement.setString(
                    2,
                    playerUuid.toString()
            );

            statement.executeUpdate();

        } catch (SQLException exception) {

            throw new IllegalStateException(
                    "Não foi possível atualizar estatísticas gerais do Create para "
                            + playerUuid,
                    exception
            );
        }
    }

    private void updateCategoryStatistics(
            UUID playerUuid,
            CreateBlockCategory category,
            CreateBlockAction action,
            Instant timestamp
    ) {

        String sql;

        if (action == CreateBlockAction.PLACE) {

            sql = """
                    UPDATE create_category_statistics
                    SET
                        blocks_placed = blocks_placed + 1,
                        updated_at = ?
                    WHERE player_uuid = ?
                      AND category = ?
                    """;

        } else {

            sql = """
                    UPDATE create_category_statistics
                    SET
                        blocks_broken = blocks_broken + 1,
                        updated_at = ?
                    WHERE player_uuid = ?
                      AND category = ?
                    """;
        }

        try (
                var connection =
                        DatabaseManager.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1,
                    timestamp.toString()
            );

            statement.setString(
                    2,
                    playerUuid.toString()
            );

            statement.setString(
                    3,
                    category.name()
            );

            statement.executeUpdate();

        } catch (SQLException exception) {

            throw new IllegalStateException(
                    "Não foi possível atualizar estatísticas da categoria "
                            + category
                            + " para "
                            + playerUuid,
                    exception
            );
        }
    }
}