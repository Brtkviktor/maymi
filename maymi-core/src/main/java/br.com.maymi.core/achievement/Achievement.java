package br.com.maymi.core.achievement;

import java.util.Objects;

public record Achievement(
        String id,
        String name,
        String description,
        AchievementCategory category,
        AchievementRarity rarity,
        AchievementMetric metric,
        long targetValue,
        long xpReward
) {

    public Achievement {

        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException(
                    "ID da conquista não pode ser vazio."
            );
        }

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(
                    "Nome da conquista não pode ser vazio."
            );
        }

        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException(
                    "Descrição da conquista não pode ser vazia."
            );
        }

        Objects.requireNonNull(
                category,
                "Categoria não pode ser nula."
        );

        Objects.requireNonNull(
                rarity,
                "Raridade não pode ser nula."
        );

        Objects.requireNonNull(
                metric,
                "Métrica não pode ser nula."
        );

        if (targetValue <= 0) {
            throw new IllegalArgumentException(
                    "Meta da conquista deve ser maior que zero."
            );
        }

        if (xpReward < 0) {
            throw new IllegalArgumentException(
                    "Recompensa de XP não pode ser negativa."
            );
        }
    }

    public boolean isCompleted(
            long currentValue
    ) {
        return currentValue >= targetValue;
    }

    public double progressPercentage(
            long currentValue
    ) {

        if (currentValue <= 0) {
            return 0;
        }

        return Math.min(
                100.0,
                currentValue * 100.0 / targetValue
        );
    }
}