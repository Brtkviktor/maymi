package br.com.maymi.core.progression;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LevelCalculatorTest {

    @Test
    void deveCalcularNivelUmComZeroXp() {

        assertEquals(
                1,
                LevelCalculator.calculateLevel(0)
        );
    }

    @Test
    void deveCalcularNivelDoisComCinquentaXp() {

        assertEquals(
                2,
                LevelCalculator.calculateLevel(50)
        );
    }

    @Test
    void deveCalcularNivelTresComDuzentosXp() {

        assertEquals(
                3,
                LevelCalculator.calculateLevel(200)
        );
    }

    @Test
    void deveTratarXpNegativoComoZero() {

        assertEquals(
                1,
                LevelCalculator.calculateLevel(-100)
        );
    }
}