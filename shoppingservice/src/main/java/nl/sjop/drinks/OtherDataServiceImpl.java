package nl.sjop.drinks;

import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public class OtherDataServiceImpl implements DataService {

    public List<String> retrieveData() {
        final List<String> myList =
                Arrays.asList("Coca Cola 1.5 liter",
                        "Sisi 2 liter");
        return myList;
    }
}
