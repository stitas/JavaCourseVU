package products;

public class Book extends Product {
    private final ProductCategory productCategory;

    public Book(String title, float price){
        super(title, price);
        this.productCategory = ProductCategory.BOOKS;
    }

    @Override
    public ProductCategory getCategory() {
        return productCategory;
    }
}
