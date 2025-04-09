package products;

public class Clothing extends Product {
    private final ProductCategory productCategory;

    public Clothing(String title, float price){
        super(title, price);
        this.productCategory = ProductCategory.CLOTHING;
    }

    @Override
    public ProductCategory getCategory() {
        return productCategory;
    }
}
