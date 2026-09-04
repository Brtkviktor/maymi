package br.com.maymi.core.network.handler.impl;

import br.com.maymi.common.network.packet.TpsResponsePacket;
import br.com.maymi.core.discord.DiscordService;
import br.com.maymi.core.discord.interaction.InteractionResponseService;

public class TpsResponseHandler {

    private final DiscordService discordService;
    private final InteractionResponseService interactionResponseService;

    public TpsResponseHandler(
            DiscordService discordService,
            InteractionResponseService interactionResponseService
    ) {
        this.discordService = discordService;
        this.interactionResponseService = interactionResponseService;
    }

    public void handle(TpsResponsePacket packet) {

        boolean interactionAnswered =
                interactionResponseService.replyTps(
                        packet.getRequestId(),
                        packet.getTps()
                );

        if (interactionAnswered) {
            return;
        }

        discordService.sendTps(packet);
    }
}