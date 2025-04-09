package decorators;

import products.IProduct;

public class ProductPaintingDecorator extends ProductDecorator {
    public ProductPaintingDecorator(IProduct product) {
        super(product);
    }

    @Override
    public String getTitle() {
        return product.getTitle() + " changed color";
    }

    @Override
    public float getPrice() {
        return product.getPrice() + 5.89f;
    }

    @Override
    public String toString() {
        return "Product{" +
                "title='" + getTitle() + '\'' +
                ", price=" + getPrice() +
                '}';
    }
}
