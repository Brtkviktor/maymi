package br.com.maymi.core.event.player;

import br.com.maymi.core.event.GameEvent;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public record MaymiLevelUpEvent(
        UUID playerUuid,
        String playerName,
        int previousLevel,
        int currentLevel,
        long currentXp,
        Instant occurredAt
) implements GameEvent {

    public MaymiLevelUpEvent {

        Objects.requireNonNull(
                playerUuid,
                "UUID do jogador não pode ser nulo."
        );

        if (
                playerName == null
                        || playerName.isBlank()
        ) {

            throw new IllegalArgumentException(
                    "Nome do jogador não pode ser vazio."
            );
        }

        if (previousLevel < 1) {

            throw new IllegalArgumentException(
                    "Nível anterior inválido."
            );
        }

        if (currentLevel <= previousLevel) {

            throw new IllegalArgumentException(
                    "O novo nível deve ser maior que o nível anterior."
            );
        }

        Objects.requireNonNull(
                occurredAt,
                "Data do evento não pode ser nula."
        );
    }
}