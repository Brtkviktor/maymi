package br.com.maymi.core.history;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public record PlayerHistoryEntity(
        long id,
        UUID playerUuid,
        HistoryType type,
        String title,
        String description,
        String metadata,
        Instant createdAt
) {

    public PlayerHistoryEntity {

        if (id < 0) {
            throw new IllegalArgumentException(
                    "ID do histórico não pode ser negativo."
            );
        }

        Objects.requireNonNull(
                playerUuid,
                "UUID do jogador não pode ser nulo."
        );

        Objects.requireNonNull(
                type,
                "Tipo do histórico não pode ser nulo."
        );

        if (
                title == null
                        || title.isBlank()
        ) {

            throw new IllegalArgumentException(
                    "Título do histórico não pode ser vazio."
            );
        }

        if (
                description == null
                        || description.isBlank()
        ) {

            throw new IllegalArgumentException(
                    "Descrição do histórico não pode ser vazia."
            );
        }

        /*
         * Metadata pode ser "{}".
         * Não deixaremos null para simplificar
         * consultas futuras e integração com IA.
         */
        if (
                metadata == null
                        || metadata.isBlank()
        ) {

            metadata = "{}";
        }

        Objects.requireNonNull(
                createdAt,
                "Data do histórico não pode ser nula."
        );
    }

    public static PlayerHistoryEntity newEntry(
            UUID playerUuid,
            HistoryType type,
            String title,
            String description,
            String metadata,
            Instant createdAt
    ) {

        return new PlayerHistoryEntity(
                0,
                playerUuid,
                type,
                title,
                description,
                metadata,
                createdAt
        );
    }
}