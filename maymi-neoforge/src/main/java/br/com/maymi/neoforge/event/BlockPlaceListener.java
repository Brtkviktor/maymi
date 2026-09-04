package br.com.maymi.neoforge.event;

import br.com.maymi.neoforge.network.MaymiSocketClient;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.level.BlockEvent;

public final class BlockPlaceListener {

    private static final MaymiSocketClient SOCKET_CLIENT =
            new MaymiSocketClient();

    private BlockPlaceListener() {
    }

    @SubscribeEvent
    public static void onBlockPlace(
            BlockEvent.EntityPlaceEvent event
    ) {

        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }

        if (event.isCanceled()) {
            return;
        }

        BlockState blockState =
                event.getPlacedBlock();

        BlockPos position =
                event.getPos();

        ResourceLocation blockId =
                BuiltInRegistries.BLOCK.getKey(
                        blockState.getBlock()
                );

        String playerUuid =
                player.getUUID()
                        .toString();

        String playerName =
                player.getGameProfile()
                        .getName();

        String blockType =
                blockId != null
                        ? blockId.toString()
                        : "minecraft:unknown";

        String worldName =
                player.serverLevel()
                        .dimension()
                        .location()
                        .toString();

        int x =
                position.getX();

        int y =
                position.getY();

        int z =
                position.getZ();

        String payload =
                """
                {
                    "type":"BLOCK_PLACE",
                    "playerUuid":"%s",
                    "playerName":"%s",
                    "blockType":"%s",
                    "worldName":"%s",
                    "x":%d,
                    "y":%d,
                    "z":%d
                }
                """.formatted(
                        playerUuid,
                        escapeJson(playerName),
                        escapeJson(blockType),
                        escapeJson(worldName),
                        x,
                        y,
                        z
                ).replace(
                        "\n",
                        ""
                );

        SOCKET_CLIENT.send(
                payload
        );

        System.out.println(
                "[Maymi NeoForge] BLOCK_PLACE enviado: "
                        + playerName
                        + " colocou "
                        + blockType
                        + " em "
                        + worldName
                        + " ["
                        + x
                        + ", "
                        + y
                        + ", "
                        + z
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