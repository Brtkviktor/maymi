package br.com.maymi.core.event.listener;

import br.com.maymi.core.event.player.MaymiPlayerDeathEvent;
import br.com.maymi.core.persistence.service.PlayerStatisticsService;
import br.com.maymi.core.progression.PlayerProgressionService;
import br.com.maymi.core.progression.policy.DeathXpPenaltyPolicy;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PlayerDeathStatisticsListenerTest {

    @Test
    void deveRegistrarMorteEAplicarPenalidadeCalculada() {

        PlayerStatisticsService statisticsService =
                mock(PlayerStatisticsService.class);

        PlayerProgressionService progressionService =
                mock(PlayerProgressionService.class);

        DeathXpPenaltyPolicy penaltyPolicy =
                mock(DeathXpPenaltyPolicy.class);

        PlayerDeathStatisticsListener listener =
                new PlayerDeathStatisticsListener(
                        statisticsService,
                        progressionService,
                        penaltyPolicy
                );

        UUID playerUuid =
                UUID.randomUUID();

        MaymiPlayerDeathEvent event =
                new MaymiPlayerDeathEvent(
                        playerUuid,
                        "BRtkViktor",
                        "foi morto por um zumbi",
                        "ENTITY_ATTACK",
                        "ZOMBIE",
                        Instant.now()
                );

        when(
                penaltyPolicy.calculatePenalty(
                        "ENTITY_ATTACK",
                        "ZOMBIE"
                )
        ).thenReturn(-7L);

        listener.onEvent(event);

        verify(statisticsService)
                .registerDeath(
                        playerUuid,
                        "BRtkViktor"
                );

        verify(progressionService)
                .grantXp(
                        playerUuid,
                        "BRtkViktor",
                        -7
                );
    }
}