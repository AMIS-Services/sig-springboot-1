package nl.sjop.service.web;

import nl.sjop.service.domain.Currency;
import nl.sjop.service.domain.Product;
import nl.sjop.service.exception.ProductNotFoundException;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
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

    @RequestMapping(value="drink/{id}", method=RequestMethod.GET, produces = "application/json")
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
        Product product = result.getBody();
        if (!(result.getStatusCode() == HttpStatus.OK && result.getBody() != null)) {
           throw new ProductNotFoundException("id:"+productId);
        }

        final String currencyUrl = "https://api.exchangeratesapi.io/latest";
        restTemplate = new RestTemplate();
        HttpHeaders currencyHeaders = new HttpHeaders();
        headers.add("base", "EUR");
        HttpEntity<String> currencyEntity = new HttpEntity<>("parameters", currencyHeaders);
        ResponseEntity<Currency> currencyResult = restTemplate.exchange(currencyUrl, HttpMethod.GET, currencyEntity, Currency.class);
        Currency currency = currencyResult.getBody();
        product.setPriceUSD(product.getPrice() * currency.getRates().get("USD"));
        return product;
    }
}
