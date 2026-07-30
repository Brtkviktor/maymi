package br.com.maymi.core.discord;

import br.com.maymi.core.configuration.ConfigurationManager;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

public class DiscordChannelManager {


    public TextChannel getCommandChannel() {

        String channelId =
                ConfigurationManager.getCommandChannel();

        System.out.println(
                "[DiscordChannelManager] Command Channel ID: "
                        + channelId
        );

        TextChannel channel =
                DiscordManager.getJda()
                        .getTextChannelById(
                                channelId
                        );

        System.out.println(
                "[DiscordChannelManager] Command Channel: "
                        + (channel != null
                        ? channel.getName()
                        : "NULL")
        );

        return channel;

    }


    public TextChannel getJoinLogsChannel() {

        String channelId =
                ConfigurationManager.getJoinLogsChannel();

        System.out.println(
                "[DiscordChannelManager] Join Logs Channel ID: "
                        + channelId
        );

        TextChannel channel =
                DiscordManager.getJda()
                        .getTextChannelById(
                                channelId
                        );

        System.out.println(
                "[DiscordChannelManager] Join Logs Channel: "
                        + (channel != null
                        ? channel.getName()
                        : "NULL")
        );

        return channel;

    }


    public TextChannel getChatChannel() {

        String channelId =
                ConfigurationManager.getChatChannel();

        System.out.println(
                "[DiscordChannelManager] Chat Channel ID: "
                        + channelId
        );

        TextChannel channel =
                DiscordManager.getJda()
                        .getTextChannelById(
                                channelId
                        );

        System.out.println(
                "[DiscordChannelManager] Chat Channel: "
                        + (channel != null
                        ? channel.getName()
                        : "NULL")
        );

        return channel;

    }


    public TextChannel getDeathLogsChannel() {

        String channelId =
                ConfigurationManager.getDeathLogsChannel();

        System.out.println(
                "[DiscordChannelManager] Death Logs Channel ID: "
                        + channelId
        );

        TextChannel channel =
                DiscordManager.getJda()
                        .getTextChannelById(
                                channelId
                        );

        System.out.println(
                "[DiscordChannelManager] Death Logs Channel: "
                        + (channel != null
                        ? channel.getName()
                        : "NULL")
        );

        return channel;

    }

}