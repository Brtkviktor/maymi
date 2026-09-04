package br.com.maymi.core.achievement;

import br.com.maymi.core.persistence.database.DatabaseManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public final class PlayerAchievementRepository {

    public boolean hasUnlocked(
            UUID playerUuid,
            String achievementId
    ) {

        validate(
                playerUuid,
                achievementId
        );

        String sql = """
                SELECT 1
                FROM player_achievements
                WHERE
                    player_uuid = ?
                    AND achievement_id = ?
                LIMIT 1
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
                    achievementId
            );

            try (
                    ResultSet resultSet =
                            statement.executeQuery()
            ) {

                return resultSet.next();
            }

        } catch (SQLException exception) {

            throw new IllegalStateException(
                    "Não foi possível verificar a conquista "
                            + achievementId
                            + " do jogador "
                            + playerUuid,
                    exception
            );
        }
    }

    /**
     * Tenta registrar uma conquista.
     *
     * @return true quando foi desbloqueada agora;
     * false quando o jogador já possuía a conquista.
     */
    public boolean unlock(
            UUID playerUuid,
            String achievementId,
            Instant unlockedAt
    ) {

        validate(
                playerUuid,
                achievementId
        );

        Objects.requireNonNull(
                unlockedAt,
                "Data de desbloqueio não pode ser nula."
        );

        String sql = """
                INSERT OR IGNORE INTO player_achievements (
                    player_uuid,
                    achievement_id,
                    unlocked_at
                )
                VALUES (?, ?, ?)
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
                    achievementId
            );

            statement.setString(
                    3,
                    unlockedAt.toString()
            );

            return statement.executeUpdate() > 0;

        } catch (SQLException exception) {

            throw new IllegalStateException(
                    "Não foi possível desbloquear a conquista "
                            + achievementId
                            + " para o jogador "
                            + playerUuid,
                    exception
            );
        }
    }

    public List<PlayerAchievementEntity> findByPlayerUuid(
            UUID playerUuid
    ) {

        Objects.requireNonNull(
                playerUuid,
                "UUID do jogador não pode ser nulo."
        );

        String sql = """
                SELECT
                    player_uuid,
                    achievement_id,
                    unlocked_at
                FROM player_achievements
                WHERE player_uuid = ?
                ORDER BY unlocked_at DESC
                """;

        List<PlayerAchievementEntity> achievements =
                new ArrayList<>();

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

                while (resultSet.next()) {

                    achievements.add(
                            mapPlayerAchievement(
                                    resultSet
                            )
                    );
                }
            }

            return List.copyOf(
                    achievements
            );

        } catch (SQLException exception) {

            throw new IllegalStateException(
                    "Não foi possível listar as conquistas do jogador "
                            + playerUuid,
                    exception
            );
        }
    }

    public long countByPlayerUuid(
            UUID playerUuid
    ) {

        Objects.requireNonNull(
                playerUuid,
                "UUID do jogador não pode ser nulo."
        );

        String sql = """
                SELECT COUNT(*) AS total
                FROM player_achievements
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
                    return 0;
                }

                return resultSet.getLong(
                        "total"
                );
            }

        } catch (SQLException exception) {

            throw new IllegalStateException(
                    "Não foi possível contar as conquistas do jogador "
                            + playerUuid,
                    exception
            );
        }
    }

    public long countAllUnlocked() {

        String sql = """
                SELECT COUNT(*) AS total
                FROM player_achievements
                """;

        try (
                var connection =
                        DatabaseManager.getConnection();

                var statement =
                        connection.prepareStatement(sql);

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
                    "Não foi possível contar as conquistas desbloqueadas.",
                    exception
            );
        }
    }

    private PlayerAchievementEntity mapPlayerAchievement(
            ResultSet resultSet
    ) throws SQLException {

        return new PlayerAchievementEntity(
                UUID.fromString(
                        resultSet.getString(
                                "player_uuid"
                        )
                ),
                resultSet.getString(
                        "achievement_id"
                ),
                Instant.parse(
                        resultSet.getString(
                                "unlocked_at"
                        )
                )
        );
    }

    private void validate(
            UUID playerUuid,
            String achievementId
    ) {

        Objects.requireNonNull(
                playerUuid,
                "UUID do jogador não pode ser nulo."
        );

        if (
                achievementId == null
                        || achievementId.isBlank()
        ) {
            throw new IllegalArgumentException(
                    "ID da conquista não pode ser vazio."
            );
        }
    }
}