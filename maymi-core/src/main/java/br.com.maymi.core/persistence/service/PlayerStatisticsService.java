package br.com.maymi.core.persistence.service;

import br.com.maymi.core.persistence.repository.PlayerStatisticsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public final class PlayerStatisticsService {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(
                    PlayerStatisticsService.class
            );

    private final PlayerStatisticsRepository statisticsRepository;

    public PlayerStatisticsService(
            PlayerStatisticsRepository statisticsRepository
    ) {
        this.statisticsRepository =
                Objects.requireNonNull(
                        statisticsRepository,
                        "PlayerStatisticsRepository não pode ser nulo."
                );
    }

    public void registerDeath(
            UUID playerUuid,
            String playerName
    ) {

        validatePlayer(
                playerUuid,
                playerName
        );

        boolean updated =
                statisticsRepository.incrementDeaths(
                        playerUuid,
                        Instant.now()
                );

        if (!updated) {
            throw new IllegalStateException(
                    "Não foi possível registrar a morte do jogador "
                            + playerUuid
            );
        }

        LOGGER.info(
                "Morte registrada: {} ({})",
                playerName,
                playerUuid
        );
    }

    public void registerMobKill(
            UUID playerUuid,
            String playerName
    ) {

        validatePlayer(
                playerUuid,
                playerName
        );

        boolean updated =
                statisticsRepository.incrementMobKills(
                        playerUuid,
                        Instant.now()
                );

        if (!updated) {
            throw new IllegalStateException(
                    "Não foi possível registrar a kill do jogador "
                            + playerUuid
            );
        }

        LOGGER.info(
                "Mob abatido registrado: {} ({})",
                playerName,
                playerUuid
        );
    }

    public void registerBlockBreak(
            UUID playerUuid,
            String playerName
    ) {

        validatePlayer(
                playerUuid,
                playerName
        );

        boolean updated =
                statisticsRepository.incrementBlocksBroken(
                        playerUuid,
                        Instant.now()
                );

        if (!updated) {
            throw new IllegalStateException(
                    "Não foi possível registrar o bloco quebrado do jogador "
                            + playerUuid
            );
        }

        LOGGER.info(
                "Bloco quebrado registrado: {} ({})",
                playerName,
                playerUuid
        );
    }

    public void registerBlockPlace(
            UUID playerUuid,
            String playerName
    ) {

        validatePlayer(
                playerUuid,
                playerName
        );

        boolean updated =
                statisticsRepository.incrementBlocksPlaced(
                        playerUuid,
                        Instant.now()
                );

        if (!updated) {
            throw new IllegalStateException(
                    "Não foi possível registrar o bloco colocado do jogador "
                            + playerUuid
            );
        }

        LOGGER.info(
                "Bloco colocado registrado: {} ({})",
                playerName,
                playerUuid
        );
    }

    public void startSession(
            UUID playerUuid,
            String playerName
    ) {

        validatePlayer(
                playerUuid,
                playerName
        );

        Instant startedAt =
                Instant.now();

        boolean started =
                statisticsRepository.startSession(
                        playerUuid,
                        startedAt
                );

        if (!started) {
            throw new IllegalStateException(
                    "Não foi possível iniciar a sessão do jogador "
                            + playerUuid
            );
        }

        LOGGER.info(
                "Sessão iniciada: {} ({}) em {}",
                playerName,
                playerUuid,
                startedAt
        );
    }

    public long finishSession(
            UUID playerUuid,
            String playerName
    ) {

        validatePlayer(
                playerUuid,
                playerName
        );

        Instant endedAt =
                Instant.now();

        long sessionSeconds =
                statisticsRepository.finishSession(
                        playerUuid,
                        endedAt
                );

        LOGGER.info(
                "Sessão finalizada: {} ({}) - duração: {} segundo(s)",
                playerName,
                playerUuid,
                sessionSeconds
        );

        return sessionSeconds;
    }

    private void validatePlayer(
            UUID playerUuid,
            String playerName
    ) {

        Objects.requireNonNull(
                playerUuid,
                "UUID do jogador não pode ser nulo."
        );

        if (
                playerName == null
                        || playerName.isBlank()
        ) {
            throw new IllegalArgumentException(
                    "Nome do jogador não pode ser vazio."
            );
        }
    }
}