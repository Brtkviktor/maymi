package br.com.maymi.core.achievement;

public enum AchievementCategory {

    COMBAT("⚔️", "Combate"),
    MINING("⛏️", "Mineração"),
    BUILDING("🏗️", "Construção"),
    ACTIVITY("🚪", "Atividade"),
    PROGRESSION("⭐", "Progressão"),
    PLAY_TIME("⏳", "Tempo de jogo");

    private final String emoji;
    private final String displayName;

    AchievementCategory(
            String emoji,
            String displayName
    ) {
        this.emoji = emoji;
        this.displayName = displayName;
    }

    public String getEmoji() {
        return emoji;
    }

    public String getDisplayName() {
        return displayName;
    }
}