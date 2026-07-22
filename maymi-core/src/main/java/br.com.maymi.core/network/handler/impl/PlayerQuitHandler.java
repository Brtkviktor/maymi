package br.com.maymi.core.network.handler.impl;

import br.com.maymi.common.network.packet.PlayerQuitPacket;
import br.com.maymi.core.discord.DiscordService;

public class PlayerQuitHandler {

    private final DiscordService discordService =
            new DiscordService();

    public void handle(PlayerQuitPacket packet) {

        System.out.println("""
                ==============================
                PLAYER QUIT
                ------------------------------
                Jogador: %s
                ==============================
                """.formatted(packet.getPlayerName()));

        discordService.sendPlayerQuit(packet);

    }

}