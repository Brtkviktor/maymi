package br.com.maymi.core.network.handler.impl;

import br.com.maymi.common.network.packet.RamResponsePacket;
import br.com.maymi.core.discord.DiscordService;
import br.com.maymi.core.discord.interaction.InteractionResponseService;

public class RamResponseHandler {

    private final DiscordService discordService;
    private final InteractionResponseService interactionResponseService;

    public RamResponseHandler(
            DiscordService discordService,
            InteractionResponseService interactionResponseService
    ) {
        this.discordService = discordService;
        this.interactionResponseService = interactionResponseService;
    }

    public void handle(RamResponsePacket packet) {

        boolean interactionAnswered =
                interactionResponseService.replyRam(
                        packet.getRequestId(),
                        packet.getUsedMemory(),
                        packet.getFreeMemory(),
                        packet.getMaxMemory()
                );

        if (interactionAnswered) {
            return;
        }

        discordService.sendRam(packet);
    }
}