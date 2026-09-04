package br.com.maymi.core.persistence.migration;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public final class V7CreatePlayerHistoryTable
        implements Migration {

    @Override
    public int version() {
        return 7;
    }

    @Override
    public String description() {
        return "Create player history table";
    }

    @Override
    public void migrate(
            Connection connection
    ) throws SQLException {

        String createTable = """
                CREATE TABLE IF NOT EXISTS player_history (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    player_uuid TEXT NOT NULL,
                    history_type TEXT NOT NULL,
                    title TEXT NOT NULL,
                    description TEXT NOT NULL,
                    metadata TEXT NOT NULL DEFAULT '{}',
                    created_at TEXT NOT NULL,

                    FOREIGN KEY (player_uuid)
                        REFERENCES players(uuid)
                        ON DELETE CASCADE
                )
                """;

        String createPlayerIndex = """
                CREATE INDEX IF NOT EXISTS
                    idx_player_history_player_uuid
                ON player_history(player_uuid)
                """;

        String createTypeIndex = """
                CREATE INDEX IF NOT EXISTS
                    idx_player_history_type
                ON player_history(history_type)
                """;

        String createCreatedAtIndex = """
                CREATE INDEX IF NOT EXISTS
                    idx_player_history_created_at
                ON player_history(created_at)
                """;

        try (
                Statement statement =
                        connection.createStatement()
        ) {

            statement.executeUpdate(
                    createTable
            );

            statement.executeUpdate(
                    createPlayerIndex
            );

            statement.executeUpdate(
                    createTypeIndex
            );

            statement.executeUpdate(
                    createCreatedAtIndex
            );
        }
    }
}