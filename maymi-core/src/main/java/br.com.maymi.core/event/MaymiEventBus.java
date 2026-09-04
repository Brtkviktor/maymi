package br.com.maymi.core.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public final class MaymiEventBus {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(
                    MaymiEventBus.class
            );

    private final Map<
            Class<? extends GameEvent>,
            List<GameEventListener<? extends GameEvent>>
            > listeners =
            new ConcurrentHashMap<>();

    public <T extends GameEvent> void register(
            Class<T> eventType,
            GameEventListener<T> listener
    ) {

        Objects.requireNonNull(
                eventType,
                "O tipo do evento não pode ser nulo."
        );

        Objects.requireNonNull(
                listener,
                "O listener não pode ser nulo."
        );

        listeners.computeIfAbsent(
                eventType,
                ignored -> new CopyOnWriteArrayList<>()
        ).add(listener);

        LOGGER.info(
                "Listener registrado para o evento: {}",
                eventType.getSimpleName()
        );
    }

    public <T extends GameEvent> void unregister(
            Class<T> eventType,
            GameEventListener<T> listener
    ) {

        Objects.requireNonNull(
                eventType,
                "O tipo do evento não pode ser nulo."
        );

        Objects.requireNonNull(
                listener,
                "O listener não pode ser nulo."
        );

        List<GameEventListener<? extends GameEvent>>
                registeredListeners =
                listeners.get(eventType);

        if (registeredListeners == null) {
            return;
        }

        registeredListeners.remove(listener);

        if (registeredListeners.isEmpty()) {
            listeners.remove(eventType);
        }
    }

    public void publish(
            GameEvent event
    ) {

        Objects.requireNonNull(
                event,
                "O evento não pode ser nulo."
        );

        List<GameEventListener<? extends GameEvent>>
                registeredListeners =
                listeners.getOrDefault(
                        event.getClass(),
                        List.of()
                );

        LOGGER.debug(
                "Publicando evento {} para {} listener(s).",
                event.getClass().getSimpleName(),
                registeredListeners.size()
        );

        for (
                GameEventListener<? extends GameEvent> listener :
                registeredListeners
        ) {

            notifyListener(
                    listener,
                    event
            );
        }
    }

    @SuppressWarnings("unchecked")
    private <T extends GameEvent> void notifyListener(
            GameEventListener<? extends GameEvent> listener,
            T event
    ) {

        try {

            GameEventListener<T> typedListener =
                    (GameEventListener<T>) listener;

            typedListener.onEvent(
                    event
            );

        } catch (Exception exception) {

            LOGGER.error(
                    "Erro ao processar o evento {} no listener {}.",
                    event.getClass().getSimpleName(),
                    listener.getClass().getSimpleName(),
                    exception
            );
        }
    }
}