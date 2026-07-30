package br.com.maymi.core.startup;

import br.com.maymi.core.discord.DiscordChannelManager;
import br.com.maymi.core.discord.DiscordService;
import br.com.maymi.core.discord.bot.DiscordBot;
import br.com.maymi.core.network.dispatcher.PacketDispatcher;
import br.com.maymi.core.socket.SocketServer;

public class MaymiApplication {

    public static void start() {

        System.out.println("""
                 __  __
                |  \\/  |
                | \\  / | __ _ _   _ _ __ ___  _ __
                | |\\/| |/ _` | | | | '_ ` _ \\| '_ \\
                | |  | | (_| | |_| | | | | | | |
                |_|  |_|\\__,_|\\__, |_| |_| |_|_| |_|
                               __/ |
                              |___/

                """);

        System.out.println(
                "================================="
        );

        System.out.println(
                "MAYMI v0.3.0"
        );

        System.out.println(
                "================================="
        );

        // =====================================================
        // DISCORD BOT
        // =====================================================

        DiscordBot bot =
                new DiscordBot();

        bot.start();


        // =====================================================
        // DISCORD
        // =====================================================

        DiscordChannelManager channelManager =
                new DiscordChannelManager();

        DiscordService discordService =
                new DiscordService(
                        channelManager
                );


        // =====================================================
        // PACKET DISPATCHER
        // =====================================================

        PacketDispatcher dispatcher =
                new PacketDispatcher(
                        discordService
                );


        // =====================================================
        // SOCKET SERVER
        // =====================================================

        SocketServer socketServer =
                new SocketServer(
                        dispatcher
                );

        new Thread(
                socketServer::start
        ).start();

    }

}