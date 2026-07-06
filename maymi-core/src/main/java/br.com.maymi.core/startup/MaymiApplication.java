package br.com.maymi.core.startup;

import br.com.maymi.core.discord.bot.DiscordBot;

public class MaymiApplication {

    public static void start() {

        System.out.println("""
                 __  __
                |  \\/  |
                | \\  / | __ _ _   _ _ __ ___  _ __
                | |\\/| |/ _` | | | | '_ ` _ \\| '_ \\
                | |  | | (_| | |_| | | | | | | | | |
                |_|  |_|\\__,_|\\__, |_| |_| |_|_| |_|
                               __/ |
                              |___/

                """);

        System.out.println("=================================");
        System.out.println("MAYMI v0.1.0");
        System.out.println("=================================");

        DiscordBot bot = new DiscordBot();

        bot.start();

    }

}