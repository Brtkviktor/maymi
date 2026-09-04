package br.com.maymi.core.persistence.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;

public final class DatabaseInitializer {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(DatabaseInitializer.class);

    private static final Path DATABASE_DIRECTORY =
            Path.of("data");

    private DatabaseInitializer() {
    }

    public static void initialize() {
        createDatabaseDirectory();
        validateConnection();
        MigrationManager.migrate();
    }

    private static void createDatabaseDirectory() {
        try {
            Files.createDirectories(DATABASE_DIRECTORY);

            LOGGER.info(
                    "Diretório do banco validado: {}",
                    DATABASE_DIRECTORY.toAbsolutePath()
            );

        } catch (Exception exception) {
            throw new IllegalStateException(
                    "Não foi possível criar o diretório do banco.",
                    exception
            );
        }
    }

    private static void validateConnection() {
        try (Connection connection =
                     DatabaseManager.getConnection()) {

            if (!connection.isValid(3)) {
                throw new SQLException(
                        "A conexão com o SQLite não foi validada."
                );
            }

            LOGGER.info(
                    "Banco SQLite conectado com sucesso."
            );

        } catch (SQLException exception) {
            throw new IllegalStateException(
                    "Não foi possível inicializar o SQLite.",
                    exception
            );
        }
    }
}