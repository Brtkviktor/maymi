package br.com.maymi.core.persistence.migration;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public final class V3CreatePlayerStatisticsTable
        implements Migration {

    @Override
    public int version() {
        return 3;
    }

    @Override
    public String description() {
        return "Create player statistics table";
    }

    @Override
    public void migrate(
            Connection connection
    ) throws SQLException {

        String sql = """
                CREATE TABLE IF NOT EXISTS player_statistics (
                    player_uuid TEXT PRIMARY KEY NOT NULL,

                    maymi_xp INTEGER NOT NULL DEFAULT 0,
                    level INTEGER NOT NULL DEFAULT 1,

                    deaths INTEGER NOT NULL DEFAULT 0,
                    mob_kills INTEGER NOT NULL DEFAULT 0,

                    blocks_placed INTEGER NOT NULL DEFAULT 0,
                    blocks_broken INTEGER NOT NULL DEFAULT 0,

                    play_time_seconds INTEGER NOT NULL DEFAULT 0,

                    created_at TEXT NOT NULL,
                    updated_at TEXT NOT NULL,

                    FOREIGN KEY (player_uuid)
                        REFERENCES players(uuid)
                        ON DELETE CASCADE
                )
                """;

        try (
                Statement statement =
                        connection.createStatement()
        ) {
            statement.executeUpdate(sql);
        }
    }
}