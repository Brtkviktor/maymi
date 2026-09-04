package br.com.maymi.core.achievement;

import br.com.maymi.core.persistence.database.DatabaseInitializer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PlayerAchievementRepositoryTest {

    private static final UUID PLAYER_UUID =
            UUID.fromString(
                    "9768cf9d-8f3b-404b-9e04-19e4c4ad745c"
            );

    @BeforeAll
    static void initializeDatabase() {

        DatabaseInitializer.initialize();
    }

    @Test
    void deveImpedirConquistaDuplicada() {

        PlayerAchievementRepository repository =
                new PlayerAchievementRepository();

        boolean firstUnlock =
                repository.unlock(
                        PLAYER_UUID,
                        "hunter_1",
                        Instant.now()
                );

        boolean secondUnlock =
                repository.unlock(
                        PLAYER_UUID,
                        "hunter_1",
                        Instant.now()
                );

        assertTrue(
                firstUnlock
                        || repository.hasUnlocked(
                        PLAYER_UUID,
                        "hunter_1"
                )
        );

        assertFalse(
                secondUnlock
        );
    }
}