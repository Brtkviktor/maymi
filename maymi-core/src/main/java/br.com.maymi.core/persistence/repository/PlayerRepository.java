package br.com.maymi.core.persistence.repository;

import br.com.maymi.core.persistence.database.DatabaseManager;
import br.com.maymi.core.persistence.entity.PlayerEntity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public final class PlayerRepository {

    public Optional<PlayerEntity> findByUuid(
            UUID uuid
    ) {

        String sql = """
                SELECT
                    uuid,
                    nickname,
                    first_join_at,
                    last_seen_at,
                    login_count,
                    created_at,
                    updated_at
                FROM players
                WHERE uuid = ?
                """;

        try (
                var connection =
                        DatabaseManager.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1,
                    uuid.toString()
            );

            try (
                    ResultSet resultSet =
                            statement.executeQuery()
            ) {

                if (!resultSet.next()) {
                    return Optional.empty();
                }

                return Optional.of(
                        mapPlayer(resultSet)
                );
            }

        } catch (SQLException exception) {

            throw new IllegalStateException(
                    "Não foi possível consultar o jogador "
                            + uuid,
                    exception
            );
        }
    }

    public void insert(
            PlayerEntity player
    ) {

        String sql = """
                INSERT INTO players (
                    uuid,
                    nickname,
                    first_join_at,
                    last_seen_at,
                    login_count,
                    created_at,
                    updated_at
                )
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        try (
                var connection =
                        DatabaseManager.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1,
                    player.getUuid().toString()
            );

            statement.setString(
                    2,
                    player.getNickname()
            );

            statement.setString(
                    3,
                    player.getFirstJoinAt().toString()
            );

            statement.setString(
                    4,
                    player.getLastSeenAt().toString()
            );

            statement.setInt(
                    5,
                    player.getLoginCount()
            );

            statement.setString(
                    6,
                    player.getCreatedAt().toString()
            );

            statement.setString(
                    7,
                    player.getUpdatedAt().toString()
            );

            statement.executeUpdate();

        } catch (SQLException exception) {

            throw new IllegalStateException(
                    "Não foi possível cadastrar o jogador "
                            + player.getNickname(),
                    exception
            );
        }
    }

    public boolean registerJoin(
            UUID uuid,
            String nickname,
            Instant joinedAt
    ) {

        String sql = """
                UPDATE players
                SET
                    nickname = ?,
                    last_seen_at = ?,
                    login_count = login_count + 1,
                    updated_at = ?
                WHERE uuid = ?
                """;

        try (
                var connection =
                        DatabaseManager.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            String timestamp =
                    joinedAt.toString();

            statement.setString(
                    1,
                    nickname
            );

            statement.setString(
                    2,
                    timestamp
            );

            statement.setString(
                    3,
                    timestamp
            );

            statement.setString(
                    4,
                    uuid.toString()
            );

            int affectedRows =
                    statement.executeUpdate();

            return affectedRows > 0;

        } catch (SQLException exception) {

            throw new IllegalStateException(
                    "Não foi possível registrar a entrada do jogador "
                            + uuid,
                    exception
            );
        }
    }

    public boolean registerQuit(
            UUID uuid,
            String nickname,
            Instant quitAt
    ) {

        String sql = """
                UPDATE players
                SET
                    nickname = ?,
                    last_seen_at = ?,
                    updated_at = ?
                WHERE uuid = ?
                """;

        try (
                var connection =
                        DatabaseManager.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            String timestamp =
                    quitAt.toString();

            statement.setString(
                    1,
                    nickname
            );

            statement.setString(
                    2,
                    timestamp
            );

            statement.setString(
                    3,
                    timestamp
            );

            statement.setString(
                    4,
                    uuid.toString()
            );

            int affectedRows =
                    statement.executeUpdate();

            return affectedRows > 0;

        } catch (SQLException exception) {

            throw new IllegalStateException(
                    "Não foi possível registrar a saída do jogador "
                            + uuid,
                    exception
            );
        }
    }

    private PlayerEntity mapPlayer(
            ResultSet resultSet
    ) throws SQLException {

        return new PlayerEntity(
                UUID.fromString(
                        resultSet.getString("uuid")
                ),

                resultSet.getString(
                        "nickname"
                ),

                Instant.parse(
                        resultSet.getString(
                                "first_join_at"
                        )
                ),

                Instant.parse(
                        resultSet.getString(
                                "last_seen_at"
                        )
                ),

                resultSet.getInt(
                        "login_count"
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
                )
        );
    }
}