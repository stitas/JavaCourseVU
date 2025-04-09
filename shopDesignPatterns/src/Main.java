/**
 * @author Titas Stongvila 5 grupė
 **/

import decorators.ProductPackagingDecorator;
import decorators.ProductPaintingDecorator;
import notifications.NotificationPusher;
import payment.BitcoinPaymentStrategy;
import payment.CardPaymentStrategy;
import payment.PaypalPaymentStrategy;
import payment.ShoppingCart;
import products.*;

public class Main {
    public static void main(String[] args) {
        /** Create customers **/
        Customer customer1 = new Customer("CustomerOne");
        Customer customer2 = new Customer("CustomerTwo");

        /** Get notifications pusher instance**/
        NotificationPusher notificationPusher = NotificationPusher.getInstance();
        notificationPusher.attach(customer1);
        notificationPusher.attach(customer2);

        Product computer = ProductFactory.create("Lenovo ThinkPad", 789.99f, ProductCategory.ELECTRONICS);
        Product pants = ProductFactory.create("Pants", 30.00f, ProductCategory.CLOTHING);
        Product book = ProductFactory.create("Book", 19.99f, ProductCategory.BOOKS);

        /** Get inventory instance**/
        Inventory inventory = Inventory.getInstance();
        inventory.addProduct(new ProductPaintingDecorator(computer));
        inventory.addProduct(new ProductPackagingDecorator(pants));
        inventory.addProduct(book);
        inventory.listProducts();

        /** Create cart instance**/
        ShoppingCart cart = ShoppingCart.getInstance();
        cart.addToCart(inventory.getProduct(0));
        cart.checkout(new CardPaymentStrategy("1234 5678 9000 0000"));

        cart.addToCart(inventory.getProduct(1));
        cart.checkout(new PaypalPaymentStrategy("labas@email.com"));

        cart.addToCart(inventory.getProduct(2));
        cart.checkout(new BitcoinPaymentStrategy("bc1qxy2kgdygjrsqtzq2n0yrf2493p83kkfjhx0wlh"));

        /** Add more products to test notifications **/
        Product tShirt = ProductFactory.create("T-Shirt", 15.76f, ProductCategory.CLOTHING);
        inventory.addProduct(new ProductPaintingDecorator(new ProductPackagingDecorator(tShirt)));
    }
}