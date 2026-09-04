package br.com.maymi.core.discord.profile;

import java.util.UUID;

public record PlayerRankingDto(
        int position,
        UUID playerUuid,
        String nickname,
        long maymiXp,
        int level
) {
}