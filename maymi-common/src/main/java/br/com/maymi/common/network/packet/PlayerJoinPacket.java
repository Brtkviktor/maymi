package br.com.maymi.common.network.packet;

import br.com.maymi.common.network.Packet;
import br.com.maymi.common.network.PacketType;

public class PlayerJoinPacket implements Packet {

    private final String playerName;

    public PlayerJoinPacket(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }

    @Override
    public PacketType getType() {
        return PacketType.PLAYER_JOIN;
    }

}