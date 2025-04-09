package products;

public abstract class Product implements IProduct{
    private final String title;
    private final float price;

    protected Product(String title, float price){
        this.title = title;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public float getPrice() {
        return price;
    }

    public abstract ProductCategory getCategory();

    @Override
    public String toString() {
        return "Product{" +
                "title='" + getTitle() + '\'' +
                ", price=" + getPrice() +
                '}';
    }
}
