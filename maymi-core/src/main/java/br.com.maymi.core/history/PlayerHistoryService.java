package br.com.maymi.core.history;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public final class PlayerHistoryService {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(
                    PlayerHistoryService.class
            );

    private final PlayerHistoryRepository historyRepository;

    public PlayerHistoryService(
            PlayerHistoryRepository historyRepository
    ) {

        this.historyRepository =
                Objects.requireNonNull(
                        historyRepository,
                        "PlayerHistoryRepository não pode ser nulo."
                );
    }

    public void recordLogin(
            UUID playerUuid,
            String playerName
    ) {

        save(
                playerUuid,
                HistoryType.LOGIN,
                "Jogador entrou",
                playerName + " entrou no servidor.",
                "{}"
        );
    }

    public void recordLogout(
            UUID playerUuid,
            String playerName,
            long sessionSeconds
    ) {

        save(
                playerUuid,
                HistoryType.LOGOUT,
                "Jogador saiu",
                playerName + " saiu do servidor.",
                """
                {"sessionSeconds":%d}
                """.formatted(
                        Math.max(
                                0,
                                sessionSeconds
                        )
                ).trim()
        );
    }

    public void recordLevelUp(
            UUID playerUuid,
            String playerName,
            int previousLevel,
            int currentLevel,
            long currentXp
    ) {

        save(
                playerUuid,
                HistoryType.LEVEL_UP,
                "Level Up",
                playerName
                        + " evoluiu do nível "
                        + previousLevel
                        + " para o nível "
                        + currentLevel
                        + ".",
                """
                {"previousLevel":%d,"currentLevel":%d,"xp":%d}
                """.formatted(
                        previousLevel,
                        currentLevel,
                        Math.max(
                                0,
                                currentXp
                        )
                ).trim()
        );
    }

    public void recordLevelDown(
            UUID playerUuid,
            String playerName,
            int previousLevel,
            int currentLevel,
            long currentXp
    ) {

        save(
                playerUuid,
                HistoryType.LEVEL_DOWN,
                "Level Down",
                playerName
                        + " caiu do nível "
                        + previousLevel
                        + " para o nível "
                        + currentLevel
                        + ".",
                """
                {"previousLevel":%d,"currentLevel":%d,"xp":%d}
                """.formatted(
                        previousLevel,
                        currentLevel,
                        Math.max(
                                0,
                                currentXp
                        )
                ).trim()
        );
    }

    public void recordAchievement(
            UUID playerUuid,
            String playerName,
            String achievementId,
            String achievementName,
            long xpReward
    ) {

        validateText(
                achievementId,
                "ID da conquista"
        );

        validateText(
                achievementName,
                "Nome da conquista"
        );

        save(
                playerUuid,
                HistoryType.ACHIEVEMENT_UNLOCKED,
                "Conquista desbloqueada",
                playerName
                        + " desbloqueou "
                        + achievementName
                        + ".",
                """
                {"achievementId":"%s","xpReward":%d}
                """.formatted(
                        escapeJson(
                                achievementId
                        ),
                        Math.max(
                                0,
                                xpReward
                        )
                ).trim()
        );
    }

    public void recordDeath(
            UUID playerUuid,
            String playerName,
            String deathCause,
            String killerType
    ) {

        String safeCause =
                normalizeNullable(
                        deathCause
                );

        String safeKiller =
                normalizeNullable(
                        killerType
                );

        save(
                playerUuid,
                HistoryType.PLAYER_DEATH,
                "Morte",
                playerName + " morreu.",
                """
                {"cause":"%s","killer":"%s"}
                """.formatted(
                        escapeJson(
                                safeCause
                        ),
                        escapeJson(
                                safeKiller
                        )
                ).trim()
        );
    }

    private void save(
            UUID playerUuid,
            HistoryType type,
            String title,
            String description,
            String metadata
    ) {

        Objects.requireNonNull(
                playerUuid,
                "UUID do jogador não pode ser nulo."
        );

        Objects.requireNonNull(
                type,
                "Tipo do histórico não pode ser nulo."
        );

        PlayerHistoryEntity history =
                PlayerHistoryEntity.newEntry(
                        playerUuid,
                        type,
                        title,
                        description,
                        metadata,
                        Instant.now()
                );

        PlayerHistoryEntity persisted =
                historyRepository.insert(
                        history
                );

        LOGGER.info(
                "Histórico registrado: id={} | jogador={} | tipo={}",
                persisted.id(),
                playerUuid,
                type
        );
    }

    private void validateText(
            String value,
            String fieldName
    ) {

        if (
                value == null
                        || value.isBlank()
        ) {

            throw new IllegalArgumentException(
                    fieldName
                            + " não pode ser vazio."
            );
        }
    }

    private String normalizeNullable(
            String value
    ) {

        if (
                value == null
                        || value.isBlank()
        ) {
            return "UNKNOWN";
        }

        return value;
    }

    private String escapeJson(
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