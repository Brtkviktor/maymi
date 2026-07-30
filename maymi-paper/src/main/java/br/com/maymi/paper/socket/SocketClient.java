package br.com.maymi.paper.socket;

import br.com.maymi.common.network.Packet;
import br.com.maymi.common.network.serializer.PacketSerializer;

import java.io.PrintWriter;
import java.net.Socket;

public class SocketClient {

    private static final String HOST =
            "localhost";

    private static final int PORT =
            25570;


    public void send(
            Packet packet
    ) {

        try (
                Socket socket =
                        new Socket(
                                HOST,
                                PORT
                        );

                PrintWriter writer =
                        new PrintWriter(
                                socket.getOutputStream(),
                                true
                        )
        ) {

            writer.println(
                    PacketSerializer.serialize(
                            packet
                    )
            );

        } catch (Exception exception) {

            System.out.println(
                    "Erro ao enviar pacote para Maymi Core: "
                            + exception.getMessage()
            );

            exception.printStackTrace();

        }

    }

}