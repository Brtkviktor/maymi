package br.com.maymi.core.network.handler.impl;

import br.com.maymi.common.network.packet.DeathPacket;
import br.com.maymi.core.discord.DiscordService;
import br.com.maymi.core.event.MaymiEventBus;
import br.com.maymi.core.event.player.MaymiPlayerDeathEvent;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public final class PlayerDeathHandler {

    private final DiscordService discordService;
    private final MaymiEventBus eventBus;

    public PlayerDeathHandler(
            DiscordService discordService,
            MaymiEventBus eventBus
    ) {

        this.discordService =
                Objects.requireNonNull(
                        discordService,
                        "DiscordService não pode ser nulo."
                );

        this.eventBus =
                Objects.requireNonNull(
                        eventBus,
                        "MaymiEventBus não pode ser nulo."
                );
    }

    public void handle(
            DeathPacket packet
    ) {

        Objects.requireNonNull(
                packet,
                "DeathPacket não pode ser nulo."
        );

        System.out.println(
                "[CORE-DEATH] Pacote recebido para "
                        + packet.getPlayerName()
                        + " | causa="
                        + packet.getDeathCause()
                        + " | killer="
                        + packet.getKillerType()
        );

        UUID playerUuid =
                UUID.fromString(
                        packet.getPlayerUuid()
                );

        MaymiPlayerDeathEvent event =
                new MaymiPlayerDeathEvent(
                        playerUuid,
                        packet.getPlayerName(),
                        packet.getDeathMessage(),
                        packet.getDeathCause(),
                        packet.getKillerType(),
                        Instant.now()
                );

        eventBus.publish(
                event
        );

        System.out.println("""
                ==============================
                PLAYER DEATH
                ------------------------------
                UUID: %s
                Jogador: %s
                Mensagem: %s
                Causa: %s
                Assassino: %s
                ==============================
                """.formatted(
                playerUuid,
                packet.getPlayerName(),
                packet.getDeathMessage(),
                packet.getDeathCause(),
                packet.getKillerType() == null
                        ? "Nenhum"
                        : packet.getKillerType()
        ));

        discordService.sendDeath(
                packet
        );
    }
}