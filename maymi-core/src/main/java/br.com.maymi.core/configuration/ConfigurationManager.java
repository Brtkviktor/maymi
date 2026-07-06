package br.com.maymi.core.configuration;

import io.github.cdimascio.dotenv.Dotenv;

public final class ConfigurationManager {

    private static final Dotenv dotenv = Dotenv.load();

    private ConfigurationManager(){}

    public static String getDiscordToken() {
        return dotenv.get("DISCORD_TOKEN");
    }

}