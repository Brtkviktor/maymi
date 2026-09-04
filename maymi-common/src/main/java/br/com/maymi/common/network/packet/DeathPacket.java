package br.com.maymi.common.network.packet;

import br.com.maymi.common.network.AbstractPacket;
import br.com.maymi.common.network.PacketType;

public class DeathPacket extends AbstractPacket {

    private String playerUuid;
    private String playerName;
    private String deathMessage;

    private String deathCause;
    private String killerType;

    public DeathPacket() {
        super(PacketType.PLAYER_DEATH);
    }

    public DeathPacket(
            String playerUuid,
            String playerName,
            String deathMessage,
            String deathCause,
            String killerType
    ) {
        super(PacketType.PLAYER_DEATH);

        this.playerUuid = playerUuid;
        this.playerName = playerName;
        this.deathMessage = deathMessage;
        this.deathCause = deathCause;
        this.killerType = killerType;
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

    public String getDeathMessage() {
        return deathMessage;
    }

    public void setDeathMessage(String deathMessage) {
        this.deathMessage = deathMessage;
    }

    public String getDeathCause() {
        return deathCause;
    }

    public void setDeathCause(String deathCause) {
        this.deathCause = deathCause;
    }

    public String getKillerType() {
        return killerType;
    }

    public void setKillerType(String killerType) {
        this.killerType = killerType;
    }

    @Override
    public String toString() {
        return """
                ==============================
                PLAYER DEATH PACKET
                ------------------------------
                UUID: %s
                Jogador: %s
                Mensagem: %s
                Causa: %s
                Assassino: %s
                Tipo: %s
                ==============================
                """.formatted(
                playerUuid,
                playerName,
                deathMessage,
                deathCause,
                killerType,
                getType()
        );
    }
}