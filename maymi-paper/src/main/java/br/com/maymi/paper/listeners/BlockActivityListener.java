package br.com.maymi.paper.listeners;

import br.com.maymi.common.network.packet.BlockBreakPacket;
import br.com.maymi.common.network.packet.BlockPlacePacket;
import br.com.maymi.paper.socket.SocketClient;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public final class BlockActivityListener implements Listener {

    private final SocketClient socketClient =
            new SocketClient();

    @EventHandler
    public void onBlockPlace(
            BlockPlaceEvent event
    ) {

        Player player =
                event.getPlayer();

        Block block =
                event.getBlockPlaced();

        BlockPlacePacket packet =
                new BlockPlacePacket(
                        player.getUniqueId().toString(),
                        player.getName(),
                        block.getType().name(),
                        block.getWorld().getName(),
                        block.getX(),
                        block.getY(),
                        block.getZ()
                );

        socketClient.send(packet);
    }

    @EventHandler
    public void onBlockBreak(
            BlockBreakEvent event
    ) {

        Player player =
                event.getPlayer();

        Block block =
                event.getBlock();

        BlockBreakPacket packet =
                new BlockBreakPacket(
                        player.getUniqueId().toString(),
                        player.getName(),
                        block.getType().name(),
                        block.getWorld().getName(),
                        block.getX(),
                        block.getY(),
                        block.getZ()
                );

        socketClient.send(packet);
    }
}