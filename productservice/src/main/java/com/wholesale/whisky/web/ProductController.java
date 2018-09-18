package com.wholesale.whisky.web;

import com.wholesale.whisky.model.Product;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final List<String> myList =
            Arrays.asList("1. Glenfiddich 18 Years 70 cl",
                    "2. Old Pulteney 17 years Single Malt 70 cl");
    private Map<String, Product> products;
    {
        Map<String, Product> map = new HashMap<>();
        map.put("GLEN", new Product("GLEN", "Glenfiddich 18 Years", "Whisky", "Glenfiddich", "70cl", 42, 39.95));
        map.put("PULT", new Product("PULT", "Old Pulteney 17 years Single Malt", "Whisky", "Old Pulteney", "70cl", 41, 59.95));
        this.products = map;
    }

    @RequestMapping(method=RequestMethod.GET)
    public List<Product> retrieveProducts () {
          return new ArrayList<>(products.values());
    }

    @RequestMapping(value="/{productId}", method=RequestMethod.GET)
    public Product retrieveProduct (@PathVariable("productId") String productId) {
        if (products.containsKey(productId)) {
            return products.get(productId);
        } else {
            return null;
        }
    }

    @RequestMapping(value="/{productId}", method=RequestMethod.PUT)
    public void putProduct (@PathVariable("productId") String productId, @RequestBody Product product) {
        products.put(productId, product);
    }
}
