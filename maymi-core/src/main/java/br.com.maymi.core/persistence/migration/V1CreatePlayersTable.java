package br.com.maymi.core.persistence.migration;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public final class V1CreatePlayersTable implements Migration {

    @Override
    public int version() {
        return 1;
    }

    @Override
    public String description() {
        return "Create players table";
    }

    @Override
    public void migrate(Connection connection) throws SQLException {

        String sql = """
                CREATE TABLE IF NOT EXISTS players (
                    uuid TEXT PRIMARY KEY NOT NULL,
                    nickname TEXT NOT NULL,
                    first_join_at TEXT NOT NULL,
                    last_seen_at TEXT NOT NULL,
                    created_at TEXT NOT NULL,
                    updated_at TEXT NOT NULL
                )
                """;

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        }
    }
}