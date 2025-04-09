package notifications;

import products.IProduct;
import products.Product;

public interface Observer {
    void onProductCreated(IProduct product);
}
