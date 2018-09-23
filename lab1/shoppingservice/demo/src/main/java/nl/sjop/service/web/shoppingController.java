package nl.sjop.service.web;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value="shopping")
public class shoppingController {

    private static final List<String> drinks =
            Arrays.asList("1. Glenfiddich 18 years old 70 cl",
                    "2. Old Pulteney 17 years Single Malt 70 cl",
                    "3. Aberlour 12 Years Double Cask Matured 70 cl",
                    "4. Laphroaig 10 Years 70CL");

    @RequestMapping(value="drinks", method=RequestMethod.GET)
    public static List<String> retrieveDrinks() {
        return drinks;
    }

    @RequestMapping(value="drink/{id}", method=RequestMethod.GET, produces = "application/json")
    public static StringResponse retrieveDrink(@PathVariable("id") String productId) {
        final StringResponse drinkNotFound = new StringResponse();
        drinkNotFound.setResponse("drink not found");
        try {
            int keuze = Integer.parseInt(productId)-1;
            if (keuze<drinks.size()) {
                final StringResponse stringResponse = new StringResponse();
                stringResponse.setResponse(drinks.get(keuze));
                return stringResponse;
            } else {
                return drinkNotFound;
            }
        } catch (NumberFormatException nfe) {
            return drinkNotFound;
        }
    }
}
