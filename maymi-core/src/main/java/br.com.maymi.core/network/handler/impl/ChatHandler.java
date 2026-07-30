package br.com.maymi.core.network.handler.impl;

import br.com.maymi.common.network.packet.ChatPacket;
import br.com.maymi.core.discord.DiscordService;

public class ChatHandler {

    private final DiscordService discordService;

    public ChatHandler(
            DiscordService discordService
    ) {

        this.discordService =
                discordService;

    }

    public void handle(
            ChatPacket packet
    ) {

        System.out.println("""
                ==============================
                CHAT RECEBIDO
                ------------------------------
                Jogador: %s
                Mensagem: %s
                ==============================
                """.formatted(
                packet.getPlayerName(),
                packet.getMessage()
        ));

        discordService.sendChat(
                packet
        );

    }

}