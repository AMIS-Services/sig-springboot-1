package nl.groothandel.service.web;

import nl.groothandel.service.domain.Product;
import nl.groothandel.service.factory.DataServiceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    private DataServiceFactory dataServiceFactory;

    @Value("${repository.name}")
    private String repositoryName;

    @GetMapping
    public List<Product> retrieveProducts() {
        return new ArrayList<>(dataServiceFactory.get(repositoryName).getProducts().values());
    }

    @RequestMapping(value="/{productId}", method=RequestMethod.GET)
    public Product retrieveProduct (@PathVariable("productId") String productId) {
        return dataServiceFactory.get(repositoryName).getProducts().get(productId);
    }

    @RequestMapping(value = "/{productId}", method=RequestMethod.POST)
    //@ResponseBody
    public ResponseEntity<Product> addProduct(@PathVariable("productId") String productId,
                                              @RequestBody Product product,
                                              UriComponentsBuilder builder) {
        if (dataServiceFactory.get(repositoryName).addProduct(productId, product)) {
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
        if (dataServiceFactory.get(repositoryName).updateProduct(productId, product)) {
            final HttpHeaders headers = new HttpHeaders();
            final URI uri = builder.path("drink/{id}").buildAndExpand(productId).toUri();
            headers.setLocation(uri);
            return new ResponseEntity<>(product, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(product, HttpStatus.CONFLICT);
        }
    }
}
