package payment;

public class PaypalPaymentStrategy implements PaymentStrategy{
    private final String email;

    public PaypalPaymentStrategy(String email){
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public void pay(float amount) {
        System.out.println("Payment of " + amount + " has been payed with paypal account: " + getEmail());
    }
}
