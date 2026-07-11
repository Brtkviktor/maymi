package br.com.maymi.common.network.parser;

import br.com.maymi.common.network.Packet;
import br.com.maymi.common.network.PacketType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import br.com.maymi.common.network.registry.PacketRegistry;

public final class PacketDeserializer {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        MAPPER.findAndRegisterModules();
    }

    private PacketDeserializer() {
    }

    public static Packet deserialize(String json) {

        try {

            JsonNode node = MAPPER.readTree(json);

            PacketType type =
                    PacketType.valueOf(node.get("type").asText());

            Class<? extends Packet> clazz =
                    PacketRegistry.get(type);

            return MAPPER.readValue(json, clazz);

        } catch (Exception exception) {

            throw new RuntimeException(
                    "Erro ao desserializar pacote.",
                    exception
            );

        }

    }
}