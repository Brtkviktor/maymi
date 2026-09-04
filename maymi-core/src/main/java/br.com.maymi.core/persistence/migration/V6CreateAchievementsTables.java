package br.com.maymi.core.persistence.migration;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public final class V6CreateAchievementsTables
        implements Migration {

    @Override
    public int version() {
        return 6;
    }

    @Override
    public String description() {
        return "Create achievements tables";
    }

    @Override
    public void migrate(
            Connection connection
    ) throws SQLException {

        String createAchievementsTable = """
                CREATE TABLE IF NOT EXISTS achievements (
                    id TEXT PRIMARY KEY NOT NULL,
                    name TEXT NOT NULL,
                    description TEXT NOT NULL,
                    category TEXT NOT NULL,
                    rarity TEXT NOT NULL,
                    metric TEXT NOT NULL,
                    target_value INTEGER NOT NULL,
                    xp_reward INTEGER NOT NULL,
                    created_at TEXT NOT NULL,
                    updated_at TEXT NOT NULL
                )
                """;

        String createPlayerAchievementsTable = """
                CREATE TABLE IF NOT EXISTS player_achievements (
                    player_uuid TEXT NOT NULL,
                    achievement_id TEXT NOT NULL,
                    unlocked_at TEXT NOT NULL,

                    PRIMARY KEY (
                        player_uuid,
                        achievement_id
                    ),

                    FOREIGN KEY (player_uuid)
                        REFERENCES players(uuid)
                        ON DELETE CASCADE,

                    FOREIGN KEY (achievement_id)
                        REFERENCES achievements(id)
                        ON DELETE CASCADE
                )
                """;

        String createPlayerAchievementsIndex = """
                CREATE INDEX IF NOT EXISTS
                    idx_player_achievements_player_uuid
                ON player_achievements(player_uuid)
                """;

        try (
                Statement statement =
                        connection.createStatement()
        ) {

            statement.executeUpdate(
                    createAchievementsTable
            );

            statement.executeUpdate(
                    createPlayerAchievementsTable
            );

            statement.executeUpdate(
                    createPlayerAchievementsIndex
            );
        }
    }
}