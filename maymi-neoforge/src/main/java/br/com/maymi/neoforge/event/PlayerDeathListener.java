package br.com.maymi.neoforge.event;

import br.com.maymi.neoforge.network.MaymiSocketClient;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

public final class PlayerDeathListener {

    private static final MaymiSocketClient SOCKET_CLIENT =
            new MaymiSocketClient();

    private PlayerDeathListener() {
    }

    @SubscribeEvent
    public static void onPlayerDeath(
            LivingDeathEvent event
    ) {

        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }

        DamageSource damageSource =
                event.getSource();

        String uuid =
                player.getUUID()
                        .toString();

        String nickname =
                player.getGameProfile()
                        .getName();

        String deathCause =
                resolveDeathCause(
                        damageSource
                );

        String killerType =
                resolveKillerType(
                        damageSource
                );

        String deathMessage =
                buildDeathMessage(
                        nickname,
                        deathCause,
                        killerType
                );

        String payload =
                """
                {
                    "type":"PLAYER_DEATH",
                    "playerUuid":"%s",
                    "playerName":"%s",
                    "deathMessage":"%s",
                    "deathCause":"%s",
                    "killerType":%s
                }
                """.formatted(
                        uuid,
                        escapeJson(nickname),
                        escapeJson(deathMessage),
                        escapeJson(deathCause),
                        killerType == null
                                ? "null"
                                : "\""
                                  + escapeJson(killerType)
                                  + "\""
                ).replace(
                        "\n",
                        ""
                );

        SOCKET_CLIENT.send(
                payload
        );

        System.out.println(
                "[Maymi NeoForge] PLAYER_DEATH enviado: "
                        + nickname
                        + " | causa="
                        + deathCause
                        + " | killer="
                        + (
                        killerType == null
                                ? "NONE"
                                : killerType
                )
        );
    }

    private static String resolveDeathCause(
            DamageSource damageSource
    ) {

        if (damageSource == null) {
            return "UNKNOWN";
        }

        try {

            return damageSource
                    .typeHolder()
                    .unwrapKey()
                    .map(
                            key ->
                                    key.location()
                                            .getPath()
                                            .toUpperCase()
                    )
                    .orElse(
                            "UNKNOWN"
                    );

        } catch (Exception exception) {

            return "UNKNOWN";
        }
    }

    private static String resolveKillerType(
            DamageSource damageSource
    ) {

        if (damageSource == null) {
            return null;
        }

        Entity entity =
                damageSource.getEntity();

        if (entity == null) {
            entity =
                    damageSource.getDirectEntity();
        }

        if (entity == null) {
            return null;
        }

        return entity.getType()
                .toString()
                .toUpperCase();
    }

    private static String buildDeathMessage(
            String nickname,
            String deathCause,
            String killerType
    ) {

        if (killerType != null) {

            return nickname
                    + " morreu por "
                    + killerType
                    + ".";
        }

        return nickname
                + " morreu. Causa: "
                + deathCause
                + ".";
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