package br.com.maymi.core.achievement;

import br.com.maymi.core.discord.profile.PlayerProfileDto;
import br.com.maymi.core.discord.profile.PlayerProfileRepository;
import br.com.maymi.core.event.MaymiEventBus;
import br.com.maymi.core.event.player.MaymiAchievementUnlockEvent;
import br.com.maymi.core.progression.PlayerXpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public final class AchievementUnlockService {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(
                    AchievementUnlockService.class
            );

    private final PlayerProfileRepository profileRepository;

    private final PlayerAchievementRepository
            playerAchievementRepository;

    private final PlayerXpService xpService;

    private final MaymiEventBus eventBus;

    public AchievementUnlockService(
            PlayerProfileRepository profileRepository,
            PlayerAchievementRepository playerAchievementRepository,
            PlayerXpService xpService,
            MaymiEventBus eventBus
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

        this.xpService =
                Objects.requireNonNull(
                        xpService,
                        "PlayerXpService não pode ser nulo."
                );

        this.eventBus =
                Objects.requireNonNull(
                        eventBus,
                        "MaymiEventBus não pode ser nulo."
                );
    }

    public void verifyPlayer(
            UUID playerUuid,
            String nickname
    ) {

        validatePlayer(
                playerUuid,
                nickname
        );

        /*
         * A identidade do jogador é o UUID.
         *
         * Não buscamos mais por nickname porque dois registros
         * podem possuir o mesmo nome em ambientes diferentes,
         * principalmente durante testes NeoForge offline.
         */
        var optionalProfile =
                profileRepository.findByUuid(
                        playerUuid
                );

        if (optionalProfile.isEmpty()) {

            LOGGER.warn(
                    "Perfil não encontrado durante a verificação "
                            + "de conquistas: {} ({})",
                    nickname,
                    playerUuid
            );

            return;
        }

        PlayerProfileDto profile =
                optionalProfile.get();

        for (
                Achievement achievement
                : AchievementRegistry.findAll()
        ) {

            verifyAchievement(
                    profile,
                    achievement
            );
        }
    }

    private void verifyAchievement(
            PlayerProfileDto profile,
            Achievement achievement
    ) {

        boolean alreadyUnlocked =
                playerAchievementRepository.hasUnlocked(
                        profile.playerUuid(),
                        achievement.id()
                );

        if (alreadyUnlocked) {
            return;
        }

        long currentValue =
                resolveCurrentValue(
                        profile,
                        achievement.metric()
                );

        if (!achievement.isCompleted(currentValue)) {
            return;
        }

        Instant unlockedAt =
                Instant.now();

        boolean unlocked =
                playerAchievementRepository.unlock(
                        profile.playerUuid(),
                        achievement.id(),
                        unlockedAt
                );

        /*
         * O INSERT OR IGNORE garante que dois eventos
         * simultâneos não concedam a mesma conquista
         * e a recompensa duas vezes.
         */
        if (!unlocked) {
            return;
        }

        if (achievement.xpReward() > 0) {

            xpService.addXp(
                    profile.playerUuid(),
                    profile.nickname(),
                    achievement.xpReward()
            );
        }

        eventBus.publish(
                new MaymiAchievementUnlockEvent(
                        profile.playerUuid(),
                        profile.nickname(),
                        achievement,
                        currentValue,
                        unlockedAt
                )
        );

        LOGGER.info(
                "Conquista desbloqueada: {} recebeu {} "
                        + "({}) e ganhou {} XP.",
                profile.nickname(),
                achievement.name(),
                achievement.id(),
                achievement.xpReward()
        );
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

    private void validatePlayer(
            UUID playerUuid,
            String nickname
    ) {

        Objects.requireNonNull(
                playerUuid,
                "UUID do jogador não pode ser nulo."
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