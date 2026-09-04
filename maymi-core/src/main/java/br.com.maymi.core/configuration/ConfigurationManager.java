package br.com.maymi.core.configuration;

import io.github.cdimascio.dotenv.Dotenv;

public final class ConfigurationManager {

    private static final Dotenv dotenv = Dotenv.load();

    private ConfigurationManager() {
    }

    public static String getDiscordToken() {
        return dotenv.get("DISCORD_TOKEN");
    }

    public static String getCommandChannel() {
        return dotenv.get("DISCORD_CHANNEL_COMMANDS");
    }

    public static String getJoinLogsChannel() {
        return dotenv.get("DISCORD_CHANNEL_JOIN_LOGS");
    }

    public static String getChatChannel() {
        return dotenv.get("DISCORD_CHANNEL_CHAT");
    }

    public static String getDeathLogsChannel() {
        return dotenv.get("DISCORD_CHANNEL_DEATH_LOGS");
    }

    public static int getCorePort() {
        return Integer.parseInt(
                dotenv.get("MAYMI_CORE_PORT")
        );
    }

    public static int getPaperPort() {
        return Integer.parseInt(
                dotenv.get("MAYMI_PAPER_PORT")
        );
    }

    public static String getDashboardChannel() {
        return dotenv.get("DISCORD_CHANNEL_DASHBOARD");
    }

    public static long getDashboardUpdateSeconds() {

        String value =
                dotenv.get(
                        "DASHBOARD_UPDATE_SECONDS",
                        "30"
                );

        return Long.parseLong(value);
    }

    public static String getDashboardMessageId() {

        return dotenv.get(
                "DISCORD_DASHBOARD_MESSAGE_ID",
                ""
        );
    }

    public static String getLevelLogsChannel() {
        return dotenv.get(
                "DISCORD_CHANNEL_LEVEL_LOGS"
        );
    }

    public static String getAchievementChannelId() {

        return dotenv.get(
                "DISCORD_CHANNEL_ACHIEVEMENTS"
        );
    }

}