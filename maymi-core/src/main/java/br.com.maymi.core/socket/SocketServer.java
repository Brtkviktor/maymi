package br.com.maymi.core.socket;

import br.com.maymi.common.network.Packet;
import br.com.maymi.common.network.parser.PacketDeserializer;
import br.com.maymi.core.configuration.ConfigurationManager;
import br.com.maymi.core.network.dispatcher.PacketDispatcher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {

    private final PacketDispatcher dispatcher;

    public SocketServer(
            PacketDispatcher dispatcher
    ) {

        this.dispatcher =
                dispatcher;

    }

    public void start() {

        int port =
                ConfigurationManager.getCorePort();

        try (
                ServerSocket serverSocket =
                        new ServerSocket(port)
        ) {

            System.out.println(
                    "Maymi Core ouvindo porta "
                            + port
                            + "..."
            );

            while (true) {

                try (
                        Socket socket =
                                serverSocket.accept();

                        BufferedReader reader =
                                new BufferedReader(
                                        new InputStreamReader(
                                                socket.getInputStream()
                                        )
                                )
                ) {

                    String json =
                            reader.readLine();

                    Packet packet =
                            PacketDeserializer.deserialize(
                                    json
                            );

                    dispatcher.dispatch(
                            packet
                    );

                } catch (Exception exception) {

                    System.out.println(
                            "Erro ao processar conexão: "
                                    + exception.getMessage()
                    );

                    exception.printStackTrace();

                }

            }

        } catch (IOException exception) {

            System.out.println(
                    "Erro ao iniciar SocketServer: "
                            + exception.getMessage()
            );

            exception.printStackTrace();

        }

    }

}