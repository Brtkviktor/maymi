package br.com.maymi.core.mod;

import br.com.maymi.core.mod.create.CreateBlockAction;
import br.com.maymi.core.mod.create.CreateIntegration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.UUID;

public final class ModDetectionService {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(
                    ModDetectionService.class
            );

    private final CreateIntegration createIntegration;

    public ModDetectionService(
            CreateIntegration createIntegration
    ) {

        this.createIntegration =
                Objects.requireNonNull(
                        createIntegration,
                        "CreateIntegration não pode ser nulo."
                );
    }

    public void detectBlock(
            String blockType,
            UUID playerUuid,
            String playerName,
            CreateBlockAction action
    ) {

        ModContentId content =
                ModContentId.parse(
                        blockType
                );

        if (content.isVanilla()) {

            LOGGER.debug(
                    "Conteúdo vanilla detectado: {}",
                    content.registryId()
            );

            return;
        }

        LOGGER.info(
                "Conteúdo modded detectado | mod={} | bloco={} | jogador={} | ação={}",
                content.namespace(),
                content.registryId(),
                playerName,
                action
        );

        if (
                content.namespace()
                        .equals("create")
        ) {

            createIntegration.handleBlock(
                    content,
                    playerUuid,
                    playerName,
                    action
            );
        }
    }
}