package br.com.maymi.core.event.player;

import br.com.maymi.core.achievement.Achievement;
import br.com.maymi.core.event.GameEvent;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public record MaymiAchievementUnlockEvent(
        UUID playerUuid,
        String playerName,
        Achievement achievement,
        long currentValue,
        Instant occurredAt
) implements GameEvent {

    public MaymiAchievementUnlockEvent {

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

        Objects.requireNonNull(
                achievement,
                "Conquista não pode ser nula."
        );

        if (currentValue < 0) {

            throw new IllegalArgumentException(
                    "Valor atual da métrica não pode ser negativo."
            );
        }

        Objects.requireNonNull(
                occurredAt,
                "Data do evento não pode ser nula."
        );
    }
}