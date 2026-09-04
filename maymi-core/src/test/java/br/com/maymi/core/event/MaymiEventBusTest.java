package br.com.maymi.core.event;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertTrue;

class MaymiEventBusTest {

    @Test
    void devePublicarEventoParaListenerRegistrado() {

        MaymiEventBus eventBus =
                new MaymiEventBus();

        AtomicBoolean eventReceived =
                new AtomicBoolean(false);

        GameEventListener<TestEvent> listener =
                event -> eventReceived.set(true);

        eventBus.register(
                TestEvent.class,
                listener
        );

        eventBus.publish(
                new TestEvent(
                        Instant.now()
                )
        );

        assertTrue(
                eventReceived.get()
        );
    }

    private record TestEvent(
            Instant occurredAt
    ) implements GameEvent {
    }
}