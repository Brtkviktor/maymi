package br.com.maymi.core.event.listener;

import br.com.maymi.core.event.GameEventListener;
import br.com.maymi.core.event.player.MaymiLevelUpEvent;
import br.com.maymi.core.history.PlayerHistoryService;

import java.util.Objects;

public final class PlayerLevelUpHistoryListener
        implements GameEventListener<MaymiLevelUpEvent> {

    private final PlayerHistoryService historyService;

    public PlayerLevelUpHistoryListener(
            PlayerHistoryService historyService
    ) {

        this.historyService =
                Objects.requireNonNull(
                        historyService
                );
    }

    @Override
    public void onEvent(
            MaymiLevelUpEvent event
    ) {

        historyService.recordLevelUp(
                event.playerUuid(),
                event.playerName(),
                event.previousLevel(),
                event.currentLevel(),
                event.currentXp()
        );
    }
}