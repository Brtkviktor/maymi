package br.com.maymi.common.network.packet;

import br.com.maymi.common.network.AbstractPacket;
import br.com.maymi.common.network.PacketType;

import java.util.ArrayList;
import java.util.List;

public class ListResponsePacket extends AbstractPacket {

    private List<String> players = new ArrayList<>();

    public ListResponsePacket() {
        super(PacketType.LIST_RESPONSE);
    }

    public ListResponsePacket(List<String> players) {
        super(PacketType.LIST_RESPONSE);
        this.players = players;
    }

    public List<String> getPlayers() {
        return players;
    }

    public void setPlayers(List<String> players) {
        this.players = players;
    }

    @Override
    public String toString() {
        return """
                ==============================
                LIST RESPONSE
                ------------------------------
                Jogadores: %s
                ==============================
                """.formatted(players);
    }
}