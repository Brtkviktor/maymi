package br.com.maymi.common.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class PlayerJoinMessage {

    private UUID playerId;

    private String playerName;

    private LocalDateTime joinedAt;

    public PlayerJoinMessage() {
    }

    public PlayerJoinMessage(UUID playerId, String playerName, LocalDateTime joinedAt) {
        this.playerId = playerId;
        this.playerName = playerName;
        this.joinedAt = joinedAt;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public void setPlayerId(UUID playerId) {
        this.playerId = playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public LocalDateTime getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(LocalDateTime joinedAt) {
        this.joinedAt = joinedAt;
    }

}