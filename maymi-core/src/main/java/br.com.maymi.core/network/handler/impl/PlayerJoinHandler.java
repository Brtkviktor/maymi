package br.com.maymi.core.network.handler.impl;

import br.com.maymi.common.network.packet.PlayerJoinPacket;
import br.com.maymi.core.discord.DiscordService;
import br.com.maymi.core.network.handler.PacketHandler;

public class PlayerJoinHandler
        implements PacketHandler<PlayerJoinPacket> {

    private final DiscordService discordService;

    public PlayerJoinHandler(
            DiscordService discordService
    ) {

        this.discordService =
                discordService;

    }

    @Override
    public void handle(
            PlayerJoinPacket packet
    ) {

        System.out.println();
        System.out.println("================================");
        System.out.println("NOVO JOGADOR");
        System.out.println("--------------------------------");
        System.out.println(
                "Nome: "
                        + packet.getPlayerName()
        );
        System.out.println("================================");
        System.out.println();

        discordService.sendPlayerJoin(
                packet
        );

    }

}