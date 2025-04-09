package payment;

public class BitcoinPaymentStrategy implements PaymentStrategy {
    private final String walletAddress;

    public BitcoinPaymentStrategy(String walletAddress){
        this.walletAddress = walletAddress;
    }

    public String getWalletAddress() {
        return walletAddress;
    }

    @Override
    public void pay(float amount){
        System.out.println("Payment of " + amount + " has been payed with bitcoin wallet: " + getWalletAddress());
    }
}
