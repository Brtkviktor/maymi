package br.com.maymi.paper.network.handler;

import br.com.maymi.common.network.packet.CommandPacket;
import br.com.maymi.common.network.packet.ListResponsePacket;
import br.com.maymi.common.network.packet.RamResponsePacket;
import br.com.maymi.common.network.packet.TimeResponsePacket;
import br.com.maymi.common.network.packet.TpsResponsePacket;
import br.com.maymi.paper.socket.SocketClient;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.List;

public class CommandHandler {

    private final SocketClient socketClient;

    public CommandHandler(
            SocketClient socketClient
    ) {

        this.socketClient =
                socketClient;

    }


    public void handle(
            CommandPacket packet
    ) {

        String command =
                packet.getCommand()
                        .toLowerCase()
                        .trim();


        switch (command) {

            // =====================================================
            // /LIST
            // =====================================================

            case "/list" -> {

                System.out.println("""
                        ==============================
                        EXECUTANDO /list
                        ==============================
                        """);

                List<String> players =
                        Bukkit.getOnlinePlayers()
                                .stream()
                                .map(Player::getName)
                                .toList();

                socketClient.send(
                        new ListResponsePacket(
                                players
                        )
                );

            }


            // =====================================================
            // /TPS
            // =====================================================

            case "/tps" -> {

                System.out.println("""
                        ==============================
                        EXECUTANDO /tps
                        ==============================
                        """);

                double tps =
                        Bukkit.getTPS()[0];

                socketClient.send(
                        new TpsResponsePacket(
                                tps
                        )
                );

            }


            // =====================================================
            // /RAM
            // =====================================================

            case "/ram" -> {

                System.out.println("""
                        ==============================
                        EXECUTANDO /ram
                        ==============================
                        """);

                Runtime runtime =
                        Runtime.getRuntime();

                double used =
                        (
                                runtime.totalMemory()
                                        - runtime.freeMemory()
                        )
                                / 1024.0
                                / 1024.0
                                / 1024.0;

                double free =
                        runtime.freeMemory()
                                / 1024.0
                                / 1024.0
                                / 1024.0;

                double max =
                        runtime.maxMemory()
                                / 1024.0
                                / 1024.0
                                / 1024.0;


                socketClient.send(
                        new RamResponsePacket(
                                used,
                                free,
                                max
                        )
                );

            }


            // =====================================================
            // /TIME
            // =====================================================

            case "/time" -> {

                System.out.println("""
                        ==============================
                        EXECUTANDO /time
                        ==============================
                        """);

                if (Bukkit.getWorlds().isEmpty()) {

                    System.out.println(
                            "Nenhum mundo disponível."
                    );

                    return;
                }


                World world =
                        Bukkit.getWorlds().get(0);


                long day =
                        world.getFullTime()
                                / 24000;


                long time =
                        world.getTime();


                socketClient.send(
                        new TimeResponsePacket(
                                world.getName(),
                                day,
                                time
                        )
                );

            }


            // =====================================================
            // COMANDO DESCONHECIDO
            // =====================================================

            default -> {

                System.out.println(
                        "Comando desconhecido: "
                                + packet.getCommand()
                );

            }

        }

    }

}