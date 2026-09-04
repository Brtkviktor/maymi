package br.com.maymi.core.progression;

import br.com.maymi.core.achievement.AchievementUnlockService;
import br.com.maymi.core.event.MaymiEventBus;
import br.com.maymi.core.event.player.MaymiLevelDownEvent;
import br.com.maymi.core.event.player.MaymiLevelUpEvent;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public final class PlayerProgressionService {

    private final PlayerXpService playerXpService;
    private final MaymiEventBus eventBus;
    private final AchievementUnlockService achievementUnlockService;

    public PlayerProgressionService(
            PlayerXpService playerXpService,
            MaymiEventBus eventBus,
            AchievementUnlockService achievementUnlockService
    ) {

        this.playerXpService =
                Objects.requireNonNull(
                        playerXpService,
                        "PlayerXpService não pode ser nulo."
                );

        this.eventBus =
                Objects.requireNonNull(
                        eventBus,
                        "MaymiEventBus não pode ser nulo."
                );

        this.achievementUnlockService =
                Objects.requireNonNull(
                        achievementUnlockService,
                        "AchievementUnlockService não pode ser nulo."
                );
    }

    public XpUpdateResult grantXp(
            UUID playerUuid,
            String playerName,
            long amount
    ) {

        XpUpdateResult result =
                playerXpService.addXp(
                        playerUuid,
                        playerName,
                        amount
                );

        if (result.leveledUp()) {

            eventBus.publish(
                    new MaymiLevelUpEvent(
                            playerUuid,
                            playerName,
                            result.previousLevel(),
                            result.currentLevel(),
                            result.currentXp(),
                            Instant.now()
                    )
            );

        } else if (result.leveledDown()) {

            eventBus.publish(
                    new MaymiLevelDownEvent(
                            playerUuid,
                            playerName,
                            result.previousLevel(),
                            result.currentLevel(),
                            result.currentXp(),
                            Instant.now()
                    )
            );
        }

        achievementUnlockService.verifyPlayer(
                playerUuid,
                playerName
        );

        return result;
    }
}