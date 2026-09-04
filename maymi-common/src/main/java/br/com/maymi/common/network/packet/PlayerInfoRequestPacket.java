package br.com.maymi.common.network.packet;

import br.com.maymi.common.network.AbstractPacket;
import br.com.maymi.common.network.PacketType;

public class PlayerInfoRequestPacket extends AbstractPacket {

    private String playerName;
    private String requestId;

    public PlayerInfoRequestPacket() {
        super(PacketType.PLAYER_INFO_REQUEST);
    }

    public PlayerInfoRequestPacket(
            String playerName,
            String requestId
    ) {

        super(PacketType.PLAYER_INFO_REQUEST);

        this.playerName = playerName;
        this.requestId = requestId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(
            String playerName
    ) {
        this.playerName = playerName;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(
            String requestId
    ) {
        this.requestId = requestId;
    }

    @Override
    public String toString() {
        return """
                ==============================
                PLAYER INFO REQUEST
                ------------------------------
                Player: %s
                Request ID: %s
                ==============================
                """.formatted(
                playerName,
                requestId
        );
    }
}