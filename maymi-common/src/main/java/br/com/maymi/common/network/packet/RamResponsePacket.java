package br.com.maymi.common.network.packet;

import br.com.maymi.common.network.AbstractPacket;
import br.com.maymi.common.network.PacketType;

public class RamResponsePacket extends AbstractPacket {

    private double usedMemory;
    private double freeMemory;
    private double maxMemory;
    private String requestId;

    public RamResponsePacket() {
        super(PacketType.RAM_RESPONSE);
    }

    public RamResponsePacket(
            double usedMemory,
            double freeMemory,
            double maxMemory
    ) {

        this(
                usedMemory,
                freeMemory,
                maxMemory,
                null
        );
    }

    public RamResponsePacket(
            double usedMemory,
            double freeMemory,
            double maxMemory,
            String requestId
    ) {

        super(PacketType.RAM_RESPONSE);

        this.usedMemory = usedMemory;
        this.freeMemory = freeMemory;
        this.maxMemory = maxMemory;
        this.requestId = requestId;
    }

    public double getUsedMemory() {
        return usedMemory;
    }

    public double getFreeMemory() {
        return freeMemory;
    }

    public double getMaxMemory() {
        return maxMemory;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setUsedMemory(double usedMemory) {
        this.usedMemory = usedMemory;
    }

    public void setFreeMemory(double freeMemory) {
        this.freeMemory = freeMemory;
    }

    public void setMaxMemory(double maxMemory) {
        this.maxMemory = maxMemory;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}