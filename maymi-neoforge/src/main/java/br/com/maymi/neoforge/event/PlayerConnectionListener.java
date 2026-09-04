package br.com.maymi.neoforge.event;

import br.com.maymi.neoforge.network.MaymiSocketClient;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

public final class PlayerConnectionListener {

    private static final MaymiSocketClient SOCKET_CLIENT =
            new MaymiSocketClient();

    private PlayerConnectionListener() {
    }

    // =====================================================
    // PLAYER JOIN
    // =====================================================

    @SubscribeEvent
    public static void onPlayerLogin(
            PlayerEvent.PlayerLoggedInEvent event
    ) {

        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }

        String uuid =
                player.getUUID()
                        .toString();

        String nickname =
                player.getGameProfile()
                        .getName();

        String payload =
                """
                {
                    "type":"PLAYER_JOIN",
                    "playerUuid":"%s",
                    "playerName":"%s"
                }
                """.formatted(
                        uuid,
                        escapeJson(nickname)
                ).replace(
                        "\n",
                        ""
                );

        SOCKET_CLIENT.send(
                payload
        );

        System.out.println(
                "[Maymi NeoForge] PLAYER_JOIN enviado: "
                        + nickname
                        + " ("
                        + uuid
                        + ")"
        );
    }

    // =====================================================
    // PLAYER QUIT
    // =====================================================

    @SubscribeEvent
    public static void onPlayerLogout(
            PlayerEvent.PlayerLoggedOutEvent event
    ) {

        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }

        String uuid =
                player.getUUID()
                        .toString();

        String nickname =
                player.getGameProfile()
                        .getName();

        String payload =
                """
                {
                    "type":"PLAYER_QUIT",
                    "playerUuid":"%s",
                    "playerName":"%s"
                }
                """.formatted(
                        uuid,
                        escapeJson(nickname)
                ).replace(
                        "\n",
                        ""
                );

        SOCKET_CLIENT.send(
                payload
        );

        System.out.println(
                "[Maymi NeoForge] PLAYER_QUIT enviado: "
                        + nickname
                        + " ("
                        + uuid
                        + ")"
        );
    }

    // =====================================================
    // JSON
    // =====================================================

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