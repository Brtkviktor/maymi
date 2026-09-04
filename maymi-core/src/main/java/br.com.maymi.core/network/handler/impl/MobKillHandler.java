package br.com.maymi.core.network.handler.impl;

import br.com.maymi.common.network.packet.MobKillPacket;
import br.com.maymi.core.event.MaymiEventBus;
import br.com.maymi.core.event.player.MaymiMobKillEvent;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public final class MobKillHandler {

    private final MaymiEventBus eventBus;

    public MobKillHandler(
            MaymiEventBus eventBus
    ) {
        this.eventBus =
                Objects.requireNonNull(
                        eventBus,
                        "MaymiEventBus não pode ser nulo."
                );
    }

    public void handle(
            MobKillPacket packet
    ) {

        Objects.requireNonNull(
                packet,
                "MobKillPacket não pode ser nulo."
        );

        UUID playerUuid =
                UUID.fromString(
                        packet.getPlayerUuid()
                );

        MaymiMobKillEvent event =
                new MaymiMobKillEvent(
                        playerUuid,
                        packet.getPlayerName(),
                        packet.getMobType(),
                        packet.getMobName(),
                        Instant.now()
                );

        eventBus.publish(event);

        System.out.println("""
                ==============================
                MOB KILL
                ------------------------------
                UUID: %s
                Jogador: %s
                Mob Type: %s
                Mob Name: %s
                ==============================
                """.formatted(
                playerUuid,
                packet.getPlayerName(),
                packet.getMobType(),
                packet.getMobName()
        ));
    }
}