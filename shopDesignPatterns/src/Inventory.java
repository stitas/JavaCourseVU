import notifications.NotificationPusher;
import products.IProduct;
import products.Product;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private static Inventory instance;
    private List<IProduct> products;
    private NotificationPusher notificationPusher;

    private Inventory(){
        products = new ArrayList<>();
        notificationPusher = NotificationPusher.getInstance();
    }

    /** SINGLETON **/
    public static Inventory getInstance(){
        if(instance == null){
            instance = new Inventory();
        }

        return instance;
    }

    /** Add new product to inventory **/
    public void addProduct(IProduct product){
        products.add(product);

        // Notify observers that a new product has been added
        notificationPusher.notifyObservers(product);
    }

    /** List all products in inventory **/
    public void listProducts() {
        for(IProduct product : products){
            System.out.println(product.toString());
        }
    }

    public IProduct getProduct(int index) {
        return products.get(index);
    }
}
