package br.com.maymi.neoforge.network;

import java.io.PrintWriter;
import java.net.Socket;

public final class MaymiSocketClient {

    private static final String HOST =
            "127.0.0.1";

    private static final int PORT =
            25570;

    public void send(
            String payload
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
                    payload
            );

        } catch (Exception exception) {

            System.err.println(
                    "[Maymi NeoForge] Falha ao enviar pacote para o Core: "
                            + exception.getMessage()
            );
        }
    }
}