package nl.sjop.service.web;

import nl.sjop.service.domain.Product;
import nl.sjop.service.exception.ShoppingErrorHandler;
import nl.sjop.service.exception.ProductNotFoundException;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value="shopping")
public class ShoppingController {

    private static final String urlProductService = "http://localhost:8081/products";

    @RequestMapping(value="drinks", method=RequestMethod.GET)
    public static List<Product>  retrieveDrinks() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(urlProductService, List.class );
    }

    @GetMapping(value="drink/{id}", produces = "application/json")
    public static Product retrieveDrink(@PathVariable("id") String productId) {

        final String url = "http://localhost:8081/products/{productId}";

        Map<String, String> uriParams = new HashMap<>();
        uriParams.put("productId", productId);
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
        URI uri = builder.buildAndExpand(uriParams).toUri();
        //
        RestTemplate restTemplate = new RestTemplate();
        //return restTemplate.getForObject(uri, Product.class);
        HttpHeaders headers = new HttpHeaders();
        //headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        ResponseEntity<Product> result = restTemplate.exchange(uri, HttpMethod.GET, entity, Product.class);
        if (result.getStatusCode() == HttpStatus.OK && result.getBody() != null) {
           return result.getBody();
        } else {
           throw new ProductNotFoundException("id:"+productId);
        }
    }

    @RequestMapping(value = "drink/{id}", method=RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Product> addDrink(@PathVariable("id") String productId, @RequestBody Product product) {

        final String url = "http://localhost:8081/products/{productId}";

        Map<String, String> uriParams = new HashMap<>();
        uriParams.put("productId", productId);
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
        URI uri = builder.buildAndExpand(uriParams).toUri();
        //
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new ShoppingErrorHandler());
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Product> entity = new HttpEntity<>(product, headers);
        ResponseEntity<Product> result = restTemplate.exchange(uri, HttpMethod.POST, entity, Product.class);
        if (result.getStatusCode() == HttpStatus.CREATED) {
            System.out.println("Product added");
        } else {
            System.out.println("Failure adding product with id: " + productId);
        }
        return result;
    }

    @RequestMapping(value = "drink/{id}", method=RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Product> modifyDrink(@PathVariable("id") String productId, @RequestBody Product product) {

        final String url = "http://localhost:8081/products/{productId}";

        Map<String, String> uriParams = new HashMap<>();
        uriParams.put("productId", productId);
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
        URI uri = builder.buildAndExpand(uriParams).toUri();
        //
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new ShoppingErrorHandler());
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Product> entity = new HttpEntity<>(product, headers);
        ResponseEntity<Product> result = restTemplate.exchange(uri, HttpMethod.PUT, entity, Product.class);
        if (result.getStatusCode() == HttpStatus.OK) {
            System.out.println("Product updated");
        } else {
            System.out.println("Failure updating product with id: " + productId);
        }
        return result;
    }
}
