package br.com.maymi.core.event;

@FunctionalInterface
public interface GameEventListener<T extends GameEvent> {

    void onEvent(T event);
}