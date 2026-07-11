package br.com.maymi.common.network.packet;

import br.com.maymi.common.network.AbstractPacket;
import br.com.maymi.common.network.PacketType;

public class PlayerJoinPacket extends AbstractPacket {

    private String playerName;

    public PlayerJoinPacket() {
        super(PacketType.PLAYER_JOIN);
    }

    public PlayerJoinPacket(String playerName) {
        super(PacketType.PLAYER_JOIN);
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    @Override
    public String toString() {
        return """
            ==============================
            PLAYER JOIN PACKET
            ------------------------------
            Nome: %s
            Tipo: %s
            ==============================
            """.formatted(
                playerName,
                getType()
        );
    }

}