package br.com.maymi.paper.listeners;

import br.com.maymi.common.network.packet.PlayerQuitPacket;
import br.com.maymi.paper.socket.SocketClient;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    private final SocketClient socketClient = new SocketClient();

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {

        PlayerQuitPacket packet =
                new PlayerQuitPacket(
                        event.getPlayer().getName()
                );

        socketClient.send(packet);

    }

}