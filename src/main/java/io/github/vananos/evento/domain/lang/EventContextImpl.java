package io.github.vananos.evento.domain.lang;

class EventContextImpl<E> implements EventContext<E> {
    private final E event;
    private boolean processingStopped;

    EventContextImpl(E event) {
        this.event = event;
    }

    @Override
    public E getEvent() {
        return event;
    }

    @Override
    public void stopProcessing() {
        processingStopped = true;
    }

    @Override
    public boolean isProcessingStopped() {
        return processingStopped;
    }
}
