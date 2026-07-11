package br.com.maymi.common.network.serializer;

import br.com.maymi.common.network.Packet;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public final class PacketSerializer {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {

        MAPPER.findAndRegisterModules();

        MAPPER.disable(
                SerializationFeature.WRITE_DATES_AS_TIMESTAMPS
        );

    }

    private PacketSerializer() {

    }

    public static String serialize(Packet packet) {

        try {

            return MAPPER.writeValueAsString(packet);

        } catch (Exception exception) {

            throw new RuntimeException(
                    "Erro ao serializar pacote.",
                    exception
            );

        }

    }

}