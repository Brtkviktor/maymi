package br.com.maymi.core.event.mod.create;

import br.com.maymi.core.event.GameEvent;
import br.com.maymi.core.mod.create.CreateBlockAction;
import br.com.maymi.core.mod.create.CreateBlockCategory;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public record MaymiCreateBlockEvent(
        UUID playerUuid,
        String playerName,
        String registryId,
        String blockPath,
        CreateBlockCategory category,
        CreateBlockAction action,
        Instant occurredAt
) implements GameEvent {

    public MaymiCreateBlockEvent {

        Objects.requireNonNull(
                playerUuid,
                "UUID do jogador não pode ser nulo."
        );

        Objects.requireNonNull(
                category,
                "Categoria do Create não pode ser nula."
        );

        Objects.requireNonNull(
                action,
                "Ação do Create não pode ser nula."
        );

        Objects.requireNonNull(
                occurredAt,
                "Data do evento não pode ser nula."
        );

        if (
                playerName == null
                        || playerName.isBlank()
        ) {

            throw new IllegalArgumentException(
                    "Nome do jogador não pode ser vazio."
            );
        }

        if (
                registryId == null
                        || registryId.isBlank()
        ) {

            throw new IllegalArgumentException(
                    "Registry ID não pode ser vazio."
            );
        }

        if (
                blockPath == null
                        || blockPath.isBlank()
        ) {

            throw new IllegalArgumentException(
                    "Path do bloco não pode ser vazio."
            );
        }
    }
}