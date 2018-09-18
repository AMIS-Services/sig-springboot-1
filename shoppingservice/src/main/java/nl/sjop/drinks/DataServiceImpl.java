package nl.sjop.drinks;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Repository
@Primary
public class DataServiceImpl implements DataService {

    public List<String> retrieveDataOld() {
        final List<String> myList =
                Arrays.asList("Glenfiddich 18 Years 70 cl",
                        "Old Pulteney 17 years Single Malt 70 cl");
        return myList;
    }


    private List<String> parseJsonResponse (String jsonString) {
        ObjectMapper objectMapper = new ObjectMapper();
        TypeFactory typeFactory = objectMapper.getTypeFactory();
        try {
            return objectMapper.readValue(jsonString, typeFactory.constructCollectionType(List.class, String.class));
        } catch (IOException e) {
            //e.printStackTrace();
            return Collections.emptyList();
        }
    }


    public List<String> retrieveData() {
        RestTemplate restTemplate = new RestTemplate();
        String productServiceUrl
                = "http://localhost:8081/products";
        ResponseEntity<String> response
                = restTemplate.getForEntity(productServiceUrl , String.class);
                //= restTemplate.getForEntity(productServiceUrl + "/1", String.class);
        if (HttpStatus.OK.equals(response.getStatusCode())) {
            //System.out.println(response.getBody());
            return parseJsonResponse(response.getBody());
        } else {
            return Collections.emptyList();
        }
    }
}
