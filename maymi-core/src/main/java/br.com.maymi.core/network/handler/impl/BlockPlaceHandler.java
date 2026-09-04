package br.com.maymi.core.network.handler.impl;

import br.com.maymi.common.network.packet.BlockPlacePacket;
import br.com.maymi.core.event.MaymiEventBus;
import br.com.maymi.core.event.player.MaymiBlockPlaceEvent;
import br.com.maymi.core.mod.ModDetectionService;
import br.com.maymi.core.mod.create.CreateBlockAction;

import java.time.Instant;
import java.util.UUID;

public final class BlockPlaceHandler {

    private final MaymiEventBus eventBus;
    private final ModDetectionService modDetectionService;

    public BlockPlaceHandler(
            MaymiEventBus eventBus,
            ModDetectionService modDetectionService
    ) {
        this.eventBus = eventBus;
        this.modDetectionService = modDetectionService;
    }

    public void handle(
            BlockPlacePacket packet
    ) {

        UUID playerUuid =
                UUID.fromString(
                        packet.getPlayerUuid()
                );

        modDetectionService.detectBlock(
                packet.getBlockType(),
                playerUuid,
                packet.getPlayerName(),
                CreateBlockAction.PLACE
        );

        eventBus.publish(
                new MaymiBlockPlaceEvent(
                        playerUuid,
                        packet.getPlayerName(),
                        packet.getBlockType(),
                        packet.getWorldName(),
                        packet.getX(),
                        packet.getY(),
                        packet.getZ(),
                        Instant.now()
                )
        );

        System.out.println("""
                ==============================
                BLOCK PLACE
                ------------------------------
                Jogador: %s
                Bloco: %s
                Mundo: %s
                Posição: %d, %d, %d
                ==============================
                """.formatted(
                packet.getPlayerName(),
                packet.getBlockType(),
                packet.getWorldName(),
                packet.getX(),
                packet.getY(),
                packet.getZ()
        ));
    }
}