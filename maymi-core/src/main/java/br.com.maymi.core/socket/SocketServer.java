package br.com.maymi.core.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {

    public void start() {

        try {

            ServerSocket serverSocket = new ServerSocket(25570);

            System.out.println("Maymi Core ouvindo porta 25570...");

            while (true) {

                Socket socket = serverSocket.accept();

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(socket.getInputStream())
                );

                System.out.println(reader.readLine());

                socket.close();

            }

        } catch (IOException exception) {

            exception.printStackTrace();

        }

    }

}