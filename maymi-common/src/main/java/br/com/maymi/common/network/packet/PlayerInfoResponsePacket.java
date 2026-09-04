package br.com.maymi.common.network.packet;

import br.com.maymi.common.network.AbstractPacket;
import br.com.maymi.common.network.PacketType;

public class PlayerInfoResponsePacket extends AbstractPacket {

    private boolean found;
    private boolean online;

    private String playerName;
    private String uuid;

    private double health;
    private double maxHealth;

    private int foodLevel;
    private int experienceLevel;

    private String worldName;

    private double x;
    private double y;
    private double z;

    private String gameMode;

    private long sessionTime;

    private String requestId;

    public PlayerInfoResponsePacket() {
        super(PacketType.PLAYER_INFO_RESPONSE);
    }

    public PlayerInfoResponsePacket(
            boolean found,
            boolean online,
            String playerName,
            String uuid,
            double health,
            double maxHealth,
            int foodLevel,
            int experienceLevel,
            String worldName,
            double x,
            double y,
            double z,
            String gameMode,
            long sessionTime,
            String requestId
    ) {

        super(PacketType.PLAYER_INFO_RESPONSE);

        this.found = found;
        this.online = online;
        this.playerName = playerName;
        this.uuid = uuid;
        this.health = health;
        this.maxHealth = maxHealth;
        this.foodLevel = foodLevel;
        this.experienceLevel = experienceLevel;
        this.worldName = worldName;
        this.x = x;
        this.y = y;
        this.z = z;
        this.gameMode = gameMode;
        this.sessionTime = sessionTime;
        this.requestId = requestId;
    }

    public boolean isFound() {
        return found;
    }

    public void setFound(
            boolean found
    ) {
        this.found = found;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(
            boolean online
    ) {
        this.online = online;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(
            String playerName
    ) {
        this.playerName = playerName;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(
            String uuid
    ) {
        this.uuid = uuid;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(
            double health
    ) {
        this.health = health;
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(
            double maxHealth
    ) {
        this.maxHealth = maxHealth;
    }

    public int getFoodLevel() {
        return foodLevel;
    }

    public void setFoodLevel(
            int foodLevel
    ) {
        this.foodLevel = foodLevel;
    }

    public int getExperienceLevel() {
        return experienceLevel;
    }

    public void setExperienceLevel(
            int experienceLevel
    ) {
        this.experienceLevel = experienceLevel;
    }

    public String getWorldName() {
        return worldName;
    }

    public void setWorldName(
            String worldName
    ) {
        this.worldName = worldName;
    }

    public double getX() {
        return x;
    }

    public void setX(
            double x
    ) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(
            double y
    ) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(
            double z
    ) {
        this.z = z;
    }

    public String getGameMode() {
        return gameMode;
    }

    public void setGameMode(
            String gameMode
    ) {
        this.gameMode = gameMode;
    }

    public long getSessionTime() {
        return sessionTime;
    }

    public void setSessionTime(
            long sessionTime
    ) {
        this.sessionTime = sessionTime;
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
                PLAYER INFO RESPONSE
                ------------------------------
                Found: %s
                Online: %s
                Player: %s
                UUID: %s
                Health: %.2f / %.2f
                Food: %d
                Level: %d
                World: %s
                Position: %.2f, %.2f, %.2f
                Game Mode: %s
                Session Time: %d ms
                Request ID: %s
                ==============================
                """.formatted(
                found,
                online,
                playerName,
                uuid,
                health,
                maxHealth,
                foodLevel,
                experienceLevel,
                worldName,
                x,
                y,
                z,
                gameMode,
                sessionTime,
                requestId
        );
    }
}