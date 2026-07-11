package br.com.maymi.common.network.registry;
import br.com.maymi.common.network.Packet;
import br.com.maymi.common.network.PacketType;
import br.com.maymi.common.network.packet.PlayerJoinPacket;

import java.util.HashMap;
import java.util.Map;

public final class PacketRegistry {

    private static final Map<PacketType, Class<? extends Packet>> REGISTRY =
            new HashMap<>();

    private PacketRegistry() {

    }

    static {

        REGISTRY.put(
                PacketType.PLAYER_JOIN,
                PlayerJoinPacket.class
        );

    }

    public static Class<? extends Packet> get(PacketType type) {

        return REGISTRY.get(type);

    }

}