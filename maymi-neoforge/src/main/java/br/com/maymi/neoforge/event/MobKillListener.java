package br.com.maymi.neoforge.event;

import br.com.maymi.neoforge.network.MaymiSocketClient;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

public final class MobKillListener {

    private static final MaymiSocketClient SOCKET_CLIENT =
            new MaymiSocketClient();

    private MobKillListener() {
    }

    @SubscribeEvent
    public static void onMobDeath(
            LivingDeathEvent event
    ) {

        /*
         * Não conta morte de jogador como mob kill.
         */
        if (event.getEntity() instanceof Player) {
            return;
        }

        Entity attacker =
                event.getSource()
                        .getEntity();

        /*
         * Só interessa quando o atacante é um jogador
         * conectado ao servidor.
         */
        if (!(attacker instanceof ServerPlayer player)) {
            return;
        }

        LivingEntity killedEntity =
                event.getEntity();

        String playerUuid =
                player.getUUID()
                        .toString();

        String playerName =
                player.getGameProfile()
                        .getName();

        String mobType =
                killedEntity
                        .getType()
                        .toString()
                        .toUpperCase();

        String mobName =
                killedEntity
                        .getName()
                        .getString();

        String payload =
                """
                {
                    "type":"MOB_KILL",
                    "playerUuid":"%s",
                    "playerName":"%s",
                    "mobType":"%s",
                    "mobName":"%s"
                }
                """.formatted(
                        playerUuid,
                        escapeJson(playerName),
                        escapeJson(mobType),
                        escapeJson(mobName)
                ).replace(
                        "\n",
                        ""
                );

        SOCKET_CLIENT.send(
                payload
        );

        System.out.println(
                "[Maymi NeoForge] MOB_KILL enviado: "
                        + playerName
                        + " matou "
                        + mobName
                        + " ["
                        + mobType
                        + "]"
        );
    }

    private static String escapeJson(
            String value
    ) {

        return value
                .replace(
                        "\\",
                        "\\\\"
                )
                .replace(
                        "\"",
                        "\\\""
                );
    }
}