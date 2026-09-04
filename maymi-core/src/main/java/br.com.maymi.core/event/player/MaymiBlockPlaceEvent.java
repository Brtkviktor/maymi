package br.com.maymi.core.event.player;

import br.com.maymi.core.event.GameEvent;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public record MaymiBlockPlaceEvent(
        UUID playerUuid,
        String playerName,
        String blockType,
        String worldName,
        int x,
        int y,
        int z,
        Instant occurredAt
) implements GameEvent {

    public MaymiBlockPlaceEvent {

        Objects.requireNonNull(playerUuid);

        if (playerName == null || playerName.isBlank()) {
            throw new IllegalArgumentException(
                    "Nome do jogador não pode ser vazio."
            );
        }

        if (blockType == null || blockType.isBlank()) {
            throw new IllegalArgumentException(
                    "Tipo do bloco não pode ser vazio."
            );
        }

        if (worldName == null || worldName.isBlank()) {
            throw new IllegalArgumentException(
                    "Nome do mundo não pode ser vazio."
            );
        }

        Objects.requireNonNull(occurredAt);
    }
}