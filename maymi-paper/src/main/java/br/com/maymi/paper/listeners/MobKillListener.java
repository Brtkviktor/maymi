package br.com.maymi.paper.listeners;

import br.com.maymi.common.network.packet.MobKillPacket;
import br.com.maymi.paper.socket.SocketClient;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public final class MobKillListener implements Listener {

    private final SocketClient socketClient =
            new SocketClient();

    @EventHandler
    public void onEntityDeath(
            EntityDeathEvent event
    ) {

        LivingEntity entity =
                event.getEntity();

        Player killer =
                entity.getKiller();

        if (killer == null) {
            return;
        }

        /*
         * Evita considerar a morte de outro jogador como mob kill.
         * Mortes PvP poderão ter um evento próprio futuramente.
         */
        if (entity instanceof Player) {
            return;
        }

        String mobType =
                entity.getType().name();

        String mobName =
                entity.customName() == null
                        ? mobType
                        : PlainTextComponentSerializer
                        .plainText()
                        .serialize(entity.customName());

        MobKillPacket packet =
                new MobKillPacket(
                        killer.getUniqueId().toString(),
                        killer.getName(),
                        mobType,
                        mobName
                );

        socketClient.send(packet);
    }
}