package br.com.maymi.core.achievement;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AchievementRegistryTest {

    @Test
    void deveEncontrarConquistaPorId() {

        var achievement =
                AchievementRegistry.findById(
                        "hunter_1"
                );

        assertTrue(
                achievement.isPresent()
        );

        assertEquals(
                "Caçador I",
                achievement.get().name()
        );
    }

    @Test
    void deveRetornarConquistasPorMetrica() {

        var achievements =
                AchievementRegistry.findByMetric(
                        AchievementMetric.MOB_KILLS
                );

        assertFalse(
                achievements.isEmpty()
        );

        assertTrue(
                achievements
                        .stream()
                        .allMatch(
                                achievement ->
                                        achievement.metric()
                                                == AchievementMetric.MOB_KILLS
                        )
        );
    }

    @Test
    void deveCalcularProgressoDaConquista() {

        Achievement achievement =
                AchievementRegistry
                        .findById(
                                "miner_1"
                        )
                        .orElseThrow();

        assertEquals(
                50.0,
                achievement.progressPercentage(
                        50
                )
        );

        assertTrue(
                achievement.isCompleted(
                        100
                )
        );
    }
}