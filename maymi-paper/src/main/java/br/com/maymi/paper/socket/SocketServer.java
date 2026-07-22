package br.com.maymi.paper.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import br.com.maymi.common.network.Packet;
import br.com.maymi.common.network.parser.PacketDeserializer;
import br.com.maymi.paper.network.dispatcher.PacketDispatcher;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class SocketServer {

    private final PacketDispatcher dispatcher =
            new PacketDispatcher();

    private static final int PORT = 25571;


    public void start() {

        try {

            ServerSocket serverSocket =
                    new ServerSocket(PORT);

            System.out.println(
                    "Maymi Paper ouvindo porta " + PORT + "..."
            );

            while (true) {

                Socket socket = serverSocket.accept();

                BufferedReader reader =
                        new BufferedReader(
                                new InputStreamReader(
                                        socket.getInputStream()
                                )
                        );

                String json = reader.readLine();

                Packet packet =
                        PacketDeserializer.deserialize(json);

                dispatcher.dispatch(packet);

                System.out.println(packet);

                socket.close();
            }
        } catch (IOException exception) {

            exception.printStackTrace();

        }


    }
}