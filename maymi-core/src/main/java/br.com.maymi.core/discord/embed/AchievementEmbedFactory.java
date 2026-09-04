package br.com.maymi.core.discord.embed;

import br.com.maymi.core.achievement.Achievement;
import br.com.maymi.core.achievement.AchievementRarity;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.Color;
import java.time.Instant;

public final class AchievementEmbedFactory {

    private AchievementEmbedFactory() {
    }

    public static MessageEmbed create(
            String playerName,
            Achievement achievement
    ) {

        EmbedBuilder embed =
                new EmbedBuilder();

        embed.setColor(
                getColor(
                        achievement.rarity()
                )
        );

        embed.setTitle(
                "🏆 MAYMI • CONQUISTA DESBLOQUEADA"
        );

        embed.setDescription(
                "🎉 **"
                        + playerName
                        + "** desbloqueou uma nova conquista!"
        );

        embed.addField(
                "🏅 Conquista",
                achievement.name(),
                false
        );

        embed.addField(
                "📝 Descrição",
                achievement.description(),
                false
        );

        embed.addField(
                "⭐ XP Bônus",
                "+"
                        + achievement.xpReward()
                        + " XP",
                true
        );

        embed.addField(
                "📂 Categoria",
                achievement.category()
                        .getDisplayName(),
                true
        );

        embed.addField(
                "💎 Raridade",
                achievement.rarity()
                        .getDisplayName(),
                true
        );

        embed.setFooter(
                "Maymi • Sistema de Conquistas"
        );

        embed.setTimestamp(
                Instant.now()
        );

        return embed.build();
    }

    private static Color getColor(
            AchievementRarity rarity
    ) {

        return rarity.getColor();
    }
}