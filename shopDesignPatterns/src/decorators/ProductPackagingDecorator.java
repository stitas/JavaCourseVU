package decorators;

import products.IProduct;

public class ProductPackagingDecorator extends ProductDecorator {
    public ProductPackagingDecorator(IProduct product) {
        super(product);
    }

    @Override
    public String getTitle() {
        return product.getTitle() + " with packaging";
    }

    @Override
    public float getPrice() {
        return product.getPrice() + 2.99f;
    }

    @Override
    public String toString() {
        return "Product{" +
                "title='" + getTitle() + '\'' +
                ", price=" + getPrice() +
                '}';
    }
}
