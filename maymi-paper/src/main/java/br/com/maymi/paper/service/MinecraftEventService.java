package br.com.maymi.paper.service;

import br.com.maymi.common.network.model.PlayerJoinMessage;

public class MinecraftEventService {

    public void playerJoined(PlayerJoinMessage message) {

        System.out.println("==================================");
        System.out.println("Novo evento recebido!");
        System.out.println("Jogador: " + message.getPlayerName());
        System.out.println("UUID: " + message.getPlayerId());
        System.out.println("Horário: " + message.getJoinedAt());
        System.out.println("==================================");

    }

}