package br.com.maymi.paper.network.handler;

import br.com.maymi.common.network.packet.PlayerInfoRequestPacket;
import br.com.maymi.common.network.packet.PlayerInfoResponsePacket;
import br.com.maymi.paper.player.session.PlayerSessionManager;
import br.com.maymi.paper.socket.SocketClient;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

public class PlayerInfoRequestHandler {

    private final SocketClient socketClient;
    private final PlayerSessionManager playerSessionManager;

    public PlayerInfoRequestHandler(
            SocketClient socketClient,
            PlayerSessionManager playerSessionManager
    ) {
        this.socketClient = socketClient;
        this.playerSessionManager = playerSessionManager;
    }

    public void handle(
            PlayerInfoRequestPacket packet
    ) {

        String playerName =
                packet.getPlayerName();

        String requestId =
                packet.getRequestId();

        Player onlinePlayer =
                Bukkit.getPlayerExact(playerName);

        if (onlinePlayer != null) {

            sendOnlinePlayerResponse(
                    onlinePlayer,
                    requestId
            );

            return;
        }

        OfflinePlayer offlinePlayer =
                Bukkit.getOfflinePlayer(playerName);

        if (offlinePlayer.hasPlayedBefore()) {

            sendOfflinePlayerResponse(
                    offlinePlayer,
                    requestId
            );

            return;
        }

        sendPlayerNotFoundResponse(
                playerName,
                requestId
        );
    }

    private void sendOnlinePlayerResponse(
            Player player,
            String requestId
    ) {

        double maxHealth = 20.0;

        if (
                player.getAttribute(
                        Attribute.GENERIC_MAX_HEALTH
                ) != null
        ) {
            maxHealth =
                    player.getAttribute(
                            Attribute.GENERIC_MAX_HEALTH
                    ).getValue();
        }

        long sessionDurationMillis =
                playerSessionManager
                        .getSessionDurationMillis(
                                player.getUniqueId()
                        );

        PlayerInfoResponsePacket response =
                new PlayerInfoResponsePacket(
                        true,
                        true,
                        player.getName(),
                        player.getUniqueId().toString(),
                        player.getHealth(),
                        maxHealth,
                        player.getFoodLevel(),
                        player.getLevel(),
                        player.getWorld().getName(),
                        player.getLocation().getX(),
                        player.getLocation().getY(),
                        player.getLocation().getZ(),
                        player.getGameMode().name(),
                        sessionDurationMillis,
                        requestId
                );

        socketClient.send(response);
    }

    private void sendOfflinePlayerResponse(
            OfflinePlayer player,
            String requestId
    ) {

        PlayerInfoResponsePacket response =
                new PlayerInfoResponsePacket(
                        true,
                        false,
                        player.getName(),
                        player.getUniqueId().toString(),
                        0.0,
                        0.0,
                        0,
                        0,
                        null,
                        0.0,
                        0.0,
                        0.0,
                        null,
                        0L,
                        requestId
                );

        socketClient.send(response);
    }

    private void sendPlayerNotFoundResponse(
            String playerName,
            String requestId
    ) {

        PlayerInfoResponsePacket response =
                new PlayerInfoResponsePacket(
                        false,
                        false,
                        playerName,
                        null,
                        0.0,
                        0.0,
                        0,
                        0,
                        null,
                        0.0,
                        0.0,
                        0.0,
                        null,
                        0L,
                        requestId
                );

        socketClient.send(response);
    }
}