package event;

public interface Observer {
    void onNotify(Event<?> e);
}
