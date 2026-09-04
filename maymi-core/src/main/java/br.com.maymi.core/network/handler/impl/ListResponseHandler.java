package br.com.maymi.core.network.handler.impl;

import br.com.maymi.common.network.packet.ListResponsePacket;
import br.com.maymi.core.discord.DiscordService;
import br.com.maymi.core.discord.interaction.InteractionResponseService;

public class ListResponseHandler {

    private final DiscordService discordService;
    private final InteractionResponseService interactionResponseService;

    public ListResponseHandler(
            DiscordService discordService,
            InteractionResponseService interactionResponseService
    ) {
        this.discordService = discordService;
        this.interactionResponseService = interactionResponseService;
    }

    public void handle(ListResponsePacket packet) {

        boolean interactionAnswered =
                interactionResponseService.replyPlayers(
                        packet.getRequestId(),
                        packet.getPlayers()
                );

        if (interactionAnswered) {
            return;
        }

        discordService.sendPlayerList(packet);
    }
}