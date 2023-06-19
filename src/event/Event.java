package event;

public class Event<T> {
    private EventType type;
    private T data;

    public Event(T data) {
        this.type = EventType.GENERAL;
        this.data = data;
    }

    public Event(EventType type, T data) {
        this.type = type;
        this.data = data;
    }

    public EventType getType() {
        return type;
    }

    public T getData() {
        return data;
    }
}
