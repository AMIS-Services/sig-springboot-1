package nl.sjop.drinks;

import java.util.List;

public class BusinessServiceImpl implements BusinessService {

    public String produkten() {
        final DataService dataService = new OtherDataServiceImpl();
        final List<String> drinks = dataService.retrieveData();
        final StringBuilder result = new StringBuilder();
        drinks.forEach(s -> result.append(s + "<br>"));
        return result.toString();
    }
}

