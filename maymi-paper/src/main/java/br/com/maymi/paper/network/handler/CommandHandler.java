package br.com.maymi.paper.network.handler;

import br.com.maymi.common.network.packet.CommandPacket;
import br.com.maymi.common.network.packet.ListResponsePacket;
import br.com.maymi.common.network.packet.RamResponsePacket;
import br.com.maymi.common.network.packet.TimeResponsePacket;
import br.com.maymi.common.network.packet.TpsResponsePacket;
import br.com.maymi.paper.socket.SocketClient;
import org.bukkit.Bukkit;

import java.util.List;

public class CommandHandler {

    private final SocketClient socketClient = new SocketClient();

    public void handle(CommandPacket packet) {

        switch (packet.getCommand().toLowerCase()) {

            case "/list" -> {

                System.out.println("""
                        ==============================
                        EXECUTANDO /list
                        ==============================
                        """);

                List<String> players = Bukkit.getOnlinePlayers()
                        .stream()
                        .map(player -> player.getName())
                        .toList();

                ListResponsePacket responsePacket =
                        new ListResponsePacket(players);



                socketClient.send(responsePacket);

            }

            case "/tps" -> {

                double tps = Bukkit.getTPS()[0];

                socketClient.send(
                        new TpsResponsePacket(tps)
                );

            }

            case "/ram" -> {

                Runtime runtime = Runtime.getRuntime();

                double used =
                        (runtime.totalMemory() - runtime.freeMemory())
                                / 1024.0 / 1024.0 / 1024.0;

                double free =
                        runtime.freeMemory()
                                / 1024.0 / 1024.0 / 1024.0;

                double max =
                        runtime.maxMemory()
                                / 1024.0 / 1024.0 / 1024.0;

                socketClient.send(
                        new RamResponsePacket(
                                used,
                                free,
                                max
                        )
                );

            }

            case "/time" -> {

                var world = Bukkit.getWorlds().get(0);

                long day = world.getFullTime() / 24000;

                long time = world.getTime();

                socketClient.send(
                        new TimeResponsePacket(
                                world.getName(),
                                day,
                                time
                        )
                );

            }

            default -> System.out.println(
                    "Comando desconhecido: " + packet.getCommand()
            );
        }
    }
}