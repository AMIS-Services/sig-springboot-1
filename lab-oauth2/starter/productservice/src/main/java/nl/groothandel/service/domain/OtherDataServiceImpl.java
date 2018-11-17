package nl.groothandel.service.domain;

import nl.groothandel.service.condition.SodaCondition;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Repository("soda")
public class OtherDataServiceImpl implements DataService {

    private Map<String, Product> products;
    {
        Map<String, Product> map = new HashMap<>();
        map.put("COCACOLA", new Product("COCACOLA", "Coca cola", "Cola", "Coca cola", "1 liter", 0, 1.59));
        map.put("PEPSICOLA", new Product("PEPSICOLA", "Pepsi Cola", "Cola", "Pepsi", "1 liter", 0, 1.36));
        this.products = map;
    }

    @Override
    public void setProducts(Map<String, Product> products) {
        this.products = products;
    }

    @Override
    public Map<String, Product> getProducts() {
        return products.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public boolean addProduct(String id, Product product) {
        if (products.containsKey(id)) {
            return false;
        } else {
            products.put(id, product);
            return true;
        }
    }

    @Override
    public boolean updateProduct(String id, Product product) {
        if (products.containsKey(id)) {
            products.remove(id);
            products.put(id, product);
            return true;
        } else {
            return false;
        }
    }
}
