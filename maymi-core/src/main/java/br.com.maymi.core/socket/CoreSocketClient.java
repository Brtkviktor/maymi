package br.com.maymi.core.socket;

import br.com.maymi.common.network.Packet;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.PrintWriter;
import java.net.Socket;

public class CoreSocketClient {

    private final String host;

    private final int port;

    private final ObjectMapper objectMapper;


    public CoreSocketClient(
            String host,
            int port
    ) {

        this.host =
                host;

        this.port =
                port;

        this.objectMapper =
                new ObjectMapper();

    }


    public void send(
            Packet packet
    ) {

        try (
                Socket socket =
                        new Socket(
                                host,
                                port
                        );

                PrintWriter writer =
                        new PrintWriter(
                                socket.getOutputStream(),
                                true
                        )
        ) {

            String json =
                    objectMapper.writeValueAsString(
                            packet
                    );

            writer.println(
                    json
            );

        } catch (Exception exception) {

            System.out.println(
                    "Erro ao enviar pacote: "
                            + exception.getMessage()
            );

            exception.printStackTrace();

        }

    }

}