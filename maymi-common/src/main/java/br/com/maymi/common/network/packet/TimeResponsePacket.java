package br.com.maymi.common.network.packet;

import br.com.maymi.common.network.AbstractPacket;
import br.com.maymi.common.network.PacketType;

public class TimeResponsePacket extends AbstractPacket {

    private String worldName;
    private long day;
    private long time;

    public TimeResponsePacket() {
        super(PacketType.TIME_RESPONSE);
    }

    public TimeResponsePacket(
            String worldName,
            long day,
            long time
    ) {

        super(PacketType.TIME_RESPONSE);

        this.worldName = worldName;
        this.day = day;
        this.time = time;
    }

    public String getWorldName() {
        return worldName;
    }

    public long getDay() {
        return day;
    }

    public long getTime() {
        return time;
    }

    public void setWorldName(String worldName) {
        this.worldName = worldName;
    }

    public void setDay(long day) {
        this.day = day;
    }

    public void setTime(long time) {
        this.time = time;
    }

}