package br.com.maymi.core.progression;

public final class LevelCalculator {

    private static final long XP_BASE = 50L;

    private LevelCalculator() {
    }

    public static int calculateLevel(
            long xp
    ) {

        long safeXp =
                Math.max(
                        0,
                        xp
                );

        return (int) Math.floor(
                Math.sqrt(
                        safeXp / (double) XP_BASE
                )
        ) + 1;
    }

    public static long minimumXpForLevel(
            int level
    ) {

        if (level <= 1) {
            return 0;
        }

        long levelIndex =
                level - 1L;

        return XP_BASE
                * levelIndex
                * levelIndex;
    }
}