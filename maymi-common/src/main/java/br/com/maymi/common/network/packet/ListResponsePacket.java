package br.com.maymi.common.network.packet;

import br.com.maymi.common.network.AbstractPacket;
import br.com.maymi.common.network.PacketType;

import java.util.ArrayList;
import java.util.List;

public class ListResponsePacket extends AbstractPacket {

    private List<String> players =
            new ArrayList<>();

    private String requestId;

    public ListResponsePacket() {
        super(PacketType.LIST_RESPONSE);
    }

    public ListResponsePacket(
            List<String> players
    ) {

        this(players, null);
    }

    public ListResponsePacket(
            List<String> players,
            String requestId
    ) {

        super(PacketType.LIST_RESPONSE);

        this.players = players;
        this.requestId = requestId;
    }

    public List<String> getPlayers() {
        return players;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setPlayers(
            List<String> players
    ) {

        this.players = players;
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
                LIST RESPONSE
                ------------------------------
                Jogadores: %s
                Request ID: %s
                ==============================
                """.formatted(
                players,
                requestId
        );
    }
}