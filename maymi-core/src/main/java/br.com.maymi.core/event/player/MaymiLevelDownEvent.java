package br.com.maymi.core.event.player;

import br.com.maymi.core.event.GameEvent;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public record MaymiLevelDownEvent(
        UUID playerUuid,
        String playerName,
        int previousLevel,
        int currentLevel,
        long currentXp,
        Instant occurredAt
) implements GameEvent {

    public MaymiLevelDownEvent {

        Objects.requireNonNull(
                playerUuid,
                "UUID do jogador não pode ser nulo."
        );

        if (playerName == null || playerName.isBlank()) {
            throw new IllegalArgumentException(
                    "Nome do jogador não pode ser vazio."
            );
        }

        if (previousLevel <= currentLevel) {
            throw new IllegalArgumentException(
                    "O nível anterior deve ser maior que o nível atual."
            );
        }

        Objects.requireNonNull(
                occurredAt,
                "Data do evento não pode ser nula."
        );
    }
}