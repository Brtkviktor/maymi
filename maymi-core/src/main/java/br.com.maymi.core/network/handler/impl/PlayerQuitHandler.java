package br.com.maymi.core.network.handler.impl;

import br.com.maymi.common.network.packet.PlayerQuitPacket;
import br.com.maymi.core.discord.DiscordService;
import br.com.maymi.core.persistence.service.PlayerPersistenceService;

import java.util.Objects;
import java.util.UUID;

public final class PlayerQuitHandler {

    private final DiscordService discordService;
    private final PlayerPersistenceService playerPersistenceService;

    public PlayerQuitHandler(
            DiscordService discordService,
            PlayerPersistenceService playerPersistenceService
    ) {
        this.discordService = Objects.requireNonNull(
                discordService,
                "DiscordService não pode ser nulo."
        );

        this.playerPersistenceService = Objects.requireNonNull(
                playerPersistenceService,
                "PlayerPersistenceService não pode ser nulo."
        );
    }

    public void handle(
            PlayerQuitPacket packet
    ) {
        Objects.requireNonNull(
                packet,
                "PlayerQuitPacket não pode ser nulo."
        );

        UUID playerUuid = UUID.fromString(
                packet.getPlayerUuid()
        );

        String playerName =
                packet.getPlayerName();

        playerPersistenceService.registerQuit(
                playerUuid,
                playerName
        );

        System.out.println("""
                ==============================
                PLAYER QUIT
                ------------------------------
                UUID: %s
                Jogador: %s
                ==============================
                """.formatted(
                playerUuid,
                playerName
        ));

        discordService.sendPlayerQuit(
                packet
        );
    }
}