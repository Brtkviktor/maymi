package br.com.maymi.core.network.handler.impl;

import br.com.maymi.common.network.packet.PlayerJoinPacket;
import br.com.maymi.core.discord.DiscordService;
import br.com.maymi.core.network.handler.PacketHandler;

public class PlayerJoinHandler implements PacketHandler<PlayerJoinPacket> {

    private final DiscordService discordService =
            new DiscordService();

    @Override
    public void handle(PlayerJoinPacket packet) {

        discordService.sendPlayerJoin(packet);

    }

}