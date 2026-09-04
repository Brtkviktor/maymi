package br.com.maymi.core.persistence.migration;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public final class V5AddPlayTimeColumns implements Migration {

    @Override
    public int version() {
        return 5;
    }

    @Override
    public String description() {
        return "Add session start to player statistics";
    }

    @Override
    public void migrate(
            Connection connection
    ) throws SQLException {

        String sql = """
                ALTER TABLE player_statistics
                ADD COLUMN session_start_at TEXT
                """;

        try (
                Statement statement =
                        connection.createStatement()
        ) {
            statement.executeUpdate(
                    sql
            );
        }
    }
}