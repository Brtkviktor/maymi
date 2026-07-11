package br.com.maymi.paper.service;

import br.com.maymi.common.network.model.PlayerJoinMessage;
import br.com.maymi.common.network.packet.PlayerJoinPacket;
import br.com.maymi.paper.socket.SocketClient;

public class MinecraftEventService {

    private final SocketClient socketClient = new SocketClient();

    public void playerJoined(PlayerJoinMessage message) {

        System.out.println("==================================");
        System.out.println("Novo evento recebido!");
        System.out.println("Jogador: " + message.getPlayerName());
        System.out.println("UUID: " + message.getPlayerId());
        System.out.println("Horário: " + message.getJoinedAt());
        System.out.println("==================================");

        PlayerJoinPacket packet =
                new PlayerJoinPacket(
                        message.getPlayerName()
                );

        socketClient.send(packet);

    }

}