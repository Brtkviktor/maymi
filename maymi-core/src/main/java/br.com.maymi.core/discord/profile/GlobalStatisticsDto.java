package br.com.maymi.core.discord.profile;

public record GlobalStatisticsDto(
        long totalPlayers,
        long totalXp,
        long totalDeaths,
        long totalMobKills,
        long totalBlocksPlaced,
        long totalBlocksBroken,
        long totalPlayTimeSeconds
) {
}