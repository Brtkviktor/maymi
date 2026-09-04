package br.com.maymi.core.discord.profile;

import br.com.maymi.core.progression.LevelCalculator;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.Color;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public final class PlayerProfileEmbedFactory {

    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter
                    .ofPattern("dd/MM/yyyy HH:mm")
                    .withZone(
                            ZoneId.systemDefault()
                    );

    private static final int PROGRESS_BAR_SIZE = 20;

    private PlayerProfileEmbedFactory() {
    }

    public static MessageEmbed create(
            PlayerProfileDto profile
    ) {

        long currentXp =
                profile.maymiXp();

        int currentLevel =
                profile.level();

        long currentLevelMinimum =
                LevelCalculator.minimumXpForLevel(
                        currentLevel
                );

        long nextLevelMinimum =
                LevelCalculator.minimumXpForLevel(
                        currentLevel + 1
                );

        long levelRange =
                Math.max(
                        1,
                        nextLevelMinimum - currentLevelMinimum
                );

        long progressInCurrentLevel =
                Math.max(
                        0,
                        currentXp - currentLevelMinimum
                );

        int progressPercentage =
                (int) Math.min(
                        100,
                        Math.round(
                                progressInCurrentLevel
                                        * 100.0
                                        / levelRange
                        )
                );

        long remainingXp =
                Math.max(
                        0,
                        nextLevelMinimum - currentXp
                );

        EmbedBuilder embed =
                new EmbedBuilder();

        embed.setColor(
                colorForLevel(
                        currentLevel
                )
        );

        embed.setTitle(
                "🌸 MAYMI • PERFIL"
        );

        embed.setDescription(
                """
                👤 **%s**

                ⭐ **Nível %d**
                ✨ **%,d / %,d XP**

                %s
                **%d%%** para o próximo nível
                """.formatted(
                        profile.nickname(),
                        currentLevel,
                        currentXp,
                        nextLevelMinimum,
                        createProgressBar(
                                progressPercentage
                        ),
                        progressPercentage
                )
        );

        embed.addField(
                "🏆 Ranking global",
                rankingLabel(
                        profile.rankingPosition()
                ),
                true
        );

        embed.addField(
                "📈 Próximo nível",
                formatNumber(
                        remainingXp
                )
                        + " XP restantes",
                true
        );

        embed.addField(
                "⏳ Tempo jogado",
                formatDuration(
                        profile.playTimeSeconds()
                ),
                true
        );

        embed.addField(
                "⚔️ Mobs abatidos",
                formatNumber(
                        profile.mobKills()
                ),
                true
        );

        embed.addField(
                "☠️ Mortes",
                formatNumber(
                        profile.deaths()
                ),
                true
        );

        embed.addField(
                "🚪 Entradas",
                formatNumber(
                        profile.loginCount()
                ),
                true
        );

        embed.addField(
                "⛏️ Blocos quebrados",
                formatNumber(
                        profile.blocksBroken()
                ),
                true
        );

        embed.addField(
                "🧱 Blocos colocados",
                formatNumber(
                        profile.blocksPlaced()
                ),
                true
        );

        embed.addField(
                "🏗️ Total de ações",
                formatNumber(
                        profile.blocksBroken()
                                + profile.blocksPlaced()
                ),
                true
        );

        embed.addField(
                "📅 Primeiro acesso",
                DATE_FORMATTER.format(
                        profile.firstJoinAt()
                ),
                true
        );

        embed.addField(
                "🕒 Última atividade",
                DATE_FORMATTER.format(
                        profile.lastSeenAt()
                ),
                true
        );

        embed.addField(
                "🆔 UUID",
                "`"
                        + profile.playerUuid()
                        + "`",
                false
        );

        embed.setFooter(
                "Maymi • Perfil do jogador"
        );

        embed.setTimestamp(
                Instant.now()
        );

        return embed.build();
    }

    private static String createProgressBar(
            int percentage
    ) {

        int filledBlocks =
                (int) Math.round(
                        percentage
                                / 100.0
                                * PROGRESS_BAR_SIZE
                );

        filledBlocks =
                Math.max(
                        0,
                        Math.min(
                                PROGRESS_BAR_SIZE,
                                filledBlocks
                        )
                );

        return "█".repeat(
                filledBlocks
        )
                + "░".repeat(
                PROGRESS_BAR_SIZE - filledBlocks
        );
    }

    private static String rankingLabel(
            int position
    ) {

        return switch (position) {

            case 1 -> "🥇 #1";
            case 2 -> "🥈 #2";
            case 3 -> "🥉 #3";

            default ->
                    "🏅 #" + position;
        };
    }

    private static Color colorForLevel(
            int level
    ) {

        if (level >= 50) {
            return new Color(
                    0,
                    255,
                    255
            );
        }

        if (level >= 35) {
            return new Color(
                    255,
                    69,
                    0
            );
        }

        if (level >= 20) {
            return new Color(
                    255,
                    215,
                    0
            );
        }

        if (level >= 10) {
            return new Color(
                    138,
                    43,
                    226
            );
        }

        if (level >= 5) {
            return new Color(
                    30,
                    144,
                    255
            );
        }

        return new Color(
                255,
                105,
                180
        );
    }

    private static String formatNumber(
            long value
    ) {

        return String.format(
                "%,d",
                value
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
                    + "h "
                    + minutes
                    + "m";
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