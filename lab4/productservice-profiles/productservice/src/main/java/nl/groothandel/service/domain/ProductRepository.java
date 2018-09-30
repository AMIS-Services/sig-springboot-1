package nl.groothandel.service.domain;

import java.util.Map;

public interface ProductRepository {
    void setProducts(Map<String, Product> products);

    Map<String, Product> getProducts();

    boolean addProduct(String id, Product product);

    boolean updateProduct(String id, Product product);
}
