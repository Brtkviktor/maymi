package br.com.maymi.core.achievement;

import java.awt.Color;

public enum AchievementRarity {

    COMMON(
            "Comum",
            new Color(149, 165, 166)
    ),

    UNCOMMON(
            "Incomum",
            new Color(46, 204, 113)
    ),

    RARE(
            "Rara",
            new Color(52, 152, 219)
    ),

    EPIC(
            "Épica",
            new Color(155, 89, 182)
    ),

    LEGENDARY(
            "Lendária",
            new Color(241, 196, 15)
    );

    private final String displayName;
    private final Color color;

    AchievementRarity(
            String displayName,
            Color color
    ) {
        this.displayName = displayName;
        this.color = color;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Color getColor() {
        return color;
    }
}