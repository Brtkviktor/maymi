package br.com.maymi.core.network.handler.impl;

import br.com.maymi.common.network.packet.TimeResponsePacket;
import br.com.maymi.core.discord.DiscordService;

public class TimeResponseHandler {

    private final DiscordService discordService;

    public TimeResponseHandler(
            DiscordService discordService
    ) {

        this.discordService =
                discordService;

    }

    public void handle(
            TimeResponsePacket packet
    ) {

        System.out.println("""
                ==============================
                TIME RESPONSE
                ------------------------------
                Mundo: %s
                Dia: %d
                Tempo: %d
                ==============================
                """.formatted(
                packet.getWorldName(),
                packet.getDay(),
                packet.getTime()
        ));

        discordService.sendTime(
                packet
        );

    }

}