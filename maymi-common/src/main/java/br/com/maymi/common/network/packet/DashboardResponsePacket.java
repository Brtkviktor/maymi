package br.com.maymi.common.network.packet;

import br.com.maymi.common.network.AbstractPacket;
import br.com.maymi.common.network.PacketType;

public class DashboardResponsePacket extends AbstractPacket {

    private double tps;
    private double mspt;

    private double usedMemory;
    private double freeMemory;
    private double maxMemory;

    private int onlinePlayers;
    private int maxPlayers;

    private String worldName;
    private long day;
    private long time;

    private long uptime;

    private String requestId;

    public DashboardResponsePacket() {
        super(PacketType.DASHBOARD_RESPONSE);
    }

    public DashboardResponsePacket(
            double tps,
            double mspt,
            double usedMemory,
            double freeMemory,
            double maxMemory,
            int onlinePlayers,
            int maxPlayers,
            String worldName,
            long day,
            long time,
            long uptime,
            String requestId
    ) {

        super(PacketType.DASHBOARD_RESPONSE);

        this.tps = tps;
        this.mspt = mspt;
        this.usedMemory = usedMemory;
        this.freeMemory = freeMemory;
        this.maxMemory = maxMemory;
        this.onlinePlayers = onlinePlayers;
        this.maxPlayers = maxPlayers;
        this.worldName = worldName;
        this.day = day;
        this.time = time;
        this.uptime = uptime;
        this.requestId = requestId;
    }

    public double getTps() {
        return tps;
    }

    public void setTps(double tps) {
        this.tps = tps;
    }

    public double getMspt() {
        return mspt;
    }

    public void setMspt(double mspt) {
        this.mspt = mspt;
    }

    public double getUsedMemory() {
        return usedMemory;
    }

    public void setUsedMemory(double usedMemory) {
        this.usedMemory = usedMemory;
    }

    public double getFreeMemory() {
        return freeMemory;
    }

    public void setFreeMemory(double freeMemory) {
        this.freeMemory = freeMemory;
    }

    public double getMaxMemory() {
        return maxMemory;
    }

    public void setMaxMemory(double maxMemory) {
        this.maxMemory = maxMemory;
    }

    public int getOnlinePlayers() {
        return onlinePlayers;
    }

    public void setOnlinePlayers(int onlinePlayers) {
        this.onlinePlayers = onlinePlayers;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public String getWorldName() {
        return worldName;
    }

    public void setWorldName(String worldName) {
        this.worldName = worldName;
    }

    public long getDay() {
        return day;
    }

    public void setDay(long day) {
        this.day = day;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getUptime() {
        return uptime;
    }

    public void setUptime(long uptime) {
        this.uptime = uptime;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    @Override
    public String toString() {
        return """
                ==============================
                DASHBOARD RESPONSE
                ------------------------------
                TPS: %.2f
                MSPT: %.2f
                RAM: %.2f / %.2f GB
                Players: %d/%d
                World: %s
                Day: %d
                Time: %d
                Uptime: %d ms
                Request ID: %s
                ==============================
                """.formatted(
                tps,
                mspt,
                usedMemory,
                maxMemory,
                onlinePlayers,
                maxPlayers,
                worldName,
                day,
                time,
                uptime,
                requestId
        );
    }
}