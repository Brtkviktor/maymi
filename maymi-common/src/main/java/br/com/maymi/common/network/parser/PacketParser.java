package br.com.maymi.common.network.parser;

import br.com.maymi.common.network.Packet;
import br.com.maymi.common.network.PacketType;
import br.com.maymi.common.network.packet.PlayerJoinPacket;
import br.com.maymi.common.network.packet.PlayerQuitPacket;

public class PacketParser {

    public Packet parse(String message) {

        String[] parts = message.split(";", -1);

        if (parts.length == 0 || parts[0].isBlank()) {
            throw new IllegalArgumentException(
                    "Mensagem de pacote vazia ou inválida."
            );
        }

        PacketType type;

        try {
            type = PacketType.valueOf(parts[0]);
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException(
                    "Tipo de pacote inválido: " + parts[0],
                    exception
            );
        }

        return switch (type) {

            case PLAYER_JOIN ->
                    parsePlayerJoin(parts);

            case PLAYER_QUIT ->
                    parsePlayerQuit(parts);

            default -> throw new IllegalArgumentException(
                    "Pacote desconhecido: " + type
            );
        };
    }

    private PlayerJoinPacket parsePlayerJoin(
            String[] parts
    ) {

        validatePlayerPacket(
                parts,
                "PLAYER_JOIN"
        );

        String playerUuid = parts[1];
        String playerName = parts[2];

        return new PlayerJoinPacket(
                playerUuid,
                playerName
        );
    }

    private PlayerQuitPacket parsePlayerQuit(
            String[] parts
    ) {

        validatePlayerPacket(
                parts,
                "PLAYER_QUIT"
        );

        String playerUuid = parts[1];
        String playerName = parts[2];

        return new PlayerQuitPacket(
                playerUuid,
                playerName
        );
    }

    private void validatePlayerPacket(
            String[] parts,
            String packetType
    ) {

        if (parts.length < 3) {
            throw new IllegalArgumentException(
                    packetType
                            + " inválido. Formato esperado: "
                            + packetType
                            + ";uuid;nome"
            );
        }

        if (parts[1].isBlank()) {
            throw new IllegalArgumentException(
                    "UUID do jogador não pode estar vazio."
            );
        }

        if (parts[2].isBlank()) {
            throw new IllegalArgumentException(
                    "Nome do jogador não pode estar vazio."
            );
        }
    }
}