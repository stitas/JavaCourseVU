package products;

public class ProductFactory {
    public static Product create(String title, float price, ProductCategory productCategory){
        switch(productCategory){
            case BOOKS:
                return new Book(title, price);
            case CLOTHING:
                return new Clothing(title, price);
            case ELECTRONICS:
                return new Electronic(title, price);
            default:
                throw new IllegalArgumentException("Uknown Category " + productCategory);
        }
    }
}
