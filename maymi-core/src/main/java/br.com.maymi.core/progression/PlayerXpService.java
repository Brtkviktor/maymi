package br.com.maymi.core.progression;

import br.com.maymi.core.persistence.entity.PlayerStatisticsEntity;
import br.com.maymi.core.persistence.repository.PlayerStatisticsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public final class PlayerXpService {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(
                    PlayerXpService.class
            );

    private final PlayerStatisticsRepository statisticsRepository;

    public PlayerXpService(
            PlayerStatisticsRepository statisticsRepository
    ) {

        this.statisticsRepository =
                Objects.requireNonNull(
                        statisticsRepository,
                        "PlayerStatisticsRepository não pode ser nulo."
                );
    }

    public XpUpdateResult addXp(
            UUID playerUuid,
            String playerName,
            long amount
    ) {

        Objects.requireNonNull(
                playerUuid,
                "UUID do jogador não pode ser nulo."
        );

        PlayerStatisticsEntity statistics =
                statisticsRepository
                        .findByPlayerUuid(
                                playerUuid
                        )
                        .orElseThrow(
                                () -> new IllegalStateException(
                                        "Estatísticas não encontradas para o jogador "
                                                + playerUuid
                                )
                        );

        long previousXp =
                statistics.getMaymiXp();

        int previousLevel =
                statistics.getLevel();

        long currentXp =
                Math.max(
                        0,
                        previousXp + amount
                );

        int currentLevel =
                LevelCalculator.calculateLevel(
                        currentXp
                );

        boolean updated =
                statisticsRepository.updateXpAndLevel(
                        playerUuid,
                        currentXp,
                        currentLevel,
                        Instant.now()
                );

        if (!updated) {

            throw new IllegalStateException(
                    "Nenhuma estatística foi atualizada para o jogador "
                            + playerUuid
            );
        }

        XpUpdateResult result =
                new XpUpdateResult(
                        previousXp,
                        currentXp,
                        previousLevel,
                        currentLevel
                );

        LOGGER.info(
                "XP atualizado: {} ({}) | {} XP -> {} XP | nível {} -> {}",
                playerName,
                playerUuid,
                previousXp,
                currentXp,
                previousLevel,
                currentLevel
        );

        return result;
    }
}