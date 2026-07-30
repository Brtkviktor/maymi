package br.com.maymi.common.network.parser;

import br.com.maymi.common.network.Packet;
import br.com.maymi.common.network.PacketType;
import br.com.maymi.common.network.exception.PacketDeserializationException;
import br.com.maymi.common.network.registry.PacketRegistry;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class PacketDeserializer {

    private static final ObjectMapper MAPPER =
            new ObjectMapper();

    static {

        MAPPER.findAndRegisterModules();

    }

    private PacketDeserializer() {
    }

    public static Packet deserialize(
            String json
    ) {

        if (json == null || json.isBlank()) {

            throw new PacketDeserializationException(
                    "Não é possível desserializar um JSON vazio."
            );

        }

        try {

            JsonNode node =
                    MAPPER.readTree(json);

            if (node == null) {

                throw new PacketDeserializationException(
                        "JSON inválido ou vazio."
                );

            }

            JsonNode typeNode =
                    node.get("type");

            if (typeNode == null
                    || typeNode.isNull()
                    || typeNode.asText().isBlank()) {

                throw new PacketDeserializationException(
                        "Pacote não possui o campo 'type'."
                );

            }

            PacketType type;

            try {

                type =
                        PacketType.valueOf(
                                typeNode.asText()
                        );

            } catch (IllegalArgumentException exception) {

                throw new PacketDeserializationException(
                        "Tipo de pacote desconhecido: "
                                + typeNode.asText(),
                        exception
                );

            }

            Class<? extends Packet> clazz =
                    PacketRegistry.get(type);

            if (clazz == null) {

                throw new PacketDeserializationException(
                        "Nenhuma classe registrada para o pacote: "
                                + type
                );

            }

            return MAPPER.readValue(
                    json,
                    clazz
            );

        } catch (
                PacketDeserializationException exception
        ) {

            throw exception;

        } catch (Exception exception) {

            throw new PacketDeserializationException(
                    "Erro ao desserializar pacote.",
                    exception
            );

        }

    }

}