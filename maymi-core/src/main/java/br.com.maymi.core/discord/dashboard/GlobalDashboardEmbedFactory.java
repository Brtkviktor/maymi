package br.com.maymi.core.discord.dashboard;

import br.com.maymi.core.discord.profile.GlobalStatisticsDto;
import br.com.maymi.core.discord.profile.PlayerRankingDto;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.Color;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

public final class GlobalDashboardEmbedFactory {

    private static final Color MAYMI_COLOR =
            new Color(
                    255,
                    105,
                    180
            );

    private static final DateTimeFormatter TIME_FORMATTER =
            DateTimeFormatter
                    .ofPattern("dd/MM/yyyy 'às' HH:mm:ss")
                    .withZone(
                            ZoneId.systemDefault()
                    );

    private GlobalDashboardEmbedFactory() {
    }

    public static MessageEmbed create(
            List<PlayerRankingDto> ranking,
            GlobalStatisticsDto statistics
    ) {

        EmbedBuilder embed =
                new EmbedBuilder();

        embed.setColor(
                MAYMI_COLOR
        );

        embed.setTitle(
                "🌸 MAYMI • DASHBOARD"
        );

        embed.setDescription(
                """
                🟢 **Maymi Core online**

                Acompanhe a progressão, o ranking e as estatísticas globais do servidor.
                """
        );

        if (
                ranking != null
                        && !ranking.isEmpty()
        ) {

            PlayerRankingDto leader =
                    ranking.getFirst();

            embed.addField(
                    "👑 Líder do servidor",
                    """
                    **%s**
                    ⭐ Nível %d • ✨ %,d XP
                    """.formatted(
                            leader.nickname(),
                            leader.level(),
                            leader.maymiXp()
                    ),
                    false
            );
        }

        embed.addField(
                "🏆 Ranking global",
                createRankingText(
                        ranking
                ),
                false
        );

        embed.addField(
                "👤 Jogadores",
                formatNumber(
                        statistics.totalPlayers()
                ),
                true
        );

        embed.addField(
                "✨ XP global",
                formatNumber(
                        statistics.totalXp()
                ),
                true
        );

        embed.addField(
                "⏳ Tempo total",
                formatDuration(
                        statistics.totalPlayTimeSeconds()
                ),
                true
        );

        embed.addField(
                "⚔️ Mobs abatidos",
                formatNumber(
                        statistics.totalMobKills()
                ),
                true
        );

        embed.addField(
                "☠️ Mortes",
                formatNumber(
                        statistics.totalDeaths()
                ),
                true
        );

        embed.addField(
                "📊 Relação global",
                formatGlobalCombatRatio(
                        statistics.totalMobKills(),
                        statistics.totalDeaths()
                ),
                true
        );

        embed.addField(
                "⛏️ Blocos quebrados",
                formatNumber(
                        statistics.totalBlocksBroken()
                ),
                true
        );

        embed.addField(
                "🧱 Blocos colocados",
                formatNumber(
                        statistics.totalBlocksPlaced()
                ),
                true
        );

        embed.addField(
                "🏗️ Total de ações",
                formatNumber(
                        statistics.totalBlocksBroken()
                                + statistics.totalBlocksPlaced()
                ),
                true
        );

        embed.addField(
                "🕒 Última atualização",
                TIME_FORMATTER.format(
                        Instant.now()
                ),
                false
        );

        embed.setFooter(
                "Maymi • Minecraft Discord Companion"
        );

        embed.setTimestamp(
                Instant.now()
        );

        return embed.build();
    }

    private static String createRankingText(
            List<PlayerRankingDto> ranking
    ) {

        if (
                ranking == null
                        || ranking.isEmpty()
        ) {
            return "Ainda não existem jogadores no ranking.";
        }

        StringBuilder builder =
                new StringBuilder();

        for (PlayerRankingDto player : ranking) {

            builder
                    .append(
                            medalFor(
                                    player.position()
                            )
                    )
                    .append(" **")
                    .append(
                            player.nickname()
                    )
                    .append("**")
                    .append("\n")
                    .append("⭐ Nível ")
                    .append(
                            player.level()
                    )
                    .append(" • ✨ ")
                    .append(
                            formatNumber(
                                    player.maymiXp()
                            )
                    )
                    .append(" XP")
                    .append("\n\n");
        }

        return builder.toString().trim();
    }

    private static String medalFor(
            int position
    ) {

        return switch (position) {

            case 1 -> "🥇";
            case 2 -> "🥈";
            case 3 -> "🥉";

            default ->
                    "`#" + position + "`";
        };
    }

    private static String formatNumber(
            long value
    ) {

        return String.format(
                "%,d",
                value
        );
    }

    private static String formatGlobalCombatRatio(
            long mobKills,
            long deaths
    ) {

        if (deaths == 0) {

            return mobKills == 0
                    ? "0,00"
                    : "∞";
        }

        double ratio =
                mobKills / (double) deaths;

        return String.format(
                "%.2f",
                ratio
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