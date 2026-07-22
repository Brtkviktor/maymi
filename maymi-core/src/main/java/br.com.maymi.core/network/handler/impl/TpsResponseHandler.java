package br.com.maymi.core.network.handler.impl;

import br.com.maymi.common.network.packet.TpsResponsePacket;
import br.com.maymi.core.discord.DiscordService;

public class TpsResponseHandler {

    private final DiscordService discordService =
            new DiscordService();

    public void handle(TpsResponsePacket packet) {

        System.out.println(packet);

        discordService.sendTps(packet);

    }

}