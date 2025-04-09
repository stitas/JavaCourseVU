package payment;

public class CardPaymentStrategy implements PaymentStrategy{
    private final String cardNumber;

    public CardPaymentStrategy(String cardNumber){
        this.cardNumber = cardNumber;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    @Override
    public void pay(float amount) {
        System.out.println("Payment of " + amount + " has been payed with card: " + getCardNumber());
    }
}
