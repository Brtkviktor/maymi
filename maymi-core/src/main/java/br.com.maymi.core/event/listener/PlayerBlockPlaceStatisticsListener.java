package br.com.maymi.core.event.listener;

import br.com.maymi.core.achievement.AchievementUnlockService;
import br.com.maymi.core.event.GameEventListener;
import br.com.maymi.core.event.player.MaymiBlockPlaceEvent;
import br.com.maymi.core.persistence.service.PlayerStatisticsService;
import br.com.maymi.core.progression.PlayerProgressionService;
import br.com.maymi.core.progression.policy.GameplayXpPolicy;

import java.util.Objects;

public final class PlayerBlockPlaceStatisticsListener
        implements GameEventListener<MaymiBlockPlaceEvent> {

    private final PlayerStatisticsService statisticsService;
    private final PlayerProgressionService progressionService;
    private final AchievementUnlockService achievementUnlockService;

    public PlayerBlockPlaceStatisticsListener(
            PlayerStatisticsService statisticsService,
            PlayerProgressionService progressionService,
            AchievementUnlockService achievementUnlockService
    ) {

        this.statisticsService =
                Objects.requireNonNull(statisticsService);

        this.progressionService =
                Objects.requireNonNull(progressionService);

        this.achievementUnlockService =
                Objects.requireNonNull(achievementUnlockService);
    }

    @Override
    public void onEvent(
            MaymiBlockPlaceEvent event
    ) {

        statisticsService.registerBlockPlace(
                event.playerUuid(),
                event.playerName()
        );

        progressionService.grantXp(
                event.playerUuid(),
                event.playerName(),
                GameplayXpPolicy.BLOCK_PLACE_XP
        );

        achievementUnlockService.verifyPlayer(
                event.playerUuid(),
                event.playerName()
        );
    }
}