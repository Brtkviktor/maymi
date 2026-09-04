package br.com.maymi.core.progression;

public record XpUpdateResult(
        long previousXp,
        long currentXp,
        int previousLevel,
        int currentLevel
) {

    public boolean leveledUp() {
        return currentLevel > previousLevel;
    }

    public boolean leveledDown() {
        return currentLevel < previousLevel;
    }

    public long appliedXp() {
        return currentXp - previousXp;
    }
}