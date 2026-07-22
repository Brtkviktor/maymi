package br.com.maymi.core.network.handler.impl;

import br.com.maymi.common.network.packet.ListResponsePacket;
import br.com.maymi.core.discord.DiscordService;

public class ListResponseHandler {

    private final DiscordService discordService =
            new DiscordService();

    public void handle(ListResponsePacket packet) {

        discordService.sendPlayerList(packet);

    }

}