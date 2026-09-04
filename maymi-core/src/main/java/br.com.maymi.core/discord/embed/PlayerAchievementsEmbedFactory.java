package br.com.maymi.core.discord.embed;

import br.com.maymi.core.achievement.AchievementMetric;
import br.com.maymi.core.achievement.PlayerAchievementsDto;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.Color;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

public final class PlayerAchievementsEmbedFactory {

    private static final Color MAYMI_COLOR =
            new Color(
                    255,
                    105,
                    180
            );

    private static final int PROGRESS_BAR_SIZE =
            20;

    private PlayerAchievementsEmbedFactory() {
    }

    public static MessageEmbed create(
            String playerName,
            List<PlayerAchievementsDto> achievements
    ) {

        long unlockedCount =
                achievements
                        .stream()
                        .filter(
                                PlayerAchievementsDto::unlocked
                        )
                        .count();

        int totalAchievements =
                achievements.size();

        int generalPercentage =
                totalAchievements == 0
                        ? 0
                        : (int) Math.round(
                        unlockedCount
                        * 100.0
                        / totalAchievements
                );

        PlayerAchievementsDto nextAchievement =
                findNextAchievement(
                        achievements
                );

        EmbedBuilder embed =
                new EmbedBuilder();

        embed.setColor(
                MAYMI_COLOR
        );

        embed.setTitle(
                "🏆 MAYMI • CONQUISTAS"
        );

        embed.setDescription(
                """
                👤 **%s**

                **Desbloqueadas:** %d / %d

                %s
                **%d%% concluído**
                """.formatted(
                        playerName,
                        unlockedCount,
                        totalAchievements,
                        createProgressBar(
                                generalPercentage
                        ),
                        generalPercentage
                )
        );

        embed.addField(
                "✅ Conquistas desbloqueadas",
                createUnlockedText(
                        achievements
                ),
                false
        );

        if (nextAchievement != null) {

            embed.addField(
                    "🎯 Próxima conquista",
                    createNextAchievementText(
                            nextAchievement
                    ),
                    false
            );
        }

        embed.setFooter(
                "Maymi • Sistema de Conquistas"
        );

        embed.setTimestamp(
                Instant.now()
        );

        return embed.build();
    }

    private static String createUnlockedText(
            List<PlayerAchievementsDto> achievements
    ) {

        StringBuilder builder =
                new StringBuilder();

        achievements
                .stream()
                .filter(
                        PlayerAchievementsDto::unlocked
                )
                .limit(10)
                .forEach(
                        item -> builder
                                .append(
                                        item.achievement()
                                                .category()
                                                .getEmoji()
                                )
                                .append(" **")
                                .append(
                                        item.achievement()
                                                .name()
                                )
                                .append("**")
                                .append(" • ")
                                .append(
                                        item.achievement()
                                                .rarity()
                                                .getDisplayName()
                                )
                                .append("\n")
                );

        if (builder.isEmpty()) {
            return "Nenhuma conquista desbloqueada ainda.";
        }

        return builder.toString().trim();
    }

    private static String createNextAchievementText(
            PlayerAchievementsDto item
    ) {

        int percentage =
                (int) Math.round(
                        item.progressPercentage()
                );

        return """
                %s **%s**

                %s

                **Progresso:** %s / %s
                %s
                **%d%%**

                ✨ **Recompensa:** +%,d XP
                💎 **Raridade:** %s
                """.formatted(
                item.achievement()
                        .category()
                        .getEmoji(),

                item.achievement()
                        .name(),

                item.achievement()
                        .description(),

                formatMetricValue(
                        item.currentValue(),
                        item.achievement()
                                .metric()
                ),

                formatMetricValue(
                        item.achievement()
                                .targetValue(),
                        item.achievement()
                                .metric()
                ),

                createProgressBar(
                        percentage
                ),

                percentage,

                item.achievement()
                        .xpReward(),

                item.achievement()
                        .rarity()
                        .getDisplayName()
        );
    }

    private static PlayerAchievementsDto findNextAchievement(
            List<PlayerAchievementsDto> achievements
    ) {

        return achievements
                .stream()
                .filter(
                        achievement ->
                                !achievement.unlocked()
                )
                .max(
                        java.util.Comparator.comparingDouble(
                                PlayerAchievementsDto::progressPercentage
                        )
                )
                .orElse(null);
    }

    private static String formatMetricValue(
            long value,
            AchievementMetric metric
    ) {

        if (
                metric
                        == AchievementMetric.PLAY_TIME_SECONDS
        ) {

            return formatDuration(
                    value
            );
        }

        return String.format(
                "%,d",
                value
        );
    }

    private static String createProgressBar(
            int percentage
    ) {

        int normalizedPercentage =
                Math.max(
                        0,
                        Math.min(
                                100,
                                percentage
                        )
                );

        int filledBlocks =
                (int) Math.round(
                        normalizedPercentage
                                / 100.0
                                * PROGRESS_BAR_SIZE
                );

        return "█".repeat(
                filledBlocks
        )
                + "░".repeat(
                PROGRESS_BAR_SIZE
                        - filledBlocks
        );
    }

    private static String formatDuration(
            long totalSeconds
    ) {

        Duration duration =
                Duration.ofSeconds(
                        Math.max(
                                0,
                                totalSeconds
                        )
                );

        long days =
                duration.toDays();

        long hours =
                duration.toHoursPart();

        long minutes =
                duration.toMinutesPart();

        if (days > 0) {

            return days
                    + "d "
                    + hours
                    + "h";
        }

        if (hours > 0) {

            return hours
                    + "h "
                    + minutes
                    + "m";
        }

        return minutes + "m";
    }
}