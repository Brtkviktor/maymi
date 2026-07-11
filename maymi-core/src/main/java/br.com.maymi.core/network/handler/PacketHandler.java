package br.com.maymi.core.network.handler;

import br.com.maymi.common.network.Packet;

public interface PacketHandler<T extends Packet> {

    void handle(T packet);

}