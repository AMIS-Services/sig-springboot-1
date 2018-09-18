package nl.sjop.drinks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.swing.table.DefaultTableCellRenderer;
import java.util.List;

@Component
public class BusinessServiceAutowiredImpl {

    private DataService dataService;

    @Autowired
    public BusinessServiceAutowiredImpl(
             DataService dataService) {
        this.dataService = dataService;
    }



    public String produkten() {
        final List<String> drinks = dataService.retrieveData();
        final StringBuilder result = new StringBuilder();
        drinks.forEach(s -> result.append(s + "<br>"));
        return result.toString();
    }
}
