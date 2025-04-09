package products;

public class Electronic extends Product{
    private final ProductCategory productCategory;

    public Electronic(String title, float price){
        super(title, price);
        this.productCategory = ProductCategory.ELECTRONICS;
    }

    @Override
    public ProductCategory getCategory() {
        return productCategory;
    }
}
