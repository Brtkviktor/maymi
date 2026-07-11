package br.com.maymi.paper.socket;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import br.com.maymi.common.network.Packet;
import br.com.maymi.common.network.serializer.PacketSerializer;

public class SocketClient {

    private static final String HOST = "localhost";

    private static final int PORT = 25570;

    public void send(Packet packet) {

        try (

                Socket socket = new Socket(HOST, PORT);

                PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)

        ) {
            String json = PacketSerializer.serialize(packet);

            writer.println(json);

        } catch (IOException exception) {

            System.out.println("Não foi possível conectar ao Core.");

        }

    }

}