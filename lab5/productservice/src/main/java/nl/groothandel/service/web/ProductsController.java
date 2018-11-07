package nl.groothandel.service.web;

import nl.groothandel.service.domain.Product;
import nl.groothandel.service.domain.ProductRepositoryDb;
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
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "products")
public class ProductsController {

  @Autowired
  private ProductRepositoryDb productRepository;

  @GetMapping
  @Cacheable(value="products")
  public List<Product> retrieveProducts() {
    try {
      long time = 3000L;
      Thread.sleep(time);
    } catch (InterruptedException e) {
      throw new IllegalStateException(e);
    }

    return (List<Product>) productRepository.findAll();
  }

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

  @PostMapping
  @Caching(evict = {
    @CacheEvict(value="products", allEntries=true)
  }
         ,
  put = {
    @CachePut(value="product", key="#product.productId")
  }
         )

  // @ResponseBody
  public ResponseEntity<Product> addProduct(@RequestBody Product product,
      UriComponentsBuilder builder) {

//    productRepository.save(product);
    productRepository.insert(product);
    final HttpHeaders headers = new HttpHeaders();
    final URI uri = builder.path("drink/{id}").buildAndExpand(product.getId()).toUri();
    headers.setLocation(uri);
    return new ResponseEntity<>(product, headers, HttpStatus.CREATED);
    // return new ResponseEntity<>(product, HttpStatus.CONFLICT);

  }

  @PutMapping(value = "/{productId}")
  @Caching(evict = {
      @CacheEvict(value="products", allEntries=true)
    }
    ,
    put = {
      @CachePut(value="product", key="#product.productId")
    }
  )
  // @ResponseBody
  public ResponseEntity<Product> modifyProduct( @PathVariable("productId") String productId
//                                                @PathVariable("id") Long id
                                              , @RequestBody Product product
                                              , UriComponentsBuilder builder )
  {
  /*  Optional<Product> test = productRepository.findByProductId(productId);
    if (test.isPresent()){
      product.setId(test.get().getId());
      productRepository.save(product);
      final HttpHeaders headers = new HttpHeaders();
      final URI uri = builder.path("drink/{id}").buildAndExpand(productId).toUri();
      headers.setLocation(uri);

      return new ResponseEntity<>(product, headers, HttpStatus.OK);
    }
    else{
      return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.CONFLICT);
    }*/
//    product.setId(id);
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
}
