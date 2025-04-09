package decorators;

import products.IProduct;
import products.Product;
import products.ProductCategory;

public abstract class ProductDecorator implements IProduct {
    protected IProduct product;

    public ProductDecorator(IProduct product){
        this.product = product;
    }

    @Override
    public String getTitle() {
        return product.getTitle();
    }

    @Override
    public float getPrice() {
        return product.getPrice();
    }

    @Override
    public ProductCategory getCategory() {
        return product.getCategory();
    }

    @Override
    public String toString() {
        return product.toString();
    }
}
