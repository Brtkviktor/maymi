package br.com.maymi.core.discord;

import net.dv8tion.jda.api.JDA;

public final class DiscordManager {

    private static JDA jda;

    private DiscordManager() {
    }

    public static void setJda(JDA instance) {

        jda = instance;

    }

    public static JDA getJda() {

        return jda;

    }

}