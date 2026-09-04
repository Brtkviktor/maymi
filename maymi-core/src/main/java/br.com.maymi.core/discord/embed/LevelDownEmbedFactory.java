package br.com.maymi.core.discord.embed;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.Color;
import java.time.Instant;

public final class LevelDownEmbedFactory {

    private static final Color COLOR =
            new Color(180, 50, 50);

    private LevelDownEmbedFactory() {
    }

    public static MessageEmbed create(
            String playerName,
            int oldLevel,
            int newLevel,
            long xp
    ) {

        EmbedBuilder embed =
                new EmbedBuilder();

        embed.setColor(
                COLOR
        );

        embed.setTitle(
                "📉 MAYMI • LEVEL DOWN"
        );

        embed.setDescription(
                "💀 **"
                        + playerName
                        + "** perdeu um nível."
        );

        embed.addField(
                "⭐ Nível anterior",
                String.valueOf(
                        oldLevel
                ),
                true
        );

        embed.addField(
                "🔻 Nível atual",
                String.valueOf(
                        newLevel
                ),
                true
        );

        embed.addField(
                "✨ XP Atual",
                String.format(
                        "%,d",
                        xp
                ),
                false
        );

        embed.addField(
                "⚔️",
                "Sobreviva por mais tempo para recuperar seu progresso.",
                false
        );

        embed.setFooter(
                "Maymi • Progressão do servidor"
        );

        embed.setTimestamp(
                Instant.now()
        );

        return embed.build();
    }
}