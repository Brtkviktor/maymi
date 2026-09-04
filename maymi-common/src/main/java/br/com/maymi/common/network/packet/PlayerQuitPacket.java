package br.com.maymi.common.network.packet;

import br.com.maymi.common.network.AbstractPacket;
import br.com.maymi.common.network.PacketType;

public class PlayerQuitPacket extends AbstractPacket {

    private String playerUuid;
    private String playerName;

    public PlayerQuitPacket() {
        super(PacketType.PLAYER_QUIT);
    }

    public PlayerQuitPacket(
            String playerUuid,
            String playerName
    ) {
        super(PacketType.PLAYER_QUIT);

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
                PLAYER QUIT PACKET
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