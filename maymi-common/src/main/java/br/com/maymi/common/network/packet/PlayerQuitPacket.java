package br.com.maymi.common.network.packet;

import br.com.maymi.common.network.Packet;
import br.com.maymi.common.network.PacketType;

public class PlayerQuitPacket implements Packet {

    private final String playerName;

    public PlayerQuitPacket(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }

    @Override
    public PacketType getType() {
        return PacketType.PLAYER_QUIT;
    }

}