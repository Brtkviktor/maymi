package br.com.maymi.core.event.listener;

import br.com.maymi.core.event.GameEventListener;
import br.com.maymi.core.event.player.MaymiAchievementUnlockEvent;
import br.com.maymi.core.history.PlayerHistoryService;

import java.util.Objects;

public final class PlayerAchievementHistoryListener
        implements GameEventListener<MaymiAchievementUnlockEvent> {

    private final PlayerHistoryService historyService;

    public PlayerAchievementHistoryListener(
            PlayerHistoryService historyService
    ) {

        this.historyService =
                Objects.requireNonNull(
                        historyService
                );
    }

    @Override
    public void onEvent(
            MaymiAchievementUnlockEvent event
    ) {

        historyService.recordAchievement(
                event.playerUuid(),
                event.playerName(),
                event.achievement().id(),
                event.achievement().name(),
                event.achievement().xpReward()
        );
    }
}