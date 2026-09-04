package br.com.maymi.core.persistence.entity;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public final class PlayerEntity {

    private final UUID uuid;
    private final String nickname;

    private final Instant firstJoinAt;
    private final Instant lastSeenAt;

    private final int loginCount;

    private final Instant createdAt;
    private final Instant updatedAt;

    public PlayerEntity(
            UUID uuid,
            String nickname,
            Instant firstJoinAt,
            Instant lastSeenAt,
            int loginCount,
            Instant createdAt,
            Instant updatedAt
    ) {

        this.uuid =
                Objects.requireNonNull(
                        uuid,
                        "UUID não pode ser nulo."
                );

        this.nickname =
                Objects.requireNonNull(
                        nickname,
                        "Nickname não pode ser nulo."
                );

        this.firstJoinAt =
                Objects.requireNonNull(
                        firstJoinAt,
                        "Primeira entrada não pode ser nula."
                );

        this.lastSeenAt =
                Objects.requireNonNull(
                        lastSeenAt,
                        "Última atividade não pode ser nula."
                );

        if (loginCount < 0) {
            throw new IllegalArgumentException(
                    "Quantidade de logins não pode ser negativa."
            );
        }

        this.loginCount = loginCount;

        this.createdAt =
                Objects.requireNonNull(
                        createdAt,
                        "Data de criação não pode ser nula."
                );

        this.updatedAt =
                Objects.requireNonNull(
                        updatedAt,
                        "Data de atualização não pode ser nula."
                );
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getNickname() {
        return nickname;
    }

    public Instant getFirstJoinAt() {
        return firstJoinAt;
    }

    public Instant getLastSeenAt() {
        return lastSeenAt;
    }

    public int getLoginCount() {
        return loginCount;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}