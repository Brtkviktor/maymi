package br.com.maymi.paper.network.handler;

import br.com.maymi.common.network.packet.DiscordChatPacket;
import org.bukkit.Bukkit;

public class DiscordChatHandler {

    public void handle(DiscordChatPacket packet) {

        Bukkit.broadcastMessage(
                "§9[Discord] §f"
                        + packet.getAuthor()
                        + ": "
                        + packet.getMessage()
        );

    }

}