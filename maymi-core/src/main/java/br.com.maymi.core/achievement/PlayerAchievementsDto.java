package br.com.maymi.core.achievement;

import java.time.Instant;

public record PlayerAchievementsDto(
        Achievement achievement,
        boolean unlocked,
        long currentValue,
        Instant unlockedAt
) {

    public double progressPercentage() {

        return achievement.progressPercentage(
                currentValue
        );
    }
}