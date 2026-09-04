package br.com.maymi.core.persistence.entity;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public final class PlayerStatisticsEntity {

    private final UUID playerUuid;

    private final long maymiXp;
    private final int level;

    private final long deaths;
    private final long mobKills;

    private final long blocksPlaced;
    private final long blocksBroken;

    private final long playTimeSeconds;

    private final Instant createdAt;
    private final Instant updatedAt;

    private final Instant sessionStartAt;

    public PlayerStatisticsEntity(
            UUID playerUuid,
            long maymiXp,
            int level,
            long deaths,
            long mobKills,
            long blocksPlaced,
            long blocksBroken,
            long playTimeSeconds,
            Instant createdAt,
            Instant updatedAt,
            Instant sessionStartAt
    ) {

        this.playerUuid =
                Objects.requireNonNull(playerUuid);

        this.maymiXp = maymiXp;
        this.level = level;
        this.deaths = deaths;
        this.mobKills = mobKills;
        this.blocksPlaced = blocksPlaced;
        this.blocksBroken = blocksBroken;
        this.playTimeSeconds = playTimeSeconds;

        this.createdAt =
                Objects.requireNonNull(createdAt);

        this.updatedAt =
                Objects.requireNonNull(updatedAt);

        this.sessionStartAt = sessionStartAt;
    }

    public UUID getPlayerUuid() {
        return playerUuid;
    }

    public long getMaymiXp() {
        return maymiXp;
    }

    public int getLevel() {
        return level;
    }

    public long getDeaths() {
        return deaths;
    }

    public long getMobKills() {
        return mobKills;
    }

    public long getBlocksPlaced() {
        return blocksPlaced;
    }

    public long getBlocksBroken() {
        return blocksBroken;
    }

    public long getPlayTimeSeconds() {
        return playTimeSeconds;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Instant getSessionStartAt() {
        return sessionStartAt;
    }
}