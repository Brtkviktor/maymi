package br.com.maymi.common.network.packet;

import br.com.maymi.common.network.AbstractPacket;
import br.com.maymi.common.network.PacketType;

public class BlockBreakPacket extends AbstractPacket {

    private String playerUuid;
    private String playerName;
    private String blockType;
    private String worldName;

    private int x;
    private int y;
    private int z;

    public BlockBreakPacket() {
        super(PacketType.BLOCK_BREAK);
    }

    public BlockBreakPacket(
            String playerUuid,
            String playerName,
            String blockType,
            String worldName,
            int x,
            int y,
            int z
    ) {
        super(PacketType.BLOCK_BREAK);

        this.playerUuid = playerUuid;
        this.playerName = playerName;
        this.blockType = blockType;
        this.worldName = worldName;
        this.x = x;
        this.y = y;
        this.z = z;
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

    public String getBlockType() {
        return blockType;
    }

    public void setBlockType(String blockType) {
        this.blockType = blockType;
    }

    public String getWorldName() {
        return worldName;
    }

    public void setWorldName(String worldName) {
        this.worldName = worldName;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    @Override
    public String toString() {
        return """
                ==============================
                BLOCK BREAK PACKET
                ------------------------------
                UUID: %s
                Jogador: %s
                Bloco: %s
                Mundo: %s
                Posição: %d, %d, %d
                Tipo: %s
                ==============================
                """.formatted(
                playerUuid,
                playerName,
                blockType,
                worldName,
                x,
                y,
                z,
                getType()
        );
    }
}