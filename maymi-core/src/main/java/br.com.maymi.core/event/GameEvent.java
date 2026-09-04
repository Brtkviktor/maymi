package br.com.maymi.core.event;

import java.time.Instant;

public interface GameEvent {

    Instant occurredAt();
}