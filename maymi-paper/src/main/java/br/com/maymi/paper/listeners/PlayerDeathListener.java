package br.com.maymi.paper.listeners;

import br.com.maymi.common.network.packet.DeathPacket;
import br.com.maymi.paper.socket.SocketClient;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {

    private final SocketClient socketClient = new SocketClient();

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {

        String playerName = event.getPlayer().getName();

        String deathMessage =
                PlainTextComponentSerializer.plainText()
                        .serialize(event.deathMessage());

        DeathPacket packet =
                new DeathPacket(
                        playerName,
                        deathMessage
                );

        socketClient.send(packet);



    }

}