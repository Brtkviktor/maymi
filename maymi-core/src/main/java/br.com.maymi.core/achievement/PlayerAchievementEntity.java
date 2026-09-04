package br.com.maymi.core.achievement;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public record PlayerAchievementEntity(
        UUID playerUuid,
        String achievementId,
        Instant unlockedAt
) {

    public PlayerAchievementEntity {

        Objects.requireNonNull(
                playerUuid,
                "UUID do jogador não pode ser nulo."
        );

        if (
                achievementId == null
                        || achievementId.isBlank()
        ) {
            throw new IllegalArgumentException(
                    "ID da conquista não pode ser vazio."
            );
        }

        Objects.requireNonNull(
                unlockedAt,
                "Data de desbloqueio não pode ser nula."
        );
    }
}