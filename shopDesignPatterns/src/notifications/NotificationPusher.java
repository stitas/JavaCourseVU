package notifications;

import products.IProduct;

import java.util.ArrayList;
import java.util.List;

public class NotificationPusher implements Observable {
    private static NotificationPusher instance;
    private final List<Observer> observers;

    private NotificationPusher() {
        observers = new ArrayList<>();
    }

    /** SINGLETON **/
    public static NotificationPusher getInstance() {
        if(instance == null){
            instance = new NotificationPusher();
        }
        return instance;
    }

    @Override
    public void attach(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void detach(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(IProduct product) {
        for(Observer observer : observers){
            observer.onProductCreated(product);
        }
    }
}
