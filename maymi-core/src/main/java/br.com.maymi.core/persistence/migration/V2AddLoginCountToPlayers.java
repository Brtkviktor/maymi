package br.com.maymi.core.persistence.migration;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public final class V2AddLoginCountToPlayers
        implements Migration {

    @Override
    public int version() {
        return 2;
    }

    @Override
    public String description() {
        return "Add login count to players";
    }

    @Override
    public void migrate(
            Connection connection
    ) throws SQLException {

        String sql = """
                ALTER TABLE players
                ADD COLUMN login_count INTEGER NOT NULL DEFAULT 1
                """;

        try (
                Statement statement =
                        connection.createStatement()
        ) {
            statement.executeUpdate(sql);
        }
    }
}