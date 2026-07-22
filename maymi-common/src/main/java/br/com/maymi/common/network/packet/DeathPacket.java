package br.com.maymi.common.network.packet;

import br.com.maymi.common.network.AbstractPacket;
import br.com.maymi.common.network.PacketType;

public class DeathPacket extends AbstractPacket {

    private String playerName;
    private String deathMessage;

    public DeathPacket() {
        super(PacketType.PLAYER_DEATH);
    }

    public DeathPacket(String playerName, String deathMessage) {
        super(PacketType.PLAYER_DEATH);
        this.playerName = playerName;
        this.deathMessage = deathMessage;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getDeathMessage() {
        return deathMessage;
    }

    public void setDeathMessage(String deathMessage) {
        this.deathMessage = deathMessage;
    }

}
