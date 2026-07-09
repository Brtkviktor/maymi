package br.com.maymi.paper.socket;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketClient {

    private static final String HOST = "localhost";

    private static final int PORT = 25570;

    public void send(String message) {

        try (

                Socket socket = new Socket(HOST, PORT);

                PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)

        ) {

            writer.println(message);

        } catch (IOException exception) {

            System.out.println("Não foi possível conectar ao Core.");

        }

    }

}