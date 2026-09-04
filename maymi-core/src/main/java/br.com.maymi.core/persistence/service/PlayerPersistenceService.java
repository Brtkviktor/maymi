package br.com.maymi.core.persistence.service;

import br.com.maymi.core.achievement.AchievementUnlockService;
import br.com.maymi.core.history.PlayerHistoryService;
import br.com.maymi.core.persistence.entity.PlayerEntity;
import br.com.maymi.core.persistence.repository.PlayerRepository;
import br.com.maymi.core.persistence.repository.PlayerStatisticsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public final class PlayerPersistenceService {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(
                    PlayerPersistenceService.class
            );

    private final PlayerRepository playerRepository;
    private final PlayerStatisticsRepository statisticsRepository;
    private final PlayerStatisticsService statisticsService;
    private final AchievementUnlockService achievementUnlockService;
    private final PlayerHistoryService historyService;

    public PlayerPersistenceService(
            PlayerRepository playerRepository,
            PlayerStatisticsRepository statisticsRepository,
            PlayerStatisticsService statisticsService,
            AchievementUnlockService achievementUnlockService,
            PlayerHistoryService historyService
    ) {

        this.playerRepository =
                Objects.requireNonNull(
                        playerRepository,
                        "PlayerRepository não pode ser nulo."
                );

        this.statisticsRepository =
                Objects.requireNonNull(
                        statisticsRepository,
                        "PlayerStatisticsRepository não pode ser nulo."
                );

        this.statisticsService =
                Objects.requireNonNull(
                        statisticsService,
                        "PlayerStatisticsService não pode ser nulo."
                );

        this.achievementUnlockService =
                Objects.requireNonNull(
                        achievementUnlockService,
                        "AchievementUnlockService não pode ser nulo."
                );

        this.historyService =
                Objects.requireNonNull(
                        historyService,
                        "PlayerHistoryService não pode ser nulo."
                );
    }

    public void registerJoin(
            UUID uuid,
            String nickname
    ) {

        validatePlayer(
                uuid,
                nickname
        );

        Instant now =
                Instant.now();

        var existingPlayer =
                playerRepository.findByUuid(
                        uuid
                );

        // =====================================================
        // NOVO JOGADOR
        // =====================================================

        if (existingPlayer.isEmpty()) {

            insertNewPlayer(
                    uuid,
                    nickname,
                    now
            );

            statisticsService.startSession(
                    uuid,
                    nickname
            );

            achievementUnlockService.verifyPlayer(
                    uuid,
                    nickname
            );

            historyService.recordLogin(
                    uuid,
                    nickname
            );

            LOGGER.info(
                    "Entrada registrada para novo jogador: {} ({})",
                    nickname,
                    uuid
            );

            return;
        }

        // =====================================================
        // JOGADOR EXISTENTE
        // =====================================================

        boolean playerUpdated =
                playerRepository.registerJoin(
                        uuid,
                        nickname,
                        now
                );

        if (!playerUpdated) {

            throw new IllegalStateException(
                    "O jogador foi encontrado, mas a entrada "
                            + "não pôde ser registrada: "
                            + uuid
            );
        }

        /*
         * A sessão deve ser iniciada somente uma vez.
         */
        statisticsService.startSession(
                uuid,
                nickname
        );

        int newLoginCount =
                existingPlayer
                        .get()
                        .getLoginCount()
                        + 1;

        LOGGER.info(
                "Entrada registrada: {} ({}) - total de logins: {}",
                nickname,
                uuid,
                newLoginCount
        );

        /*
         * Depois da atualização do login,
         * verificamos conquistas relacionadas à atividade.
         */
        achievementUnlockService.verifyPlayer(
                uuid,
                nickname
        );

        /*
         * Registra a entrada no histórico.
         */
        historyService.recordLogin(
                uuid,
                nickname
        );
    }

    public void registerQuit(
            UUID uuid,
            String nickname
    ) {

        validatePlayer(
                uuid,
                nickname
        );

        Instant now =
                Instant.now();

        var existingPlayer =
                playerRepository.findByUuid(
                        uuid
                );

        if (existingPlayer.isEmpty()) {

            LOGGER.warn(
                    "Saída recebida para jogador não cadastrado: {} ({})",
                    nickname,
                    uuid
            );

            /*
             * Mantemos consistência cadastrando o jogador,
             * mas não tentamos finalizar uma sessão inexistente.
             */
            insertNewPlayer(
                    uuid,
                    nickname,
                    now
            );

            return;
        }

        boolean playerUpdated =
                playerRepository.registerQuit(
                        uuid,
                        nickname,
                        now
                );

        if (!playerUpdated) {

            throw new IllegalStateException(
                    "O jogador foi encontrado, mas a saída "
                            + "não pôde ser registrada: "
                            + uuid
            );
        }

        /*
         * Finaliza a sessão e acumula o tempo jogado.
         */
        long sessionSeconds =
                statisticsService.finishSession(
                        uuid,
                        nickname
                );

        /*
         * Agora o play_time_seconds já foi atualizado,
         * então conquistas de tempo podem ser verificadas.
         */
        achievementUnlockService.verifyPlayer(
                uuid,
                nickname
        );

        /*
         * Registra a saída no histórico.
         */
        historyService.recordLogout(
                uuid,
                nickname,
                sessionSeconds
        );

        LOGGER.info(
                "Saída registrada: {} ({}) - sessão: {} segundo(s)",
                nickname,
                uuid,
                sessionSeconds
        );
    }

    private void insertNewPlayer(
            UUID uuid,
            String nickname,
            Instant timestamp
    ) {

        PlayerEntity player =
                new PlayerEntity(
                        uuid,
                        nickname,
                        timestamp,
                        timestamp,
                        1,
                        timestamp,
                        timestamp
                );

        playerRepository.insert(
                player
        );

        statisticsRepository.insertDefault(
                uuid,
                timestamp
        );

        LOGGER.info(
                "Novo jogador cadastrado: {} ({}) - total de logins: 1",
                nickname,
                uuid
        );
    }

    private void validatePlayer(
            UUID uuid,
            String nickname
    ) {

        Objects.requireNonNull(
                uuid,
                "UUID não pode ser nulo."
        );

        if (
                nickname == null
                        || nickname.isBlank()
        ) {

            throw new IllegalArgumentException(
                    "Nickname não pode ser vazio."
            );
        }
    }
}