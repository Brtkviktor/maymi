package br.com.maymi.core.event.player;

import br.com.maymi.core.event.GameEvent;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public record MaymiPlayerDeathEvent(
        UUID playerUuid,
        String playerName,
        String deathMessage,
        String deathCause,
        String killerType,
        Instant occurredAt
) implements GameEvent {

    public MaymiPlayerDeathEvent {

        Objects.requireNonNull(
                playerUuid,
                "UUID do jogador não pode ser nulo."
        );

        if (playerName == null || playerName.isBlank()) {
            throw new IllegalArgumentException(
                    "Nome do jogador não pode ser vazio."
            );
        }

        if (deathMessage == null || deathMessage.isBlank()) {
            deathMessage =
                    "morreu por uma causa desconhecida";
        }

        if (deathCause == null || deathCause.isBlank()) {
            deathCause = "UNKNOWN";
        }

        Objects.requireNonNull(
                occurredAt,
                "Data do evento não pode ser nula."
        );
    }
}