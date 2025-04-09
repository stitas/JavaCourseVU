package payment;

import products.IProduct;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private static ShoppingCart instance;
    private final List<IProduct> cart;

    private ShoppingCart() {
        cart = new ArrayList<>();
    }

    public static ShoppingCart getInstance() {
        if(instance == null){
            instance = new ShoppingCart();
        }
        return instance;
    }

    public void addToCart(IProduct product){
        cart.add(product);
    }

    public void removeFromCart(IProduct product){
        cart.remove(product);
    }

    public void listCart(){
        for(IProduct product : cart){
            System.out.println(product.toString());
        }
    }

    private float calculateTotal() {
        float total = 0;

        for(IProduct product : cart){
            total += product.getPrice();
        }

        return total;
    }

    public void checkout(PaymentStrategy paymentStrategy) {
        float total = calculateTotal();

        paymentStrategy.pay(total);

        cart.clear();
    }
}
