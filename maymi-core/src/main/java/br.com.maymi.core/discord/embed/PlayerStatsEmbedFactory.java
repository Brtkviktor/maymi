package br.com.maymi.core.discord.embed;

import br.com.maymi.core.discord.profile.PlayerProfileDto;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.Color;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public final class PlayerStatsEmbedFactory {

    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter
                    .ofPattern("dd/MM/yyyy HH:mm")
                    .withZone(
                            ZoneId.systemDefault()
                    );

    private PlayerStatsEmbedFactory() {
    }

    public static MessageEmbed create(
            PlayerProfileDto profile
    ) {

        long totalActions =
                profile.blocksBroken()
                        + profile.blocksPlaced();

        double combatRatio =
                calculateCombatRatio(
                        profile.mobKills(),
                        profile.deaths()
                );

        double actionsPerLogin =
                calculateAverage(
                        totalActions,
                        profile.loginCount()
                );

        double mobsPerLogin =
                calculateAverage(
                        profile.mobKills(),
                        profile.loginCount()
                );

        EmbedBuilder embed =
                new EmbedBuilder();

        embed.setColor(
                colorForLevel(
                        profile.level()
                )
        );

        embed.setTitle(
                "📊 MAYMI • ESTATÍSTICAS"
        );

        embed.setDescription(
                """
                👤 **%s**

                Visão detalhada da atividade e progressão do jogador no servidor.
                """.formatted(
                        profile.nickname()
                )
        );

        embed.addField(
                "⭐ Progressão",
                """
                **Nível:** %d
                **XP:** %,d
                **Ranking global:** %s
                """.formatted(
                        profile.level(),
                        profile.maymiXp(),
                        rankingLabel(
                                profile.rankingPosition()
                        )
                ),
                false
        );

        embed.addField(
                "⚔️ Combate",
                """
                **Mobs abatidos:** %,d
                **Mortes:** %,d
                **Relação mobs/morte:** %.2f
                """.formatted(
                        profile.mobKills(),
                        profile.deaths(),
                        combatRatio
                ),
                true
        );

        embed.addField(
                "🎯 Eficiência",
                """
                **Mobs por entrada:** %.2f
                **Ações por entrada:** %.2f
                """.formatted(
                        mobsPerLogin,
                        actionsPerLogin
                ),
                true
        );

        embed.addField(
                "🏗️ Construção",
                """
                **Blocos quebrados:** %,d
                **Blocos colocados:** %,d
                **Total de ações:** %,d
                """.formatted(
                        profile.blocksBroken(),
                        profile.blocksPlaced(),
                        totalActions
                ),
                true
        );

        embed.addField(
                "🚪 Atividade",
                """
                **Entradas:** %,d
                **Tempo jogado:** %s
                **Média por sessão:** %s
                """.formatted(
                        profile.loginCount(),
                        formatDuration(
                                profile.playTimeSeconds()
                        ),
                        formatAverageSessionTime(
                                profile.playTimeSeconds(),
                                profile.loginCount()
                        )
                ),
                true
        );

        embed.addField(
                "📅 Histórico",
                """
                **Primeiro acesso:** %s
                **Última atividade:** %s
                """.formatted(
                        DATE_FORMATTER.format(
                                profile.firstJoinAt()
                        ),
                        DATE_FORMATTER.format(
                                profile.lastSeenAt()
                        )
                ),
                false
        );

        embed.addField(
                "🆔 UUID",
                "`"
                        + profile.playerUuid()
                        + "`",
                false
        );

        embed.setFooter(
                "Maymi • Estatísticas detalhadas"
        );

        embed.setTimestamp(
                Instant.now()
        );

        return embed.build();
    }

    private static double calculateCombatRatio(
            long mobKills,
            long deaths
    ) {

        if (deaths == 0) {
            return mobKills;
        }

        return mobKills
                / (double) deaths;
    }

    private static double calculateAverage(
            long total,
            long divisor
    ) {

        if (divisor <= 0) {
            return 0;
        }

        return total
                / (double) divisor;
    }

    private static String formatAverageSessionTime(
            long totalSeconds,
            long loginCount
    ) {

        if (loginCount <= 0) {
            return "0m";
        }

        long averageSeconds =
                totalSeconds
                        / loginCount;

        return formatDuration(
                averageSeconds
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