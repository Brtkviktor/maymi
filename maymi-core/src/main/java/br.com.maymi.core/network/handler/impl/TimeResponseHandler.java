package br.com.maymi.core.network.handler.impl;

import br.com.maymi.common.network.packet.TimeResponsePacket;
import br.com.maymi.core.discord.DiscordService;
import br.com.maymi.core.discord.interaction.InteractionResponseService;

public class TimeResponseHandler {

    private final DiscordService discordService;
    private final InteractionResponseService interactionResponseService;

    public TimeResponseHandler(
            DiscordService discordService,
            InteractionResponseService interactionResponseService
    ) {
        this.discordService = discordService;
        this.interactionResponseService = interactionResponseService;
    }

    public void handle(TimeResponsePacket packet) {

        boolean interactionAnswered =
                interactionResponseService.replyTime(
                        packet.getRequestId(),
                        packet.getWorldName(),
                        packet.getDay(),
                        packet.getTime()
                );

        if (interactionAnswered) {
            return;
        }

        discordService.sendTime(packet);
    }
}