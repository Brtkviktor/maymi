package br.com.maymi.core.network.dispatcher;

import br.com.maymi.common.network.Packet;
import br.com.maymi.common.network.PacketType;
import br.com.maymi.core.network.handler.PacketHandler;
import br.com.maymi.common.network.packet.PlayerJoinPacket;
import br.com.maymi.core.network.handler.PlayerJoinHandler;

import java.util.HashMap;
import java.util.Map;

public class PacketDispatcher {

    private final Map<PacketType, PacketHandler<?>> handlers = new HashMap<>();

    public PacketDispatcher() {
        register(PacketType.PLAYER_JOIN, new PlayerJoinHandler());
    }
    public <T extends Packet> void register(PacketType type, PacketHandler<T> handler) {
        handlers.put(type, handler);
    }

    @SuppressWarnings("unchecked")
    public void dispatch(Packet packet) {

        PacketHandler<Packet> handler =
                (PacketHandler<Packet>) handlers.get(packet.getType());

        if (handler == null) {
            System.out.println("[Dispatcher] Nenhum handler encontrado para: " + packet.getType());
            return;
        }

        handler.handle(packet);
    }
}