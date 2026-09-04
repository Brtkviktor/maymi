package br.com.maymi.core.event.listener;

import br.com.maymi.core.achievement.AchievementUnlockService;
import br.com.maymi.core.event.GameEventListener;
import br.com.maymi.core.event.player.MaymiBlockBreakEvent;
import br.com.maymi.core.persistence.service.PlayerStatisticsService;
import br.com.maymi.core.progression.PlayerProgressionService;
import br.com.maymi.core.progression.policy.GameplayXpPolicy;

import java.util.Objects;

public final class PlayerBlockBreakStatisticsListener
        implements GameEventListener<MaymiBlockBreakEvent> {

    private final PlayerStatisticsService statisticsService;
    private final PlayerProgressionService progressionService;
    private final AchievementUnlockService achievementUnlockService;

    public PlayerBlockBreakStatisticsListener(
            PlayerStatisticsService statisticsService,
            PlayerProgressionService progressionService,
            AchievementUnlockService achievementUnlockService
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

        this.achievementUnlockService =
                Objects.requireNonNull(
                        achievementUnlockService,
                        "AchievementUnlockService não pode ser nulo."
                );
    }

    @Override
    public void onEvent(
            MaymiBlockBreakEvent event
    ) {

        statisticsService.registerBlockBreak(
                event.playerUuid(),
                event.playerName()
        );

        progressionService.grantXp(
                event.playerUuid(),
                event.playerName(),
                GameplayXpPolicy.BLOCK_BREAK_XP
        );

        achievementUnlockService.verifyPlayer(
                event.playerUuid(),
                event.playerName()
        );
    }
}