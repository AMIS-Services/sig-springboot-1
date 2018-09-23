package nl.groothandel.service.web;

import nl.groothandel.service.domain.Product;
import nl.groothandel.service.domain.ProductRepositoryImpl;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value="products")
public class ProductsController {

    ProductRepositoryImpl productRepository = new ProductRepositoryImpl();

    @GetMapping
    public List<Product> retrieveProducts() {
        return new ArrayList<>(productRepository.getProducts().values());
    }

    @RequestMapping(value="/{productId}", method=RequestMethod.GET)
    public Product retrieveProduct (@PathVariable("productId") String productId) {
        return productRepository.getProducts().get(productId);
    }


}
