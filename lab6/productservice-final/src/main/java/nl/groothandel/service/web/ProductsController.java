package nl.groothandel.service.web;

import nl.groothandel.service.domain.Product;
import nl.groothandel.service.domain.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
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
    @Cacheable(value="products")
    public List<Product> retrieveProducts() {
        try {
            long time = 3000L;
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
        return new ArrayList<>(productRepository.findAll());
    }

/*    @RequestMapping(value="/{productId}", method=RequestMethod.GET)
    @Cacheable(value="product", key="#productId")
    public Product retrieveProduct (@PathVariable("productId") String productId) {
        try {
            long time = 3000L;
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
        Optional<Product> product = productRepository.findByProductId(productId);
        return new ResponseEntity<>(product.get(),new HttpHeaders(), HttpStatus.OK) ;
//        return productRepository.findByProductId(productId).get();
    }*/

    @RequestMapping(value = "/{productId}", method = RequestMethod.GET)
    @Cacheable(value="product", key="#productId")
    public ResponseEntity<Product> retrieveProduct(@PathVariable("productId") String productId) {

        try {
            long time = 3000L;
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
        Optional<Product> product = productRepository.findByProductId(productId);
        if (product.isPresent()){
            return new ResponseEntity<>(product.get(),new HttpHeaders(), HttpStatus.OK) ;
        }
        else{
            return new ResponseEntity<>(null,new HttpHeaders(), HttpStatus.NOT_FOUND) ;
        }
    }

    @RequestMapping(method=RequestMethod.POST)
    @Caching(evict = {
            @CacheEvict(value="products", allEntries=true)
    }
            ,
            put = {
                    @CachePut(value="product", key="#product.productId")
            }
    )
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
    @Caching(evict = {
            @CacheEvict(value="products", allEntries=true)
    }
            ,
            put = {
                    @CachePut(value="product", key="#product.productId")
            }
    )
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
