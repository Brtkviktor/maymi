package br.com.maymi.common.network.packet;

import br.com.maymi.common.network.AbstractPacket;
import br.com.maymi.common.network.PacketType;

public class PlayerJoinPacket extends AbstractPacket {

    private String playerUuid;
    private String playerName;

    public PlayerJoinPacket() {
        super(PacketType.PLAYER_JOIN);
    }

    public PlayerJoinPacket(
            String playerUuid,
            String playerName
    ) {
        super(PacketType.PLAYER_JOIN);

        this.playerUuid = playerUuid;
        this.playerName = playerName;
    }

    

    public String getPlayerUuid() {
        return playerUuid;
    }

    public void setPlayerUuid(String playerUuid) {
        this.playerUuid = playerUuid;
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
                UUID: %s
                Nome: %s
                Tipo: %s
                ==============================
                """.formatted(
                playerUuid,
                playerName,
                getType()
        );
    }
}