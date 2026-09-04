package br.com.maymi.core.network.handler.impl;

import br.com.maymi.common.network.packet.PlayerJoinPacket;
import br.com.maymi.core.discord.DiscordService;
import br.com.maymi.core.persistence.service.PlayerPersistenceService;

import java.util.Objects;
import java.util.UUID;

public final class PlayerJoinHandler {

    private final DiscordService discordService;
    private final PlayerPersistenceService playerPersistenceService;

    public PlayerJoinHandler(
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

    public void handle(PlayerJoinPacket packet) {

        Objects.requireNonNull(
                packet,
                "PlayerJoinPacket não pode ser nulo."
        );

        UUID playerUuid = UUID.fromString(
                packet.getPlayerUuid()
        );

        String playerName =
                packet.getPlayerName();

        /*
         * Primeiro registra ou atualiza o jogador no banco.
         */
        playerPersistenceService.registerJoin(
                playerUuid,
                playerName
        );

        /*
         * Mantém o comportamento atual do console.
         */
        System.out.println();
        System.out.println("================================");
        System.out.println("JOGADOR ENTROU");
        System.out.println("--------------------------------");
        System.out.println("UUID: " + playerUuid);
        System.out.println("Nome: " + playerName);
        System.out.println("================================");
        System.out.println();

        /*
         * Mantém o envio da mensagem para o Discord.
         */
        discordService.sendPlayerJoin(
                packet
        );
    }
}