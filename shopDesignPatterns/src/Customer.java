import notifications.Observer;
import products.IProduct;

public class Customer implements Observer {
    private String name;

    public Customer(String name) {
        this.name = name;
    }

    @Override
    public void onProductCreated(IProduct product) {
        System.out.println("Hello " + this.name + " " + product.toString() + " has been created");
    }
}
