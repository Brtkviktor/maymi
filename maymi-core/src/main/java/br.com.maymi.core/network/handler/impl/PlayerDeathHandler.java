package br.com.maymi.core.network.handler.impl;

import br.com.maymi.common.network.packet.DeathPacket;
import br.com.maymi.core.discord.DiscordService;

public class PlayerDeathHandler {

    private final DiscordService discordService =
            new DiscordService();

    public void handle(DeathPacket packet) {

        System.out.println("""
                ==============================
                PLAYER DEATH
                ------------------------------
                Jogador: %s
                Mensagem: %s
                ==============================
                """.formatted(
                packet.getPlayerName(),
                packet.getDeathMessage()
        ));

        discordService.sendDeath(packet);

    }

}