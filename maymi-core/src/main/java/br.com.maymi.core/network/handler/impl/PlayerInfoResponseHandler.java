package br.com.maymi.core.network.handler.impl;

import br.com.maymi.common.network.packet.PlayerInfoResponsePacket;
import br.com.maymi.core.discord.interaction.InteractionResponseService;

public class PlayerInfoResponseHandler {

    private final InteractionResponseService interactionResponseService;

    public PlayerInfoResponseHandler(
            InteractionResponseService interactionResponseService
    ) {
        this.interactionResponseService =
                interactionResponseService;
    }

    public void handle(
            PlayerInfoResponsePacket packet
    ) {

        boolean interactionAnswered =
                interactionResponseService.replyPlayerInfo(
                        packet.getRequestId(),
                        packet.isFound(),
                        packet.isOnline(),
                        packet.getPlayerName(),
                        packet.getUuid(),
                        packet.getHealth(),
                        packet.getMaxHealth(),
                        packet.getFoodLevel(),
                        packet.getExperienceLevel(),
                        packet.getWorldName(),
                        packet.getX(),
                        packet.getY(),
                        packet.getZ(),
                        packet.getGameMode(),
                        packet.getSessionTime()
                );

        if (!interactionAnswered) {

            System.out.println(
                    "Nenhuma interação pendente encontrada "
                            + "para o jogador. Request ID: "
                            + packet.getRequestId()
            );
        }
    }
}