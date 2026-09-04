package br.com.maymi.paper.player.session;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerSessionManager {

    private final Map<UUID, Instant> activeSessions =
            new ConcurrentHashMap<>();

    public void startSession(UUID playerId) {
        activeSessions.put(
                playerId,
                Instant.now()
        );
    }

    public void endSession(UUID playerId) {
        activeSessions.remove(playerId);
    }

    public long getSessionDurationMillis(UUID playerId) {

        Instant startedAt =
                activeSessions.get(playerId);

        if (startedAt == null) {
            return 0L;
        }

        return Duration.between(
                startedAt,
                Instant.now()
        ).toMillis();
    }

    public boolean hasActiveSession(UUID playerId) {
        return activeSessions.containsKey(playerId);
    }
}