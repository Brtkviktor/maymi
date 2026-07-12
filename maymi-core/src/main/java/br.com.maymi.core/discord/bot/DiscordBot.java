package br.com.maymi.core.discord.bot;

import br.com.maymi.core.configuration.ConfigurationManager;
import br.com.maymi.core.discord.DiscordManager;
import br.com.maymi.core.shared.util.Console;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

public class DiscordBot {

    private JDA jda;

    public void start() {

        try {

            String token = ConfigurationManager.getDiscordToken();

            Console.info("Conectando ao Discord...");

            jda = JDABuilder.createDefault(token)
                    .build();

            jda.awaitReady();

            DiscordManager.setJda(jda);

            Console.success("Discord conectado!");
            Console.info("Bot: " + jda.getSelfUser().getAsTag());
            Console.info("Servidores: " + jda.getGuilds().size());

        } catch (Exception e) {

            Console.error(e.getMessage());

        }

    }
}