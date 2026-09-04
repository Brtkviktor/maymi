package br.com.maymi.paper.listeners;

import br.com.maymi.paper.service.MinecraftEventService;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public final class PlayerQuitListener
        implements Listener {

    private final MinecraftEventService minecraftEventService;

    public PlayerQuitListener(
            MinecraftEventService minecraftEventService
    ) {
        this.minecraftEventService =
                minecraftEventService;
    }

    @EventHandler
    public void onPlayerQuit(
            PlayerQuitEvent event
    ) {

        Player player =
                event.getPlayer();

        minecraftEventService.playerQuit(
                player.getUniqueId(),
                player.getName()
        );
    }
}