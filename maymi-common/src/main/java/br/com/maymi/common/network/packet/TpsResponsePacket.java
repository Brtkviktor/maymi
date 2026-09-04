package br.com.maymi.common.network.packet;

import br.com.maymi.common.network.AbstractPacket;
import br.com.maymi.common.network.PacketType;

public class TpsResponsePacket extends AbstractPacket {

    private double tps;
    private String requestId;

    public TpsResponsePacket() {
        super(PacketType.TPS_RESPONSE);
    }

    public TpsResponsePacket(double tps) {
        this(tps, null);
    }

    public TpsResponsePacket(
            double tps,
            String requestId
    ) {

        super(PacketType.TPS_RESPONSE);

        this.tps = tps;
        this.requestId = requestId;
    }

    public double getTps() {
        return tps;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setTps(double tps) {
        this.tps = tps;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    @Override
    public String toString() {
        return """
                ==============================
                TPS RESPONSE
                ------------------------------
                TPS: %.2f
                Request ID: %s
                ==============================
                """.formatted(
                tps,
                requestId
        );
    }
}