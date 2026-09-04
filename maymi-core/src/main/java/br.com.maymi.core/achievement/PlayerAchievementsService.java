package br.com.maymi.core.achievement;

import br.com.maymi.core.discord.profile.PlayerProfileDto;
import br.com.maymi.core.discord.profile.PlayerProfileRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class PlayerAchievementsService {

    private final PlayerProfileRepository profileRepository;
    private final PlayerAchievementRepository playerAchievementRepository;

    public PlayerAchievementsService(
            PlayerProfileRepository profileRepository,
            PlayerAchievementRepository playerAchievementRepository
    ) {

        this.profileRepository =
                Objects.requireNonNull(
                        profileRepository,
                        "PlayerProfileRepository não pode ser nulo."
                );

        this.playerAchievementRepository =
                Objects.requireNonNull(
                        playerAchievementRepository,
                        "PlayerAchievementRepository não pode ser nulo."
                );
    }

    public List<PlayerAchievementsDto> findByNickname(
            String nickname
    ) {

        if (
                nickname == null
                        || nickname.isBlank()
        ) {

            throw new IllegalArgumentException(
                    "Nickname não pode ser vazio."
            );
        }

        PlayerProfileDto profile =
                profileRepository
                        .findByNickname(
                                nickname.trim()
                        )
                        .orElseThrow(
                                () -> new IllegalArgumentException(
                                        "Jogador não encontrado: "
                                                + nickname
                                )
                        );

        Map<String, PlayerAchievementEntity> unlockedById =
                playerAchievementRepository
                        .findByPlayerUuid(
                                profile.playerUuid()
                        )
                        .stream()
                        .collect(
                                Collectors.toMap(
                                        PlayerAchievementEntity::achievementId,
                                        Function.identity()
                                )
                        );

        return AchievementRegistry
                .findAll()
                .stream()
                .map(
                        achievement -> {

                            PlayerAchievementEntity unlocked =
                                    unlockedById.get(
                                            achievement.id()
                                    );

                            long currentValue =
                                    resolveCurrentValue(
                                            profile,
                                            achievement.metric()
                                    );

                            return new PlayerAchievementsDto(
                                    achievement,
                                    unlocked != null,
                                    currentValue,
                                    unlocked == null
                                            ? null
                                            : unlocked.unlockedAt()
                            );
                        }
                )
                .sorted(
                        Comparator
                                .comparing(
                                        PlayerAchievementsDto::unlocked
                                )
                                .reversed()
                                .thenComparing(
                                        item ->
                                                item.achievement()
                                                        .category()
                                                        .ordinal()
                                )
                                .thenComparingLong(
                                        item ->
                                                item.achievement()
                                                        .targetValue()
                                )
                )
                .toList();
    }

    public long countUnlocked(
            List<PlayerAchievementsDto> achievements
    ) {

        return achievements
                .stream()
                .filter(
                        PlayerAchievementsDto::unlocked
                )
                .count();
    }

    public PlayerAchievementsDto findNextAchievement(
            List<PlayerAchievementsDto> achievements
    ) {

        return achievements
                .stream()
                .filter(
                        achievement ->
                                !achievement.unlocked()
                )
                .max(
                        Comparator.comparingDouble(
                                PlayerAchievementsDto::progressPercentage
                        )
                )
                .orElse(null);
    }

    private long resolveCurrentValue(
            PlayerProfileDto profile,
            AchievementMetric metric
    ) {

        return switch (metric) {

            case MOB_KILLS ->
                    profile.mobKills();

            case BLOCKS_BROKEN ->
                    profile.blocksBroken();

            case BLOCKS_PLACED ->
                    profile.blocksPlaced();

            case LOGIN_COUNT ->
                    profile.loginCount();

            case LEVEL ->
                    profile.level();

            case PLAY_TIME_SECONDS ->
                    profile.playTimeSeconds();
        };
    }
}