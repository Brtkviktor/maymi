package br.com.maymi.core.persistence.repository;

import br.com.maymi.core.persistence.database.DatabaseManager;
import br.com.maymi.core.persistence.entity.PlayerStatisticsEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public final class PlayerStatisticsRepository {

    public Optional<PlayerStatisticsEntity> findByPlayerUuid(
            UUID playerUuid
    ) {

        Objects.requireNonNull(
                playerUuid,
                "UUID não pode ser nulo."
        );

        String sql = """
                SELECT
                    player_uuid,
                    maymi_xp,
                    level,
                    deaths,
                    mob_kills,
                    blocks_placed,
                    blocks_broken,
                    play_time_seconds,
                    created_at,
                    updated_at,
                    session_start_at
                FROM player_statistics
                WHERE player_uuid = ?
                """;

        try (
                var connection =
                        DatabaseManager.getConnection();

                var statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1,
                    playerUuid.toString()
            );

            try (
                    ResultSet resultSet =
                            statement.executeQuery()
            ) {

                if (!resultSet.next()) {
                    return Optional.empty();
                }

                return Optional.of(
                        mapStatistics(resultSet)
                );
            }

        } catch (SQLException exception) {

            throw new IllegalStateException(
                    "Não foi possível consultar as estatísticas do jogador "
                            + playerUuid,
                    exception
            );
        }
    }

    public void insertDefault(
            UUID playerUuid,
            Instant timestamp
    ) {

        Objects.requireNonNull(
                playerUuid,
                "UUID não pode ser nulo."
        );

        Objects.requireNonNull(
                timestamp,
                "Timestamp não pode ser nulo."
        );

        String sql = """
                INSERT INTO player_statistics (
                    player_uuid,
                    maymi_xp,
                    level,
                    deaths,
                    mob_kills,
                    blocks_placed,
                    blocks_broken,
                    play_time_seconds,
                    session_start_at,
                    created_at,
                    updated_at
                )
                VALUES (?, 0, 1, 0, 0, 0, 0, 0, NULL, ?, ?)
                """;

        try (
                var connection =
                        DatabaseManager.getConnection();

                var statement =
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
                    "Não foi possível criar as estatísticas do jogador "
                            + playerUuid,
                    exception
            );
        }
    }

    public boolean incrementDeaths(
            UUID playerUuid,
            Instant timestamp
    ) {

        return incrementStatistic(
                playerUuid,
                timestamp,
                "deaths",
                "Não foi possível incrementar as mortes do jogador "
        );
    }

    public boolean incrementMobKills(
            UUID playerUuid,
            Instant timestamp
    ) {

        return incrementStatistic(
                playerUuid,
                timestamp,
                "mob_kills",
                "Não foi possível incrementar as kills do jogador "
        );
    }

    public boolean incrementBlocksBroken(
            UUID playerUuid,
            Instant timestamp
    ) {

        return incrementStatistic(
                playerUuid,
                timestamp,
                "blocks_broken",
                "Não foi possível incrementar blocos quebrados do jogador "
        );
    }

    public boolean incrementBlocksPlaced(
            UUID playerUuid,
            Instant timestamp
    ) {

        return incrementStatistic(
                playerUuid,
                timestamp,
                "blocks_placed",
                "Não foi possível incrementar blocos colocados do jogador "
        );
    }

    private boolean incrementStatistic(
            UUID playerUuid,
            Instant timestamp,
            String column,
            String errorMessage
    ) {

        Objects.requireNonNull(
                playerUuid,
                "UUID não pode ser nulo."
        );

        Objects.requireNonNull(
                timestamp,
                "Timestamp não pode ser nulo."
        );

        /*
         * O nome da coluna não vem de entrada externa.
         * Ele é fornecido apenas pelos métodos internos acima.
         */
        String sql = """
                UPDATE player_statistics
                SET
                    %s = %s + 1,
                    updated_at = ?
                WHERE player_uuid = ?
                """.formatted(
                column,
                column
        );

        try (
                var connection =
                        DatabaseManager.getConnection();

                var statement =
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

            return statement.executeUpdate() > 0;

        } catch (SQLException exception) {

            throw new IllegalStateException(
                    errorMessage + playerUuid,
                    exception
            );
        }
    }

    public boolean updateXpAndLevel(
            UUID playerUuid,
            long xp,
            int level,
            Instant timestamp
    ) {

        Objects.requireNonNull(
                playerUuid,
                "UUID não pode ser nulo."
        );

        Objects.requireNonNull(
                timestamp,
                "Timestamp não pode ser nulo."
        );

        String sql = """
                UPDATE player_statistics
                SET
                    maymi_xp = ?,
                    level = ?,
                    updated_at = ?
                WHERE player_uuid = ?
                """;

        try (
                var connection =
                        DatabaseManager.getConnection();

                var statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setLong(
                    1,
                    xp
            );

            statement.setInt(
                    2,
                    level
            );

            statement.setString(
                    3,
                    timestamp.toString()
            );

            statement.setString(
                    4,
                    playerUuid.toString()
            );

            return statement.executeUpdate() > 0;

        } catch (SQLException exception) {

            throw new IllegalStateException(
                    "Não foi possível atualizar o XP do jogador "
                            + playerUuid,
                    exception
            );
        }
    }

    public boolean startSession(
            UUID playerUuid,
            Instant startedAt
    ) {

        Objects.requireNonNull(
                playerUuid,
                "UUID não pode ser nulo."
        );

        Objects.requireNonNull(
                startedAt,
                "Horário inicial não pode ser nulo."
        );

        String sql = """
                UPDATE player_statistics
                SET
                    session_start_at = ?,
                    updated_at = ?
                WHERE player_uuid = ?
                """;

        try (
                var connection =
                        DatabaseManager.getConnection();

                var statement =
                        connection.prepareStatement(sql)
        ) {

            String timestamp =
                    startedAt.toString();

            statement.setString(
                    1,
                    timestamp
            );

            statement.setString(
                    2,
                    timestamp
            );

            statement.setString(
                    3,
                    playerUuid.toString()
            );

            return statement.executeUpdate() > 0;

        } catch (SQLException exception) {

            throw new IllegalStateException(
                    "Não foi possível iniciar a sessão do jogador "
                            + playerUuid,
                    exception
            );
        }
    }

    public long finishSession(
            UUID playerUuid,
            Instant endedAt
    ) {

        Objects.requireNonNull(
                playerUuid,
                "UUID não pode ser nulo."
        );

        Objects.requireNonNull(
                endedAt,
                "Horário final não pode ser nulo."
        );

        String selectSql = """
                SELECT session_start_at
                FROM player_statistics
                WHERE player_uuid = ?
                """;

        String updateSql = """
                UPDATE player_statistics
                SET
                    play_time_seconds = play_time_seconds + ?,
                    session_start_at = NULL,
                    updated_at = ?
                WHERE player_uuid = ?
                """;

        try (
                var connection =
                        DatabaseManager.getConnection()
        ) {

            connection.setAutoCommit(false);

            try {

                String sessionStartValue;

                try (
                        var selectStatement =
                                connection.prepareStatement(
                                        selectSql
                                )
                ) {

                    selectStatement.setString(
                            1,
                            playerUuid.toString()
                    );

                    try (
                            ResultSet resultSet =
                                    selectStatement.executeQuery()
                    ) {

                        if (!resultSet.next()) {

                            throw new IllegalStateException(
                                    "Estatísticas não encontradas para o jogador "
                                            + playerUuid
                            );
                        }

                        sessionStartValue =
                                resultSet.getString(
                                        "session_start_at"
                                );
                    }
                }

                /*
                 * Se não há sessão aberta, não existe tempo para somar.
                 */
                if (
                        sessionStartValue == null
                                || sessionStartValue.isBlank()
                ) {

                    connection.rollback();

                    return 0;
                }

                Instant sessionStartedAt =
                        Instant.parse(
                                sessionStartValue
                        );

                long sessionSeconds =
                        Math.max(
                                0,
                                Duration.between(
                                        sessionStartedAt,
                                        endedAt
                                ).getSeconds()
                        );

                try (
                        var updateStatement =
                                connection.prepareStatement(
                                        updateSql
                                )
                ) {

                    updateStatement.setLong(
                            1,
                            sessionSeconds
                    );

                    updateStatement.setString(
                            2,
                            endedAt.toString()
                    );

                    updateStatement.setString(
                            3,
                            playerUuid.toString()
                    );

                    int affectedRows =
                            updateStatement.executeUpdate();

                    if (affectedRows == 0) {

                        throw new IllegalStateException(
                                "Nenhuma sessão foi atualizada para o jogador "
                                        + playerUuid
                        );
                    }
                }

                connection.commit();

                return sessionSeconds;

            } catch (SQLException | RuntimeException exception) {

                try {
                    connection.rollback();

                } catch (SQLException rollbackException) {

                    exception.addSuppressed(
                            rollbackException
                    );
                }

                throw exception;

            } finally {

                try {
                    connection.setAutoCommit(true);

                } catch (SQLException ignored) {
                    // A conexão será fechada pelo try-with-resources.
                }
            }

        } catch (SQLException exception) {

            throw new IllegalStateException(
                    "Não foi possível finalizar a sessão do jogador "
                            + playerUuid,
                    exception
            );
        }
    }

    private PlayerStatisticsEntity mapStatistics(
            ResultSet resultSet
    ) throws SQLException {

        String sessionStartValue =
                resultSet.getString(
                        "session_start_at"
                );

        Instant sessionStartAt =
                sessionStartValue == null
                        || sessionStartValue.isBlank()
                        ? null
                        : Instant.parse(
                        sessionStartValue
                );

        return new PlayerStatisticsEntity(
                UUID.fromString(
                        resultSet.getString(
                                "player_uuid"
                        )
                ),
                resultSet.getLong(
                        "maymi_xp"
                ),
                resultSet.getInt(
                        "level"
                ),
                resultSet.getLong(
                        "deaths"
                ),
                resultSet.getLong(
                        "mob_kills"
                ),
                resultSet.getLong(
                        "blocks_placed"
                ),
                resultSet.getLong(
                        "blocks_broken"
                ),
                resultSet.getLong(
                        "play_time_seconds"
                ),
                Instant.parse(
                        resultSet.getString(
                                "created_at"
                        )
                ),
                Instant.parse(
                        resultSet.getString(
                                "updated_at"
                        )
                ),
                sessionStartAt
        );
    }
}