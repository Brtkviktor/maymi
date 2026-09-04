package br.com.maymi.paper.listeners;

import br.com.maymi.common.network.packet.DeathPacket;
import br.com.maymi.paper.socket.SocketClient;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.projectiles.ProjectileSource;

public final class PlayerDeathListener implements Listener {

    private final SocketClient socketClient =
            new SocketClient();

    @EventHandler
    public void onPlayerDeath(
            PlayerDeathEvent event
    ) {

        Player player =
                event.getPlayer();

        Component deathComponent =
                event.deathMessage();

        String deathMessage =
                deathComponent == null
                        ? player.getName()
                          + " morreu por uma causa desconhecida."
                        : PlainTextComponentSerializer
                        .plainText()
                        .serialize(deathComponent);

        String deathCause =
                player.getLastDamageCause() == null
                        ? "UNKNOWN"
                        : player
                        .getLastDamageCause()
                        .getCause()
                        .name();

        String killerType =
                resolveKillerType(
                        player
                );

        DeathPacket packet =
                new DeathPacket(
                        player.getUniqueId().toString(),
                        player.getName(),
                        deathMessage,
                        deathCause,
                        killerType
                );

        System.out.println(
                "[PAPER-DEATH] Enviando pacote para "
                        + player.getName()
                        + " | causa="
                        + deathCause
                        + " | killer="
                        + killerType
        );

        socketClient.send(
                packet
        );
    }

    private String resolveKillerType(
            Player player
    ) {

        if (
                !(player.getLastDamageCause()
                        instanceof EntityDamageByEntityEvent damageEvent)
        ) {
            return null;
        }

        Entity damager =
                damageEvent.getDamager();

        /*
         * Dano direto:
         * Zombie, Enderman, Spider, Warden,
         * outro jogador etc.
         */
        if (!(damager instanceof Projectile projectile)) {
            return damager.getType().name();
        }

        /*
         * Dano por projétil:
         * flecha de Skeleton, Pillager ou jogador;
         * tridente; bola de fogo etc.
         */
        ProjectileSource shooter =
                projectile.getShooter();

        if (shooter instanceof Entity shooterEntity) {
            return shooterEntity
                    .getType()
                    .name();
        }

        /*
         * Caso o projétil não tenha uma entidade
         * identificável como atirador.
         */
        return projectile
                .getType()
                .name();
    }
}