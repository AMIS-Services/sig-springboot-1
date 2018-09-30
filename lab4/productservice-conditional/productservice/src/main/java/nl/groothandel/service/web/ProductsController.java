package nl.groothandel.service.web;

import nl.groothandel.service.domain.ProductRepository;
import nl.groothandel.service.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value="products")
public class ProductsController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public List<Product> retrieveProducts() {
        return new ArrayList<>(productRepository.getProducts().values());
    }

    @RequestMapping(value="/{productId}", method=RequestMethod.GET)
    public Product retrieveProduct (@PathVariable("productId") String productId) {
        return productRepository.getProducts().get(productId);
    }

    @RequestMapping(value = "/{productId}", method=RequestMethod.POST)
    //@ResponseBody
    public ResponseEntity<Product> addProduct(@PathVariable("productId") String productId,
                                              @RequestBody Product product,
                                              UriComponentsBuilder builder) {
        if (productRepository.addProduct(productId, product)) {
            final HttpHeaders headers = new HttpHeaders();
            final URI uri = builder.path("drink/{id}").buildAndExpand(productId).toUri();
            headers.setLocation(uri);
            return new ResponseEntity<>(product, headers, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(product, HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = "/{productId}", method=RequestMethod.PUT)
    //@ResponseBody
    public ResponseEntity<Product> modifyProduct(@PathVariable("productId") String productId,
                                                 @RequestBody Product product,
                                                 UriComponentsBuilder builder) {
        if (productRepository.updateProduct(productId, product)) {
            final HttpHeaders headers = new HttpHeaders();
            final URI uri = builder.path("drink/{id}").buildAndExpand(productId).toUri();
            headers.setLocation(uri);
            return new ResponseEntity<>(product, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(product, HttpStatus.CONFLICT);
        }
    }
}
