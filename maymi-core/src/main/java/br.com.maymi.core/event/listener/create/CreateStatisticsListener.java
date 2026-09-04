package br.com.maymi.core.event.listener.create;

import br.com.maymi.core.event.mod.create.MaymiCreateBlockEvent;
import br.com.maymi.core.mod.create.persistence.CreateStatisticsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public final class CreateStatisticsListener {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(
                    CreateStatisticsListener.class
            );

    private final CreateStatisticsRepository repository;

    public CreateStatisticsListener(
            CreateStatisticsRepository repository
    ) {

        this.repository =
                Objects.requireNonNull(
                        repository,
                        "CreateStatisticsRepository não pode ser nulo."
                );
    }

    public void handle(
            MaymiCreateBlockEvent event
    ) {

        repository.registerAction(
                event.playerUuid(),
                event.category(),
                event.action(),
                event.occurredAt()
        );

        LOGGER.info(
                "Estatística Create persistida | jogador={} | ação={} | bloco={} | categoria={}",
                event.playerName(),
                event.action(),
                event.registryId(),
                event.category()
        );
    }
}