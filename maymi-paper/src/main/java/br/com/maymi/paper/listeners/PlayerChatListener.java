package br.com.maymi.paper.listeners;

import br.com.maymi.common.network.packet.ChatPacket;
import br.com.maymi.paper.socket.SocketClient;
import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

public class PlayerChatListener implements Listener {

    private final SocketClient socketClient = new SocketClient();

    @EventHandler
    public void onPlayerChat(AsyncChatEvent event) {

        String playerName = event.getPlayer().getName();

        String message =
                PlainTextComponentSerializer.plainText()
                        .serialize(event.message());

        ChatPacket packet =
                new ChatPacket(playerName, message);

        socketClient.send(packet);

    }

}