package br.com.maymi.common.network.packet;

import br.com.maymi.common.network.AbstractPacket;
import br.com.maymi.common.network.PacketType;

public class MobKillPacket extends AbstractPacket {

    private String playerUuid;
    private String playerName;
    private String mobType;
    private String mobName;

    public MobKillPacket() {
        super(PacketType.MOB_KILL);
    }

    public MobKillPacket(
            String playerUuid,
            String playerName,
            String mobType,
            String mobName
    ) {
        super(PacketType.MOB_KILL);

        this.playerUuid = playerUuid;
        this.playerName = playerName;
        this.mobType = mobType;
        this.mobName = mobName;
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

    public String getMobType() {
        return mobType;
    }

    public void setMobType(String mobType) {
        this.mobType = mobType;
    }

    public String getMobName() {
        return mobName;
    }

    public void setMobName(String mobName) {
        this.mobName = mobName;
    }

    @Override
    public String toString() {
        return """
                ==============================
                MOB KILL PACKET
                ------------------------------
                UUID: %s
                Jogador: %s
                Mob Type: %s
                Mob Name: %s
                Tipo: %s
                ==============================
                """.formatted(
                playerUuid,
                playerName,
                mobType,
                mobName,
                getType()
        );
    }
}