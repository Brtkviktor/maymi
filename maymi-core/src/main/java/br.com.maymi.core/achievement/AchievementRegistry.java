package br.com.maymi.core.achievement;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class AchievementRegistry {

    private static final List<Achievement> ACHIEVEMENTS =
            List.of(

                    // =============================================
                    // COMBATE
                    // =============================================

                    new Achievement(
                            "hunter_1",
                            "Caçador I",
                            "Abata 10 mobs.",
                            AchievementCategory.COMBAT,
                            AchievementRarity.COMMON,
                            AchievementMetric.MOB_KILLS,
                            10,
                            25
                    ),

                    new Achievement(
                            "hunter_2",
                            "Caçador II",
                            "Abata 50 mobs.",
                            AchievementCategory.COMBAT,
                            AchievementRarity.UNCOMMON,
                            AchievementMetric.MOB_KILLS,
                            50,
                            50
                    ),

                    new Achievement(
                            "hunter_3",
                            "Caçador III",
                            "Abata 250 mobs.",
                            AchievementCategory.COMBAT,
                            AchievementRarity.RARE,
                            AchievementMetric.MOB_KILLS,
                            250,
                            150
                    ),

                    // =============================================
                    // MINERAÇÃO
                    // =============================================

                    new Achievement(
                            "miner_1",
                            "Mineiro I",
                            "Quebre 100 blocos.",
                            AchievementCategory.MINING,
                            AchievementRarity.COMMON,
                            AchievementMetric.BLOCKS_BROKEN,
                            100,
                            25
                    ),

                    new Achievement(
                            "miner_2",
                            "Mineiro II",
                            "Quebre 500 blocos.",
                            AchievementCategory.MINING,
                            AchievementRarity.UNCOMMON,
                            AchievementMetric.BLOCKS_BROKEN,
                            500,
                            75
                    ),

                    new Achievement(
                            "miner_3",
                            "Mineiro III",
                            "Quebre 2.000 blocos.",
                            AchievementCategory.MINING,
                            AchievementRarity.RARE,
                            AchievementMetric.BLOCKS_BROKEN,
                            2_000,
                            200
                    ),

                    // =============================================
                    // CONSTRUÇÃO
                    // =============================================

                    new Achievement(
                            "builder_1",
                            "Construtor I",
                            "Coloque 100 blocos.",
                            AchievementCategory.BUILDING,
                            AchievementRarity.COMMON,
                            AchievementMetric.BLOCKS_PLACED,
                            100,
                            25
                    ),

                    new Achievement(
                            "builder_2",
                            "Construtor II",
                            "Coloque 500 blocos.",
                            AchievementCategory.BUILDING,
                            AchievementRarity.UNCOMMON,
                            AchievementMetric.BLOCKS_PLACED,
                            500,
                            75
                    ),

                    // =============================================
                    // ATIVIDADE
                    // =============================================

                    new Achievement(
                            "traveler_1",
                            "Viajante I",
                            "Entre 10 vezes no servidor.",
                            AchievementCategory.ACTIVITY,
                            AchievementRarity.COMMON,
                            AchievementMetric.LOGIN_COUNT,
                            10,
                            20
                    ),

                    new Achievement(
                            "traveler_2",
                            "Viajante II",
                            "Entre 50 vezes no servidor.",
                            AchievementCategory.ACTIVITY,
                            AchievementRarity.UNCOMMON,
                            AchievementMetric.LOGIN_COUNT,
                            50,
                            75
                    ),

                    // =============================================
                    // PROGRESSÃO
                    // =============================================

                    new Achievement(
                            "level_5",
                            "Primeiros Passos",
                            "Alcance o nível 5.",
                            AchievementCategory.PROGRESSION,
                            AchievementRarity.COMMON,
                            AchievementMetric.LEVEL,
                            5,
                            50
                    ),

                    new Achievement(
                            "level_10",
                            "Aventureiro",
                            "Alcance o nível 10.",
                            AchievementCategory.PROGRESSION,
                            AchievementRarity.RARE,
                            AchievementMetric.LEVEL,
                            10,
                            150
                    ),

                    // =============================================
                    // TEMPO DE JOGO
                    // =============================================

                    new Achievement(
                            "playtime_1h",
                            "Residente",
                            "Jogue por 1 hora.",
                            AchievementCategory.PLAY_TIME,
                            AchievementRarity.COMMON,
                            AchievementMetric.PLAY_TIME_SECONDS,
                            3_600,
                            30
                    ),

                    new Achievement(
                            "playtime_5h",
                            "Veterano I",
                            "Jogue por 5 horas.",
                            AchievementCategory.PLAY_TIME,
                            AchievementRarity.UNCOMMON,
                            AchievementMetric.PLAY_TIME_SECONDS,
                            18_000,
                            100
                    )
            );

    private static final Map<String, Achievement> BY_ID =
            ACHIEVEMENTS
                    .stream()
                    .collect(
                            Collectors.toUnmodifiableMap(
                                    Achievement::id,
                                    Function.identity()
                            )
                    );

    private AchievementRegistry() {
    }

    public static List<Achievement> findAll() {
        return ACHIEVEMENTS;
    }

    public static Optional<Achievement> findById(
            String achievementId
    ) {

        if (
                achievementId == null
                        || achievementId.isBlank()
        ) {
            return Optional.empty();
        }

        return Optional.ofNullable(
                BY_ID.get(
                        achievementId
                )
        );
    }

    public static List<Achievement> findByMetric(
            AchievementMetric metric
    ) {

        return ACHIEVEMENTS
                .stream()
                .filter(
                        achievement ->
                                achievement.metric() == metric
                )
                .toList();
    }

    public static List<Achievement> findByCategory(
            AchievementCategory category
    ) {

        return ACHIEVEMENTS
                .stream()
                .filter(
                        achievement ->
                                achievement.category() == category
                )
                .toList();
    }
}