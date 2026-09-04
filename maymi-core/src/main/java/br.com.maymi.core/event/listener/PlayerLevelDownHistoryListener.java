package br.com.maymi.core.event.listener;

import br.com.maymi.core.event.GameEventListener;
import br.com.maymi.core.event.player.MaymiLevelDownEvent;
import br.com.maymi.core.history.PlayerHistoryService;

import java.util.Objects;

public final class PlayerLevelDownHistoryListener
        implements GameEventListener<MaymiLevelDownEvent> {

    private final PlayerHistoryService historyService;

    public PlayerLevelDownHistoryListener(
            PlayerHistoryService historyService
    ) {

        this.historyService =
                Objects.requireNonNull(
                        historyService
                );
    }

    @Override
    public void onEvent(
            MaymiLevelDownEvent event
    ) {

        historyService.recordLevelDown(
                event.playerUuid(),
                event.playerName(),
                event.previousLevel(),
                event.currentLevel(),
                event.currentXp()
        );
    }
}