package br.com.maymi.core.network.handler.impl;

import br.com.maymi.common.network.packet.RamResponsePacket;
import br.com.maymi.core.discord.DiscordService;

public class RamResponseHandler {


    private final DiscordService discordService =
            new DiscordService();

    public void handle(RamResponsePacket packet){

        System.out.println(packet);

        discordService.sendRam(packet);

    }
}
