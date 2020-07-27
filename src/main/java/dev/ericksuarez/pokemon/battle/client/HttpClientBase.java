package dev.ericksuarez.pokemon.battle.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.ericksuarez.pokemon.battle.error.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Slf4j
public abstract class HttpClientBase {

    private final HttpClient httpClient;

    private final ObjectMapper objectMapper;

    @Autowired
    public HttpClientBase(HttpClient httpClient, ObjectMapper objectMapper) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
    }

    protected <T> T makeRequest(HttpRequest request, Class<T> tClass) {
        T responseEntity;
        var response = makeRequest(request);
        try {
            responseEntity = objectMapper.readValue(response.body().getBytes(), tClass);
            return responseEntity;
        } catch (IOException e) {
            e.printStackTrace();
            log.error("event=errorMappingResponse response={}", response.body());
            throw new RuntimeException("IO exception");
        }
    }

    protected HttpResponse<String> makeRequest(HttpRequest request) {
        log.info("event=makeRequestInvoked request={}", request);
        try {
            var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() >= 300) {
                log.error("event=errorMakeRequest statusCode={}, body={}", response.statusCode(), response.body());
                switch (response.statusCode()) {
                    case 404:
                        throw new NotFoundException(request.uri().toString());
                    default:
                        throw new RuntimeException("Error request " + response.statusCode());
                }
            } else if(response.statusCode() >= 200) {
                return response;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Error sending request");
    }
}
