package notifications;

import products.IProduct;

public interface Observable {
    void attach(Observer observer);
    void detach(Observer observer);
    void notifyObservers(IProduct product);
}
