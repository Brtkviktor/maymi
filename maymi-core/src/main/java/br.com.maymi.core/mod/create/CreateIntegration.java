package br.com.maymi.core.mod.create;

import br.com.maymi.core.event.MaymiEventBus;
import br.com.maymi.core.event.mod.create.MaymiCreateBlockEvent;
import br.com.maymi.core.mod.ModContentId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public final class CreateIntegration {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(
                    CreateIntegration.class
            );

    private final CreateBlockClassifier blockClassifier;
    private final MaymiEventBus eventBus;

    public CreateIntegration(
            CreateBlockClassifier blockClassifier,
            MaymiEventBus eventBus
    ) {

        this.blockClassifier =
                Objects.requireNonNull(
                        blockClassifier,
                        "CreateBlockClassifier não pode ser nulo."
                );

        this.eventBus =
                Objects.requireNonNull(
                        eventBus,
                        "MaymiEventBus não pode ser nulo."
                );
    }

    public void handleBlock(
            ModContentId content,
            UUID playerUuid,
            String playerName,
            CreateBlockAction action
    ) {

        Objects.requireNonNull(
                content,
                "ModContentId não pode ser nulo."
        );

        Objects.requireNonNull(
                playerUuid,
                "UUID do jogador não pode ser nulo."
        );

        Objects.requireNonNull(
                action,
                "Ação do bloco não pode ser nula."
        );

        if (!content.namespace().equals("create")) {
            return;
        }

        CreateBlockCategory category =
                blockClassifier.classify(
                        content.path()
                );

        LOGGER.info(
                "Create detectado | jogador={} | bloco={} | categoria={} | ação={}",
                playerName,
                content.registryId(),
                category,
                action
        );

        eventBus.publish(
                new MaymiCreateBlockEvent(
                        playerUuid,
                        playerName,
                        content.registryId(),
                        content.path(),
                        category,
                        action,
                        Instant.now()
                )
        );
    }
}