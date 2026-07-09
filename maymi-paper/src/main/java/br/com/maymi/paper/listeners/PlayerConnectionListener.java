package br.com.maymi.paper.listeners;

import br.com.maymi.common.network.model.PlayerJoinMessage;
import br.com.maymi.paper.service.MinecraftEventService;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.time.LocalDateTime;

public class PlayerConnectionListener implements Listener {

    private final MinecraftEventService eventService;

    public PlayerConnectionListener() {

        this.eventService = new MinecraftEventService();

    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        PlayerJoinMessage message = new PlayerJoinMessage(

                event.getPlayer().getUniqueId(),

                event.getPlayer().getName(),

                LocalDateTime.now()

        );

        eventService.playerJoined(message);

    }

}