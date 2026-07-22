package br.com.maymi.common.network.packet;

import br.com.maymi.common.network.AbstractPacket;
import br.com.maymi.common.network.PacketType;

public class PlayerQuitPacket extends AbstractPacket {

    private String playerName;

    public PlayerQuitPacket() {
        super(PacketType.PLAYER_QUIT);
    }

    public PlayerQuitPacket(String playerName) {
        super(PacketType.PLAYER_QUIT);
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

}