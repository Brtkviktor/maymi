package br.com.maymi.common.network.parser;

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

import br.com.maymi.common.network.registry.PacketRegistry;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PacketDeserializerTest {


    @Test
    void deveDesserializarPlayerJoinPacket() {

        String json = """
                {
                    "type": "PLAYER_JOIN",
                    "playerName": "BRtkViktor"
                }
                """;

        var packet =
                PacketDeserializer.deserialize(json);

        assertInstanceOf(
                PlayerJoinPacket.class,
                packet
        );

        PlayerJoinPacket playerJoinPacket =
                (PlayerJoinPacket) packet;

        assertEquals(
                "BRtkViktor",
                playerJoinPacket.getPlayerName()
        );

    }


    @Test
    void deveDesserializarPlayerQuitPacket() {

        String json = """
                {
                    "type": "PLAYER_QUIT",
                    "playerName": "BRtkViktor"
                }
                """;

        var packet =
                PacketDeserializer.deserialize(json);

        assertInstanceOf(
                PlayerQuitPacket.class,
                packet
        );

        PlayerQuitPacket playerQuitPacket =
                (PlayerQuitPacket) packet;

        assertEquals(
                "BRtkViktor",
                playerQuitPacket.getPlayerName()
        );

    }


    @Test
    void deveDesserializarChatPacket() {

        String json = """
                {
                    "type": "PLAYER_CHAT",
                    "playerName": "BRtkViktor",
                    "message": "Olá Maymi!"
                }
                """;

        var packet =
                PacketDeserializer.deserialize(json);

        assertInstanceOf(
                ChatPacket.class,
                packet
        );

        ChatPacket chatPacket =
                (ChatPacket) packet;

        assertEquals(
                "BRtkViktor",
                chatPacket.getPlayerName()
        );

        assertEquals(
                "Olá Maymi!",
                chatPacket.getMessage()
        );

    }


    @Test
    void deveDesserializarDeathPacket() {

        String json = """
                {
                    "type": "PLAYER_DEATH",
                    "playerName": "BRtkViktor",
                    "deathMessage": "morreu de uma forma misteriosa"
                }
                """;

        var packet =
                PacketDeserializer.deserialize(json);

        assertInstanceOf(
                DeathPacket.class,
                packet
        );

        DeathPacket deathPacket =
                (DeathPacket) packet;

        assertEquals(
                "BRtkViktor",
                deathPacket.getPlayerName()
        );

        assertEquals(
                "morreu de uma forma misteriosa",
                deathPacket.getDeathMessage()
        );

    }


    @Test
    void deveDesserializarDiscordChatPacket() {

        String json = """
                {
                    "type": "DISCORD_CHAT",
                    "author": "João",
                    "message": "Mensagem enviada pelo Discord"
                }
                """;

        var packet =
                PacketDeserializer.deserialize(json);

        assertInstanceOf(
                DiscordChatPacket.class,
                packet
        );

        DiscordChatPacket discordChatPacket =
                (DiscordChatPacket) packet;

        assertEquals(
                "João",
                discordChatPacket.getAuthor()
        );

        assertEquals(
                "Mensagem enviada pelo Discord",
                discordChatPacket.getMessage()
        );

    }


    @Test
    void deveDesserializarCommandPacket() {

        String json = """
                {
                    "type": "COMMAND",
                    "command": "list"
                }
                """;

        var packet =
                PacketDeserializer.deserialize(json);

        assertInstanceOf(
                CommandPacket.class,
                packet
        );

        CommandPacket commandPacket =
                (CommandPacket) packet;

        assertEquals(
                "list",
                commandPacket.getCommand()
        );

    }


    @Test
    void deveDesserializarListResponsePacket() {

        String json = """
                {
                    "type": "LIST_RESPONSE",
                    "players": [
                        "BRtkViktor",
                        "Steve",
                        "Alex"
                    ]
                }
                """;

        var packet =
                PacketDeserializer.deserialize(json);

        assertInstanceOf(
                ListResponsePacket.class,
                packet
        );

        ListResponsePacket listPacket =
                (ListResponsePacket) packet;

        assertEquals(
                List.of(
                        "BRtkViktor",
                        "Steve",
                        "Alex"
                ),
                listPacket.getPlayers()
        );

    }


    @Test
    void deveDesserializarTpsResponsePacket() {

        String json = """
                {
                    "type": "TPS_RESPONSE",
                    "tps": 20.0
                }
                """;

        var packet =
                PacketDeserializer.deserialize(json);

        assertInstanceOf(
                TpsResponsePacket.class,
                packet
        );

        TpsResponsePacket tpsPacket =
                (TpsResponsePacket) packet;

        assertEquals(
                20.0,
                tpsPacket.getTps()
        );

    }


    @Test
    void deveDesserializarRamResponsePacket() {

        String json = """
                {
                    "type": "RAM_RESPONSE",
                    "usedMemory": 0.36,
                    "freeMemory": 1.64,
                    "maxMemory": 2.0
                }
                """;

        var packet =
                PacketDeserializer.deserialize(json);

        assertInstanceOf(
                RamResponsePacket.class,
                packet
        );

        RamResponsePacket ramPacket =
                (RamResponsePacket) packet;

        assertEquals(
                0.36,
                ramPacket.getUsedMemory()
        );

        assertEquals(
                1.64,
                ramPacket.getFreeMemory()
        );

        assertEquals(
                2.0,
                ramPacket.getMaxMemory()
        );

    }


    @Test
    void deveDesserializarTimeResponsePacket() {

        String json = """
                {
                    "type": "TIME_RESPONSE",
                    "worldName": "world",
                    "day": 34,
                    "time": 19277
                }
                """;

        var packet =
                PacketDeserializer.deserialize(json);

        assertInstanceOf(
                TimeResponsePacket.class,
                packet
        );

        TimeResponsePacket timePacket =
                (TimeResponsePacket) packet;

        assertEquals(
                "world",
                timePacket.getWorldName()
        );

        assertEquals(
                34,
                timePacket.getDay()
        );

        assertEquals(
                19277,
                timePacket.getTime()
        );

    }
    @Test
    void deveFalharComJsonInvalido() {

        String json = """
            {
                "type": "PLAYER_JOIN",
                "playerName":
            }
            """;

        assertThrows(
                RuntimeException.class,
                () -> PacketDeserializer.deserialize(json)
        );

    }

    @Test
    void deveFalharComJsonSemType() {

        String json = """
            {
                "playerName": "BRtkViktor"
            }
            """;

        assertThrows(
                RuntimeException.class,
                () -> PacketDeserializer.deserialize(json)
        );

    }

    @Test
    void deveFalharComTypeDesconhecido() {

        String json = """
            {
                "type": "PACOTE_QUE_NAO_EXISTE",
                "playerName": "BRtkViktor"
            }
            """;

        assertThrows(
                RuntimeException.class,
                () -> PacketDeserializer.deserialize(json)
        );

    }

    @Test
    void todosOsPacketTypesDevemEstarRegistrados() {

        for (PacketType type : PacketType.values()) {

            assertNotNull(
                    PacketRegistry.get(type),
                    "PacketType não registrado: " + type
            );

        }

    }

}