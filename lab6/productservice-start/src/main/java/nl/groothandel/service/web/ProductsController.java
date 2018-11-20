package nl.groothandel.service.web;

import nl.groothandel.service.domain.Product;
import nl.groothandel.service.domain.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(value="products")
public class ProductsController {
    @Autowired
    ProductRepository productRepository;

    @GetMapping
    public List<Product> retrieveProducts() {
        return new ArrayList<>(productRepository.findAll());
    }

    @RequestMapping(value="/{productId}", method=RequestMethod.GET)
    public Product retrieveProduct (@PathVariable("productId") String productId) {
        return productRepository.findByProductId(productId).get();
    }

    @RequestMapping(method=RequestMethod.POST)
    //@ResponseBody
    public ResponseEntity<Product> addProduct(@RequestBody Product product,
                                              UriComponentsBuilder builder) {
        try {
            productRepository.insert(product);
            final HttpHeaders headers = new HttpHeaders();
            final URI uri = builder.path("drink/{id}").buildAndExpand(product.getId()).toUri();
            headers.setLocation(uri);
            return new ResponseEntity<>(product, headers, HttpStatus.CREATED);
        }
        catch(Exception e){
            return new ResponseEntity<>(product, HttpStatus.CONFLICT);
        }
    }
	
    @RequestMapping(value = "/{productId}", method=RequestMethod.PUT)
    //@ResponseBody
    public ResponseEntity<Product> modifyProduct(@PathVariable("productId") String productId,
                                                 @RequestBody Product product,
                                                 UriComponentsBuilder builder) {
        try {
            productRepository.update(product);
            final HttpHeaders headers = new HttpHeaders();
            final URI uri = builder.path("drink/{id}").buildAndExpand(productId).toUri();
            headers.setLocation(uri);
            return new ResponseEntity<>(product, headers, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value="/byBrand/{brand}", method=RequestMethod.GET)
    public List<Product> retrieveProductByBrand (@PathVariable("brand") String brand, @RequestParam Map<String, String> queryParameters) {
        Double priceFloor = queryParameters.get("priceFloor") == null? null : new Double(queryParameters.get("priceFloor"));
        Double priceCeiling = queryParameters.get("priceCeiling") == null? null : new Double(queryParameters.get("priceCeiling"));
        return productRepository.findByBrandIgnoreCaseAndPriceBetween(brand,priceFloor,priceCeiling);    }

    @RequestMapping(value="byPrice", method=RequestMethod.GET)
    public List<Product> customQuery(@RequestParam Map<String, String> queryParameters)  {
        Double priceFloor = queryParameters.get("priceFloor") == null? null : new Double(queryParameters.get("priceFloor"));
        Double priceCeiling = queryParameters.get("priceCeiling") == null? null : new Double(queryParameters.get("priceCeiling"));
        return productRepository.customQuery(priceFloor,priceCeiling);
    }

    @RequestMapping(value="byPriceNative", method=RequestMethod.GET)
    public List<Product> nativeQuery(@RequestParam Map<String, String> queryParameters)  {
        Double priceFloor = queryParameters.get("priceFloor") == null? null : new Double(queryParameters.get("priceFloor"));
        Double priceCeiling = queryParameters.get("priceCeiling") == null? null : new Double(queryParameters.get("priceCeiling"));
        return productRepository.nativeQuery(priceFloor,priceCeiling);
    }
}
