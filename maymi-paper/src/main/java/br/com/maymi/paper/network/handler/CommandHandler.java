package br.com.maymi.paper.network.handler;

import br.com.maymi.common.network.packet.CommandPacket;
import br.com.maymi.common.network.packet.DashboardResponsePacket;
import br.com.maymi.common.network.packet.ListResponsePacket;
import br.com.maymi.common.network.packet.RamResponsePacket;
import br.com.maymi.common.network.packet.TimeResponsePacket;
import br.com.maymi.common.network.packet.TpsResponsePacket;
import br.com.maymi.paper.socket.SocketClient;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.lang.management.ManagementFactory;
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

        String requestId =
                packet.getRequestId();

        switch (command) {

            // =====================================================
            // /LIST
            // =====================================================

            case "/list",
                 "/players" -> {

                System.out.println("""
                        ==============================
                        EXECUTANDO /list
                        Request ID: %s
                        ==============================
                        """.formatted(
                        requestId
                ));

                List<String> players =
                        Bukkit.getOnlinePlayers()
                                .stream()
                                .map(Player::getName)
                                .toList();

                socketClient.send(
                        new ListResponsePacket(
                                players,
                                requestId
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
                        Request ID: %s
                        ==============================
                        """.formatted(
                        requestId
                ));

                double tps =
                        Bukkit.getTPS()[0];

                socketClient.send(
                        new TpsResponsePacket(
                                tps,
                                requestId
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
                        Request ID: %s
                        ==============================
                        """.formatted(
                        requestId
                ));

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
                                max,
                                requestId
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
                        Request ID: %s
                        ==============================
                        """.formatted(
                        requestId
                ));

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
                                time,
                                requestId
                        )
                );

            }

            // =====================================================
// /DASHBOARD
// =====================================================

            case "/dashboard" -> {

                System.out.println("""
            ==============================
            EXECUTANDO /dashboard
            Request ID: %s
            ==============================
            """.formatted(
                        requestId
                ));

                if (Bukkit.getWorlds().isEmpty()) {

                    System.out.println(
                            "Não foi possível gerar o dashboard: "
                                    + "nenhum mundo está disponível."
                    );

                    return;
                }

                // =================================================
                // DESEMPENHO
                // =================================================

                double tps =
                        Bukkit.getTPS()[0];

                double mspt =
                        Bukkit.getServer()
                                .getAverageTickTime();

                // =================================================
                // MEMÓRIA
                // =================================================

                Runtime runtime =
                        Runtime.getRuntime();

                double usedMemory =
                        (
                                runtime.totalMemory()
                                        - runtime.freeMemory()
                        )
                                / 1024.0
                                / 1024.0
                                / 1024.0;

                double freeMemory =
                        runtime.freeMemory()
                                / 1024.0
                                / 1024.0
                                / 1024.0;

                double maxMemory =
                        runtime.maxMemory()
                                / 1024.0
                                / 1024.0
                                / 1024.0;

                // =================================================
                // JOGADORES
                // =================================================

                int onlinePlayers =
                        Bukkit.getOnlinePlayers()
                                .size();

                int maxPlayers =
                        Bukkit.getMaxPlayers();

                // =================================================
                // MUNDO
                // =================================================

                World world =
                        Bukkit.getWorlds().get(0);

                String worldName =
                        world.getName();

                long day =
                        world.getFullTime()
                                / 24000;

                long time =
                        world.getTime();

                // =================================================
                // UPTIME
                // =================================================

                long uptime =
                        ManagementFactory
                                .getRuntimeMXBean()
                                .getUptime();

                // =================================================
                // RESPOSTA
                // =================================================

                DashboardResponsePacket response =
                        new DashboardResponsePacket(
                                tps,
                                mspt,
                                usedMemory,
                                freeMemory,
                                maxMemory,
                                onlinePlayers,
                                maxPlayers,
                                worldName,
                                day,
                                time,
                                uptime,
                                requestId
                        );

                socketClient.send(response);

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