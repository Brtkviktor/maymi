package br.com.maymi.core.discord;

import br.com.maymi.common.network.packet.ChatPacket;
import br.com.maymi.common.network.packet.DeathPacket;
import br.com.maymi.common.network.packet.ListResponsePacket;
import br.com.maymi.common.network.packet.PlayerJoinPacket;
import br.com.maymi.common.network.packet.PlayerQuitPacket;
import br.com.maymi.common.network.packet.RamResponsePacket;
import br.com.maymi.common.network.packet.TimeResponsePacket;
import br.com.maymi.common.network.packet.TpsResponsePacket;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

public class DiscordService {

    private final DiscordChannelManager channelManager;

    public DiscordService(
            DiscordChannelManager channelManager
    ) {

        this.channelManager =
                channelManager;

    }

    public void sendPlayerJoin(
            PlayerJoinPacket packet
    ) {

        TextChannel channel =
                channelManager.getJoinLogsChannel();

        sendMessage(
                channel,
                "🟢 **"
                        + packet.getPlayerName()
                        + "** entrou no servidor!",
                "Join Logs"
        );

    }

    public void sendPlayerQuit(
            PlayerQuitPacket packet
    ) {

        TextChannel channel =
                channelManager.getJoinLogsChannel();

        sendMessage(
                channel,
                "🔴 **"
                        + packet.getPlayerName()
                        + "** saiu do servidor!",
                "Join Logs"
        );

    }

    public void sendChat(
            ChatPacket packet
    ) {

        TextChannel channel =
                channelManager.getChatChannel();

        sendMessage(
                channel,
                "💬 **"
                        + packet.getPlayerName()
                        + "**: "
                        + packet.getMessage(),
                "Chat"
        );

    }

    public void sendDeath(
            DeathPacket packet
    ) {

        TextChannel channel =
                channelManager.getDeathLogsChannel();

        sendMessage(
                channel,
                "💀 **"
                        + packet.getPlayerName()
                        + "** "
                        + packet.getDeathMessage(),
                "Death Logs"
        );

    }

    public void sendPlayerList(
            ListResponsePacket packet
    ) {

        TextChannel channel =
                channelManager.getCommandChannel();

        StringBuilder message =
                new StringBuilder();

        message.append(
                "👥 **Jogadores Online ("
        );

        message.append(
                packet.getPlayers().size()
        );

        message.append(
                ")**\n\n"
        );

        for (
                String player :
                packet.getPlayers()
        ) {

            message.append("• ")
                    .append(player)
                    .append("\n");

        }

        sendMessage(
                channel,
                message.toString(),
                "Comandos"
        );

    }

    public void sendTps(
            TpsResponsePacket packet
    ) {

        TextChannel channel =
                channelManager.getCommandChannel();

        String message =
                """
                📊 **TPS DO SERVIDOR**

                TPS: %.2f
                """.formatted(
                        packet.getTps()
                );

        sendMessage(
                channel,
                message,
                "Comandos"
        );

    }

    public void sendRam(
            RamResponsePacket packet
    ) {

        TextChannel channel =
                channelManager.getCommandChannel();

        String message =
                """
                🖥 **MEMÓRIA DO SERVIDOR**

                Usada : %.2f GB
                Livre : %.2f GB
                Máxima: %.2f GB
                """.formatted(
                        packet.getUsedMemory(),
                        packet.getFreeMemory(),
                        packet.getMaxMemory()
                );

        sendMessage(
                channel,
                message,
                "Comandos"
        );

    }

    public void sendTime(
            TimeResponsePacket packet
    ) {

        TextChannel channel =
                channelManager.getCommandChannel();

        long time =
                packet.getTime();

        long hours =
                ((time / 1000) + 6) % 24;

        long minutes =
                (time % 1000) * 60 / 1000;

        String message =
                """
                🌍 **INFORMAÇÕES DO MUNDO**

                Mundo: **%s**
                Dia: **%d**
                Horário: **%02d:%02d**
                """.formatted(
                        packet.getWorldName(),
                        packet.getDay(),
                        hours,
                        minutes
                );

        sendMessage(
                channel,
                message,
                "Comandos"
        );

    }

    private void sendMessage(
            TextChannel channel,
            String message,
            String channelName
    ) {

        if (channel == null) {

            System.out.println(
                    "Canal de "
                            + channelName
                            + " não encontrado."
            );

            return;
        }

        channel.sendMessage(
                message
        ).queue();

    }

}