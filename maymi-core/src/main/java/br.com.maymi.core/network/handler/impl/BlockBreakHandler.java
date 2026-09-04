package br.com.maymi.core.network.handler.impl;

import br.com.maymi.common.network.packet.BlockBreakPacket;
import br.com.maymi.core.event.MaymiEventBus;
import br.com.maymi.core.event.player.MaymiBlockBreakEvent;
import br.com.maymi.core.mod.ModDetectionService;
import br.com.maymi.core.mod.create.CreateBlockAction;

import java.time.Instant;
import java.util.UUID;

public final class BlockBreakHandler {

    private final MaymiEventBus eventBus;
    private final ModDetectionService modDetectionService;

    public BlockBreakHandler(
            MaymiEventBus eventBus,
            ModDetectionService modDetectionService
    ) {
        this.eventBus = eventBus;
        this.modDetectionService = modDetectionService;
    }

    public void handle(
            BlockBreakPacket packet
    ) {

        UUID playerUuid =
                UUID.fromString(
                        packet.getPlayerUuid()
                );

        modDetectionService.detectBlock(
                packet.getBlockType(),
                playerUuid,
                packet.getPlayerName(),
                CreateBlockAction.BREAK
        );

        eventBus.publish(
                new MaymiBlockBreakEvent(
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
                BLOCK BREAK
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