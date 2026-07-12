package br.com.maymi.core.socket;

import br.com.maymi.common.network.Packet;
import br.com.maymi.core.network.dispatcher.PacketDispatcher;
import br.com.maymi.common.network.parser.PacketDeserializer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {


    private final PacketDispatcher dispatcher = new PacketDispatcher();

    public void start() {

        try {

            ServerSocket serverSocket = new ServerSocket(25570);

            System.out.println("Maymi Core ouvindo porta 25570...");

            while (true) {

                Socket socket = serverSocket.accept();

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(socket.getInputStream())
                );

                String json = reader.readLine();

                Packet packet =
                        PacketDeserializer.deserialize(json);

                dispatcher.dispatch(packet);

                socket.close();

            }

        } catch (IOException exception) {

            exception.printStackTrace();

        }

    }

}