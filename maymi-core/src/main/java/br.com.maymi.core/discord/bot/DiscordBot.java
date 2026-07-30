package br.com.maymi.core.discord.bot;

import br.com.maymi.core.configuration.ConfigurationManager;
import br.com.maymi.core.discord.DiscordManager;
import br.com.maymi.core.discord.listener.DiscordMessageListener;
import br.com.maymi.core.shared.util.Console;
import br.com.maymi.core.socket.SocketClient;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class DiscordBot {

    private JDA jda;


    public void start() {

        try {

            String token =
                    ConfigurationManager
                            .getDiscordToken();


            Console.info(
                    "Conectando ao Discord..."
            );


            // =====================================================
            // SOCKET CLIENT
            // =====================================================

            SocketClient socketClient =
                    new SocketClient();


            // =====================================================
            // DISCORD BOT
            // =====================================================

            jda =
                    JDABuilder
                            .createDefault(
                                    token
                            )

                            .enableIntents(
                                    GatewayIntent.MESSAGE_CONTENT
                            )

                            .addEventListeners(
                                    new DiscordMessageListener(
                                            socketClient
                                    )
                            )

                            .build();


            // =====================================================
            // AGUARDA CONEXÃO
            // =====================================================

            jda.awaitReady();


            DiscordManager.setJda(
                    jda
            );


            // =====================================================
            // INFORMAÇÕES
            // =====================================================

            Console.success(
                    "Discord conectado!"
            );


            Console.info(
                    "Bot: "
                            + jda.getSelfUser()
                            .getAsTag()
            );


            Console.info(
                    "Servidores: "
                            + jda.getGuilds()
                            .size()
            );


        } catch (Exception e) {

            Console.error(
                    e.getMessage()
            );

            e.printStackTrace();

        }

    }

}