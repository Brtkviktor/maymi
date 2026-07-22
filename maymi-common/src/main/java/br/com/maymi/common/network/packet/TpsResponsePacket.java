package br.com.maymi.common.network.packet;

import br.com.maymi.common.network.AbstractPacket;
import br.com.maymi.common.network.PacketType;

public class TpsResponsePacket extends AbstractPacket {

    private double tps;

    public TpsResponsePacket() {
        super(PacketType.TPS_RESPONSE);
    }

    public TpsResponsePacket(double tps) {
        super(PacketType.TPS_RESPONSE);
        this.tps = tps;
    }

    public double getTps() {
        return tps;
    }

    public void setTps(double tps) {
        this.tps = tps;
    }

    @Override
    public String toString() {
        return """
                ==============================
                TPS RESPONSE
                ------------------------------
                TPS: %.2f
                ==============================
                """.formatted(tps);
    }

}