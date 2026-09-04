package br.com.maymi.common.network.packet;

import br.com.maymi.common.network.AbstractPacket;
import br.com.maymi.common.network.PacketType;

public class TimeResponsePacket extends AbstractPacket {

    private String worldName;
    private long day;
    private long time;
    private String requestId;

    public TimeResponsePacket() {
        super(PacketType.TIME_RESPONSE);
    }

    public TimeResponsePacket(
            String worldName,
            long day,
            long time
    ) {

        this(
                worldName,
                day,
                time,
                null
        );
    }

    public TimeResponsePacket(
            String worldName,
            long day,
            long time,
            String requestId
    ) {

        super(PacketType.TIME_RESPONSE);

        this.worldName = worldName;
        this.day = day;
        this.time = time;
        this.requestId = requestId;
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

    public String getRequestId() {
        return requestId;
    }

    public void setWorldName(
            String worldName
    ) {

        this.worldName = worldName;
    }

    public void setDay(long day) {
        this.day = day;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setRequestId(
            String requestId
    ) {

        this.requestId = requestId;
    }
}