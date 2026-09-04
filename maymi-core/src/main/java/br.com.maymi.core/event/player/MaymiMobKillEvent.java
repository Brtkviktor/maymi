package br.com.maymi.core.event.player;

import br.com.maymi.core.event.GameEvent;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public record MaymiMobKillEvent(
        UUID playerUuid,
        String playerName,
        String mobType,
        String mobName,
        Instant occurredAt
) implements GameEvent {

    public MaymiMobKillEvent {

        Objects.requireNonNull(
                playerUuid,
                "UUID do jogador não pode ser nulo."
        );

        if (playerName == null || playerName.isBlank()) {
            throw new IllegalArgumentException(
                    "Nome do jogador não pode ser vazio."
            );
        }

        if (mobType == null || mobType.isBlank()) {
            throw new IllegalArgumentException(
                    "Tipo do mob não pode ser vazio."
            );
        }

        if (mobName == null || mobName.isBlank()) {
            mobName = mobType;
        }

        Objects.requireNonNull(
                occurredAt,
                "Data do evento não pode ser nula."
        );
    }
}