package br.com.maymi.paper.socket;

import br.com.maymi.common.network.Packet;
import br.com.maymi.common.network.parser.PacketDeserializer;
import br.com.maymi.paper.network.dispatcher.PacketDispatcher;
import br.com.maymi.paper.player.session.PlayerSessionManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {

    private static final int PORT = 25571;

    private final PacketDispatcher dispatcher;

    public SocketServer(
            PlayerSessionManager playerSessionManager
    ) {

        SocketClient socketClient =
                new SocketClient();

        this.dispatcher =
                new PacketDispatcher(
                        socketClient,
                        playerSessionManager
                );

    }

    public void start() {

        try (
                ServerSocket serverSocket =
                        new ServerSocket(PORT)
        ) {

            System.out.println(
                    "Maymi Paper ouvindo porta "
                            + PORT
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

                    if (json == null || json.isBlank()) {

                        System.out.println(
                                "Pacote vazio recebido."
                        );

                        continue;
                    }

                    Packet packet =
                            PacketDeserializer.deserialize(
                                    json
                            );

                    System.out.println(
                            "Pacote recebido: "
                                    + packet
                    );

                    dispatcher.dispatch(
                            packet
                    );

                } catch (Exception exception) {

                    System.out.println(
                            "Erro ao processar pacote: "
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