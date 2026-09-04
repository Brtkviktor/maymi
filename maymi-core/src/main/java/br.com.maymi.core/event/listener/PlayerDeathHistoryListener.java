package br.com.maymi.core.event.listener;

import br.com.maymi.core.event.GameEventListener;
import br.com.maymi.core.event.player.MaymiPlayerDeathEvent;
import br.com.maymi.core.history.PlayerHistoryService;

import java.util.Objects;

public final class PlayerDeathHistoryListener
        implements GameEventListener<MaymiPlayerDeathEvent> {

    private final PlayerHistoryService historyService;

    public PlayerDeathHistoryListener(
            PlayerHistoryService historyService
    ) {

        this.historyService =
                Objects.requireNonNull(
                        historyService
                );
    }

    @Override
    public void onEvent(
            MaymiPlayerDeathEvent event
    ) {

        historyService.recordDeath(
                event.playerUuid(),
                event.playerName(),
                event.deathCause(),
                event.killerType()
        );
    }
}