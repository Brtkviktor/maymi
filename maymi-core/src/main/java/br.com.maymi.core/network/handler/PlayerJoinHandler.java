package br.com.maymi.core.network.handler;

import br.com.maymi.common.network.packet.PlayerJoinPacket;

public class PlayerJoinHandler implements PacketHandler<PlayerJoinPacket> {

    @Override
    public void handle(PlayerJoinPacket packet) {

        System.out.println();
        System.out.println("================================");
        System.out.println("NOVO JOGADOR");
        System.out.println("--------------------------------");
        System.out.println("Nome: " + packet.getPlayerName());
        System.out.println("================================");
        System.out.println();

    }
}