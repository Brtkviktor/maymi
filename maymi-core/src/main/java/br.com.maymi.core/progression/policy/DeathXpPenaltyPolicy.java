package br.com.maymi.core.progression.policy;

import java.util.Locale;
import java.util.Set;

public final class DeathXpPenaltyPolicy {

    private static final Set<String> DANGEROUS_HOSTILE_MOBS =
            Set.of(
                    "CREEPER",
                    "ENDERMAN",
                    "BLAZE",
                    "GUARDIAN",
                    "ELDER_GUARDIAN",
                    "RAVAGER",
                    "EVOKER",
                    "VINDICATOR"
            );

    private static final Set<String> COMMON_HOSTILE_MOBS =
            Set.of(
                    "ZOMBIE",
                    "HUSK",
                    "DROWNED",
                    "SKELETON",
                    "STRAY",
                    "SPIDER",
                    "CAVE_SPIDER",
                    "PILLAGER",
                    "WITCH",
                    "SLIME",
                    "MAGMA_CUBE",
                    "PHANTOM",
                    "SILVERFISH"
            );

    private static final Set<String> NEUTRAL_MOBS =
            Set.of(
                    "BEE",
                    "DOLPHIN",
                    "GOAT",
                    "IRON_GOLEM",
                    "LLAMA",
                    "PANDA",
                    "PIGLIN",
                    "POLAR_BEAR",
                    "WOLF",
                    "ZOMBIFIED_PIGLIN"
            );

    public long calculatePenalty(
            String deathCause,
            String killerType
    ) {

        String normalizedKiller =
                normalize(killerType);

        if ("WARDEN".equals(normalizedKiller)) {
            return -20;
        }

        if ("WITHER".equals(normalizedKiller)) {
            return -25;
        }

        if ("ENDER_DRAGON".equals(normalizedKiller)) {
            return -30;
        }

        if ("PLAYER".equals(normalizedKiller)) {
            return -15;
        }

        if (DANGEROUS_HOSTILE_MOBS.contains(normalizedKiller)) {
            return -10;
        }

        if (COMMON_HOSTILE_MOBS.contains(normalizedKiller)) {
            return -7;
        }

        if (NEUTRAL_MOBS.contains(normalizedKiller)) {
            return -5;
        }

        return calculateByCause(
                deathCause
        );
    }

    private long calculateByCause(
            String deathCause
    ) {

        String cause =
                normalize(deathCause);

        return switch (cause) {

            case "FALL",
                 "FLY_INTO_WALL" -> -6;

            case "DROWNING",
                 "FIRE",
                 "FIRE_TICK",
                 "LAVA",
                 "HOT_FLOOR",
                 "FREEZE" -> -5;

            case "VOID",
                 "WORLD_BORDER" -> -10;

            case "STARVATION",
                 "SUFFOCATION",
                 "CRAMMING",
                 "THORNS" -> -3;

            case "BLOCK_EXPLOSION",
                 "ENTITY_EXPLOSION" -> -8;

            case "MAGIC",
                 "POISON",
                 "WITHER",
                 "SONIC_BOOM" -> -8;

            case "PROJECTILE",
                 "ENTITY_ATTACK",
                 "ENTITY_SWEEP_ATTACK" -> -7;

            case "LIGHTNING" -> -6;

            case "SUICIDE",
                 "KILL" -> -10;

            default -> -5;
        };
    }

    private String normalize(
            String value
    ) {

        if (value == null || value.isBlank()) {
            return "";
        }

        return value
                .trim()
                .toUpperCase(Locale.ROOT);
    }
}