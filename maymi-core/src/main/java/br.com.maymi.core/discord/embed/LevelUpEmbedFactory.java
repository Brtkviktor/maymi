package br.com.maymi.core.discord.embed;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.Color;
import java.time.Instant;

public final class LevelUpEmbedFactory {

    private static final Color COLOR =
            new Color(255, 215, 0);

    private LevelUpEmbedFactory() {
    }

    public static MessageEmbed create(
            String playerName,
            int oldLevel,
            int newLevel,
            long xp
    ) {

        EmbedBuilder embed =
                new EmbedBuilder();

        embed.setColor(COLOR);

        embed.setTitle(
                "🌸 MAYMI • LEVEL UP"
        );

        embed.setDescription(
                "🎉 **" + playerName + "** evoluiu!"
        );

        embed.addField(
                "⭐ Nível anterior",
                String.valueOf(oldLevel),
                true
        );

        embed.addField(
                "🌟 Novo nível",
                String.valueOf(newLevel),
                true
        );

        embed.addField(
                "✨ XP Atual",
                String.format("%,d", xp),
                false
        );

        embed.addField(
                "🏆",
                "Continue evoluindo para alcançar novos desafios!",
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