package nl.groothandel.service.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ProductRepositoryImpl {

    private Map<String, Product> products;
    {
        Map<String, Product> map = new HashMap<>();
        map.put("GLNF", new Product("GLNF", "Glenfiddich 18 Years", "Whisky", "Glenfiddich", "70cl", 42, 39.95));
        map.put("PULT", new Product("PULT", "Old Pulteney 17 years Single Malt", "Whisky", "Old Pulteney", "70cl", 41, 59.95));
        this.products = map;
    }

    public void setProducts(Map<String, Product> products) {
        this.products = products;
    }

    public Map<String, Product> getProducts() {
        return products.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public boolean addProduct (String id, Product product) {
        if (products.containsKey(id)) {
            return false;
        } else {
            products.put(id, product);
            return true;
        }
    }

    public boolean updateProduct (String id, Product product) {
        if (products.containsKey(id)) {
            products.remove(id);
            products.put(id, product);
            return true;
        } else {
            return false;
        }
    }	
}
