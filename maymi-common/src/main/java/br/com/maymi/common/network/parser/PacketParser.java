package br.com.maymi.common.network.parser;

import br.com.maymi.common.network.Packet;
import br.com.maymi.common.network.PacketType;
import br.com.maymi.common.network.packet.PlayerJoinPacket;

public class PacketParser {

    public Packet parse(String message) {

        String[] parts = message.split(";");

        PacketType type = PacketType.valueOf(parts[0]);

        switch (type) {

            case PLAYER_JOIN:
                return new PlayerJoinPacket(parts[1]);

            default:
                throw new IllegalArgumentException(
                        "Pacote desconhecido: " + type
                );

        }

    }

}