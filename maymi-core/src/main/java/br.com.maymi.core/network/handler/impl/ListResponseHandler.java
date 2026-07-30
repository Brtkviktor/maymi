package br.com.maymi.core.network.handler.impl;

import br.com.maymi.common.network.packet.ListResponsePacket;
import br.com.maymi.core.discord.DiscordService;

public class ListResponseHandler {

    private final DiscordService discordService;

    public ListResponseHandler(
            DiscordService discordService
    ) {

        this.discordService =
                discordService;

    }

    public void handle(
            ListResponsePacket packet
    ) {

        System.out.println("""
                ==============================
                LIST RESPONSE
                ------------------------------
                Jogadores online: %d
                Jogadores: %s
                ==============================
                """.formatted(
                packet.getPlayers().size(),
                packet.getPlayers()
        ));

        discordService.sendPlayerList(
                packet
        );

    }

}