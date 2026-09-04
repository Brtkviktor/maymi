package br.com.maymi.core.persistence.migration;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public final class V4BackfillPlayerStatistics
        implements Migration {

    @Override
    public int version() {
        return 4;
    }

    @Override
    public String description() {
        return "Backfill player statistics";
    }

    @Override
    public void migrate(
            Connection connection
    ) throws SQLException {

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
                    created_at,
                    updated_at
                )
                SELECT
                    p.uuid,
                    0,
                    1,
                    0,
                    0,
                    0,
                    0,
                    0,
                    p.created_at,
                    p.updated_at
                FROM players p
                WHERE NOT EXISTS (
                    SELECT 1
                    FROM player_statistics ps
                    WHERE ps.player_uuid = p.uuid
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