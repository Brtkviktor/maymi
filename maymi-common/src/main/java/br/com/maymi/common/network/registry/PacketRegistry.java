package br.com.maymi.common.network.registry;
import br.com.maymi.common.network.Packet;
import br.com.maymi.common.network.PacketType;
import br.com.maymi.common.network.packet.ChatPacket;
import br.com.maymi.common.network.packet.CommandPacket;
import br.com.maymi.common.network.packet.DeathPacket;
import br.com.maymi.common.network.packet.DiscordChatPacket;
import br.com.maymi.common.network.packet.ListResponsePacket;
import br.com.maymi.common.network.packet.PlayerJoinPacket;
import br.com.maymi.common.network.packet.PlayerQuitPacket;
import br.com.maymi.common.network.packet.RamResponsePacket;
import br.com.maymi.common.network.packet.TimeResponsePacket;
import br.com.maymi.common.network.packet.TpsResponsePacket;

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

        REGISTRY.put(
                PacketType.PLAYER_CHAT,
                ChatPacket.class
        );

        REGISTRY.put(
                PacketType.PLAYER_QUIT,
                PlayerQuitPacket.class
        );

        REGISTRY.put(
                PacketType.PLAYER_DEATH,
                DeathPacket.class
        );

        REGISTRY.put(
                PacketType.DISCORD_CHAT,
                DiscordChatPacket.class
        );

        REGISTRY.put(
                PacketType.COMMAND,
                CommandPacket.class
        );

        REGISTRY.put(
                PacketType.LIST_RESPONSE,
                ListResponsePacket.class
        );

        REGISTRY.put(
                PacketType.TPS_RESPONSE,
                TpsResponsePacket.class
        );

        REGISTRY.put(
                PacketType.RAM_RESPONSE,
                RamResponsePacket.class
        );

        REGISTRY.put(
                PacketType.TIME_RESPONSE,
                TimeResponsePacket.class
        );
    }

    public static Class<? extends Packet> get(PacketType type) {

        return REGISTRY.get(type);

    }



}