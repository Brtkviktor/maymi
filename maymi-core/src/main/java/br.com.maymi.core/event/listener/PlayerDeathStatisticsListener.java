package br.com.maymi.core.event.listener;

import br.com.maymi.core.event.GameEventListener;
import br.com.maymi.core.event.player.MaymiPlayerDeathEvent;
import br.com.maymi.core.persistence.service.PlayerStatisticsService;
import br.com.maymi.core.progression.PlayerProgressionService;
import br.com.maymi.core.progression.policy.DeathXpPenaltyPolicy;

import java.util.Objects;

public final class PlayerDeathStatisticsListener
        implements GameEventListener<MaymiPlayerDeathEvent> {

    private final PlayerStatisticsService statisticsService;
    private final PlayerProgressionService progressionService;
    private final DeathXpPenaltyPolicy penaltyPolicy;

    public PlayerDeathStatisticsListener(
            PlayerStatisticsService statisticsService,
            PlayerProgressionService progressionService,
            DeathXpPenaltyPolicy penaltyPolicy
    ) {

        this.statisticsService =
                Objects.requireNonNull(
                        statisticsService,
                        "PlayerStatisticsService não pode ser nulo."
                );

        this.progressionService =
                Objects.requireNonNull(
                        progressionService,
                        "PlayerProgressionService não pode ser nulo."
                );

        this.penaltyPolicy =
                Objects.requireNonNull(
                        penaltyPolicy,
                        "DeathXpPenaltyPolicy não pode ser nula."
                );
    }

    @Override
    public void onEvent(
            MaymiPlayerDeathEvent event
    ) {

        statisticsService.registerDeath(
                event.playerUuid(),
                event.playerName()
        );

        long penalty =
                penaltyPolicy.calculatePenalty(
                        event.deathCause(),
                        event.killerType()
                );

        progressionService.grantXp(
                event.playerUuid(),
                event.playerName(),
                penalty
        );
    }
}