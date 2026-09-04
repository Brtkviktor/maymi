package br.com.maymi.core.discord.profile;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.Color;
import java.time.Instant;
import java.util.List;

public final class PlayerRankingEmbedFactory {

    private static final Color MAYMI_COLOR =
            new Color(
                    255,
                    105,
                    180
            );

    private PlayerRankingEmbedFactory() {
    }

    public static MessageEmbed create(
            List<PlayerRankingDto> ranking
    ) {

        EmbedBuilder embed =
                new EmbedBuilder();

        embed.setColor(
                MAYMI_COLOR
        );

        embed.setTitle(
                "🏆 Ranking Global Maymi"
        );

        if (ranking.isEmpty()) {

            embed.setDescription(
                    "Ainda não existem jogadores no ranking."
            );

            return embed.build();
        }

        StringBuilder description =
                new StringBuilder();

        for (PlayerRankingDto player : ranking) {

            description
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

        embed.setDescription(
                description.toString()
        );

        embed.setFooter(
                "Maymi • Ranking global do servidor"
        );

        embed.setTimestamp(
                Instant.now()
        );

        return embed.build();
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
}