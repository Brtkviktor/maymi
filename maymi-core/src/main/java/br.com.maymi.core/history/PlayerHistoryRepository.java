package br.com.maymi.core.history;

import br.com.maymi.core.persistence.database.DatabaseManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public final class PlayerHistoryRepository {

    public PlayerHistoryEntity insert(
            PlayerHistoryEntity history
    ) {

        Objects.requireNonNull(
                history,
                "Histórico não pode ser nulo."
        );

        String sql = """
                INSERT INTO player_history (
                    player_uuid,
                    history_type,
                    title,
                    description,
                    metadata,
                    created_at
                )
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (
                var connection =
                        DatabaseManager.getConnection();

                var statement =
                        connection.prepareStatement(
                                sql,
                                java.sql.Statement.RETURN_GENERATED_KEYS
                        )
        ) {

            statement.setString(
                    1,
                    history.playerUuid().toString()
            );

            statement.setString(
                    2,
                    history.type().name()
            );

            statement.setString(
                    3,
                    history.title()
            );

            statement.setString(
                    4,
                    history.description()
            );

            statement.setString(
                    5,
                    history.metadata()
            );

            statement.setString(
                    6,
                    history.createdAt().toString()
            );

            statement.executeUpdate();

            try (
                    ResultSet generatedKeys =
                            statement.getGeneratedKeys()
            ) {

                if (!generatedKeys.next()) {

                    throw new IllegalStateException(
                            "O histórico foi salvo, mas o ID não foi retornado."
                    );
                }

                long id =
                        generatedKeys.getLong(
                                1
                        );

                return new PlayerHistoryEntity(
                        id,
                        history.playerUuid(),
                        history.type(),
                        history.title(),
                        history.description(),
                        history.metadata(),
                        history.createdAt()
                );
            }

        } catch (SQLException exception) {

            throw new IllegalStateException(
                    "Não foi possível registrar o histórico do jogador "
                            + history.playerUuid(),
                    exception
            );
        }
    }

    public List<PlayerHistoryEntity> findByPlayerUuid(
            UUID playerUuid,
            int limit
    ) {

        Objects.requireNonNull(
                playerUuid,
                "UUID do jogador não pode ser nulo."
        );

        validateLimit(
                limit
        );

        String sql = """
                SELECT
                    id,
                    player_uuid,
                    history_type,
                    title,
                    description,
                    metadata,
                    created_at
                FROM player_history
                WHERE player_uuid = ?
                ORDER BY created_at DESC, id DESC
                LIMIT ?
                """;

        List<PlayerHistoryEntity> history =
                new ArrayList<>();

        try (
                var connection =
                        DatabaseManager.getConnection();

                var statement =
                        connection.prepareStatement(
                                sql
                        )
        ) {

            statement.setString(
                    1,
                    playerUuid.toString()
            );

            statement.setInt(
                    2,
                    limit
            );

            try (
                    ResultSet resultSet =
                            statement.executeQuery()
            ) {

                while (resultSet.next()) {

                    history.add(
                            mapHistory(
                                    resultSet
                            )
                    );
                }
            }

            return List.copyOf(
                    history
            );

        } catch (SQLException exception) {

            throw new IllegalStateException(
                    "Não foi possível consultar o histórico do jogador "
                            + playerUuid,
                    exception
            );
        }
    }

    public List<PlayerHistoryEntity> findRecent(
            int limit
    ) {

        validateLimit(
                limit
        );

        String sql = """
                SELECT
                    id,
                    player_uuid,
                    history_type,
                    title,
                    description,
                    metadata,
                    created_at
                FROM player_history
                ORDER BY created_at DESC, id DESC
                LIMIT ?
                """;

        List<PlayerHistoryEntity> history =
                new ArrayList<>();

        try (
                var connection =
                        DatabaseManager.getConnection();

                var statement =
                        connection.prepareStatement(
                                sql
                        )
        ) {

            statement.setInt(
                    1,
                    limit
            );

            try (
                    ResultSet resultSet =
                            statement.executeQuery()
            ) {

                while (resultSet.next()) {

                    history.add(
                            mapHistory(
                                    resultSet
                            )
                    );
                }
            }

            return List.copyOf(
                    history
            );

        } catch (SQLException exception) {

            throw new IllegalStateException(
                    "Não foi possível consultar o histórico recente.",
                    exception
            );
        }
    }

    public List<PlayerHistoryEntity> findByType(
            HistoryType type,
            int limit
    ) {

        Objects.requireNonNull(
                type,
                "Tipo do histórico não pode ser nulo."
        );

        validateLimit(
                limit
        );

        String sql = """
                SELECT
                    id,
                    player_uuid,
                    history_type,
                    title,
                    description,
                    metadata,
                    created_at
                FROM player_history
                WHERE history_type = ?
                ORDER BY created_at DESC, id DESC
                LIMIT ?
                """;

        List<PlayerHistoryEntity> history =
                new ArrayList<>();

        try (
                var connection =
                        DatabaseManager.getConnection();

                var statement =
                        connection.prepareStatement(
                                sql
                        )
        ) {

            statement.setString(
                    1,
                    type.name()
            );

            statement.setInt(
                    2,
                    limit
            );

            try (
                    ResultSet resultSet =
                            statement.executeQuery()
            ) {

                while (resultSet.next()) {

                    history.add(
                            mapHistory(
                                    resultSet
                            )
                    );
                }
            }

            return List.copyOf(
                    history
            );

        } catch (SQLException exception) {

            throw new IllegalStateException(
                    "Não foi possível consultar o histórico do tipo "
                            + type,
                    exception
            );
        }
    }

    public long count() {

        String sql = """
                SELECT COUNT(*) AS total
                FROM player_history
                """;

        try (
                var connection =
                        DatabaseManager.getConnection();

                var statement =
                        connection.prepareStatement(
                                sql
                        );

                ResultSet resultSet =
                        statement.executeQuery()
        ) {

            if (!resultSet.next()) {
                return 0;
            }

            return resultSet.getLong(
                    "total"
            );

        } catch (SQLException exception) {

            throw new IllegalStateException(
                    "Não foi possível contar os registros de histórico.",
                    exception
            );
        }
    }

    private PlayerHistoryEntity mapHistory(
            ResultSet resultSet
    ) throws SQLException {

        return new PlayerHistoryEntity(
                resultSet.getLong(
                        "id"
                ),
                UUID.fromString(
                        resultSet.getString(
                                "player_uuid"
                        )
                ),
                HistoryType.valueOf(
                        resultSet.getString(
                                "history_type"
                        )
                ),
                resultSet.getString(
                        "title"
                ),
                resultSet.getString(
                        "description"
                ),
                resultSet.getString(
                        "metadata"
                ),
                Instant.parse(
                        resultSet.getString(
                                "created_at"
                        )
                )
        );
    }

    private void validateLimit(
            int limit
    ) {

        if (limit <= 0) {

            throw new IllegalArgumentException(
                    "Limite deve ser maior que zero."
            );
        }
    }
}