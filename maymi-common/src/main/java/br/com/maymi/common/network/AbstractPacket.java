package br.com.maymi.common.network;

public abstract class AbstractPacket implements Packet {

    private PacketType type;

    protected AbstractPacket() {
    }

    protected AbstractPacket(PacketType type) {
        this.type = type;
    }

    @Override
    public PacketType getType() {
        return type;
    }

    public void setType(PacketType type) {
        this.type = type;
    }

}