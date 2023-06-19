package event;

import java.util.ArrayList;

public class EventSystem {
    private static final ArrayList<Observer> observers = new ArrayList<>();

    public static void addObserver(Observer observer) {
        observers.add(observer);
    }

    public static void notify(Event<?> e) {
        for (Observer o : observers) {
            o.onNotify(e);
        }
    }
}
