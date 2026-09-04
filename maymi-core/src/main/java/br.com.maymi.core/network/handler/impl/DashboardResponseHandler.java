package br.com.maymi.core.network.handler.impl;

import br.com.maymi.common.network.packet.DashboardResponsePacket;
import br.com.maymi.core.discord.interaction.InteractionResponseService;

public class DashboardResponseHandler {

    private final InteractionResponseService interactionResponseService;

    public DashboardResponseHandler(
            InteractionResponseService interactionResponseService
    ) {

        this.interactionResponseService =
                interactionResponseService;
    }

    public void handle(
            DashboardResponsePacket packet
    ) {

        boolean interactionAnswered =
                interactionResponseService.replyDashboard(
                        packet.getRequestId(),
                        packet.getTps(),
                        packet.getMspt(),
                        packet.getUsedMemory(),
                        packet.getMaxMemory(),
                        packet.getOnlinePlayers(),
                        packet.getMaxPlayers(),
                        packet.getWorldName(),
                        packet.getDay(),
                        packet.getTime(),
                        packet.getUptime()
                );

        if (!interactionAnswered) {

            System.out.println(
                    "Nenhuma interação pendente encontrada "
                            + "para o dashboard. Request ID: "
                            + packet.getRequestId()
            );
        }
    }
}