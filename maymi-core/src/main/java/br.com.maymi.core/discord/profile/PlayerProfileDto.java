package br.com.maymi.core.discord.profile;

import java.time.Instant;
import java.util.UUID;

public record PlayerProfileDto(
        UUID playerUuid,
        String nickname,
        Instant firstJoinAt,
        Instant lastSeenAt,
        int loginCount,
        long maymiXp,
        int level,
        int rankingPosition,
        long deaths,
        long mobKills,
        long blocksPlaced,
        long blocksBroken,
        long playTimeSeconds

) {
}