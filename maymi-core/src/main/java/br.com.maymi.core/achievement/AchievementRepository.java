package br.com.maymi.core.achievement;

import br.com.maymi.core.persistence.database.DatabaseManager;

import java.sql.SQLException;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class AchievementRepository {

    public void synchronize(
            List<Achievement> achievements
    ) {

        Objects.requireNonNull(
                achievements,
                "Lista de conquistas não pode ser nula."
        );

        Instant synchronizedAt =
                Instant.now();

        for (Achievement achievement : achievements) {

            upsert(
                    achievement,
                    synchronizedAt
            );
        }
    }

    public void upsert(
            Achievement achievement,
            Instant synchronizedAt
    ) {

        Objects.requireNonNull(
                achievement,
                "Conquista não pode ser nula."
        );

        Objects.requireNonNull(
                synchronizedAt,
                "Data de sincronização não pode ser nula."
        );

        String sql = """
                INSERT INTO achievements (
                    id,
                    name,
                    description,
                    category,
                    rarity,
                    metric,
                    target_value,
                    xp_reward,
                    created_at,
                    updated_at
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)

                ON CONFLICT(id) DO UPDATE SET
                    name = excluded.name,
                    description = excluded.description,
                    category = excluded.category,
                    rarity = excluded.rarity,
                    metric = excluded.metric,
                    target_value = excluded.target_value,
                    xp_reward = excluded.xp_reward,
                    updated_at = excluded.updated_at
                """;

        try (
                var connection =
                        DatabaseManager.getConnection();

                var statement =
                        connection.prepareStatement(sql)
        ) {

            String timestamp =
                    synchronizedAt.toString();

            statement.setString(
                    1,
                    achievement.id()
            );

            statement.setString(
                    2,
                    achievement.name()
            );

            statement.setString(
                    3,
                    achievement.description()
            );

            statement.setString(
                    4,
                    achievement.category().name()
            );

            statement.setString(
                    5,
                    achievement.rarity().name()
            );

            statement.setString(
                    6,
                    achievement.metric().name()
            );

            statement.setLong(
                    7,
                    achievement.targetValue()
            );

            statement.setLong(
                    8,
                    achievement.xpReward()
            );

            statement.setString(
                    9,
                    timestamp
            );

            statement.setString(
                    10,
                    timestamp
            );

            statement.executeUpdate();

        } catch (SQLException exception) {

            throw new IllegalStateException(
                    "Não foi possível sincronizar a conquista "
                            + achievement.id(),
                    exception
            );
        }
    }

    public Optional<Achievement> findById(
            String achievementId
    ) {

        if (
                achievementId == null
                        || achievementId.isBlank()
        ) {
            return Optional.empty();
        }

        String sql = """
                SELECT
                    id,
                    name,
                    description,
                    category,
                    rarity,
                    metric,
                    target_value,
                    xp_reward
                FROM achievements
                WHERE id = ?
                """;

        try (
                var connection =
                        DatabaseManager.getConnection();

                var statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1,
                    achievementId
            );

            try (
                    var resultSet =
                            statement.executeQuery()
            ) {

                if (!resultSet.next()) {
                    return Optional.empty();
                }

                return Optional.of(
                        new Achievement(
                                resultSet.getString(
                                        "id"
                                ),
                                resultSet.getString(
                                        "name"
                                ),
                                resultSet.getString(
                                        "description"
                                ),
                                AchievementCategory.valueOf(
                                        resultSet.getString(
                                                "category"
                                        )
                                ),
                                AchievementRarity.valueOf(
                                        resultSet.getString(
                                                "rarity"
                                        )
                                ),
                                AchievementMetric.valueOf(
                                        resultSet.getString(
                                                "metric"
                                        )
                                ),
                                resultSet.getLong(
                                        "target_value"
                                ),
                                resultSet.getLong(
                                        "xp_reward"
                                )
                        )
                );
            }

        } catch (SQLException exception) {

            throw new IllegalStateException(
                    "Não foi possível consultar a conquista "
                            + achievementId,
                    exception
            );
        }
    }

    public long count() {

        String sql = """
                SELECT COUNT(*) AS total
                FROM achievements
                """;

        try (
                var connection =
                        DatabaseManager.getConnection();

                var statement =
                        connection.prepareStatement(sql);

                var resultSet =
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
                    "Não foi possível contar as conquistas.",
                    exception
            );
        }
    }
}