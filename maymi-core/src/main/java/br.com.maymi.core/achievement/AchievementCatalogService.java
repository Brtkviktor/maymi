package br.com.maymi.core.achievement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public final class AchievementCatalogService {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(
                    AchievementCatalogService.class
            );

    private final AchievementRepository achievementRepository;

    public AchievementCatalogService(
            AchievementRepository achievementRepository
    ) {

        this.achievementRepository =
                Objects.requireNonNull(
                        achievementRepository,
                        "AchievementRepository não pode ser nulo."
                );
    }

    public void synchronizeRegistry() {

        var achievements =
                AchievementRegistry.findAll();

        achievementRepository.synchronize(
                achievements
        );

        long persistedAchievements =
                achievementRepository.count();

        LOGGER.info(
                "Catálogo de conquistas sincronizado: "
                        + "{} registradas no código, "
                        + "{} persistidas no banco.",
                achievements.size(),
                persistedAchievements
        );
    }
}