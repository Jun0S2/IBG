package com.ssafy.IBG.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssafy.IBG.api.game.GameListResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RESTAPIService {

    private final String BASE_URL = "http://django:7776/ibg/api/recommend/predict";
    public List<Integer> requestGETAPI(String url, Integer pathVariable) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();

        // Header set
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);


        HttpEntity<String> response = restTemplate.getForEntity(BASE_URL+url+"/"+pathVariable, String.class);
        return responseParsing(response);
    }

    public List<Integer> requestGETAPI(String url) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();

        // Header set
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> response = restTemplate.getForEntity(BASE_URL+url, String.class);
        return responseParsing(response);
    }

    private List<Integer> responseParsing(HttpEntity<String> response) {
        String data = response.getBody();
        if(data == null)
            return null;

        data = data.substring(1, data.length()-1);
        String[] game_no_list = data.split(",");
        List<Integer> list = Arrays.stream(game_no_list).map(str -> Integer.parseInt(str.strip())).collect(Collectors.toList());

        return list;
    }
}
