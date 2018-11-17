package nl.sjop.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

public class ShoppingErrorHandler implements ResponseErrorHandler {

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        throw new IOException("Error in shopping service. Http status " + response.getStatusCode());
    }

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return (response.getStatusCode() != HttpStatus.OK && response.getStatusCode() != HttpStatus.CREATED);
    }

}
