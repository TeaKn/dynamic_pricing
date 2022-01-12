package com.price.ui.controller;

import com.price.ui.model.response.PriceRest;
import com.price.ui.model.response.WeatherRest;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.*;

import static java.nio.charset.StandardCharsets.UTF_8;

@RestController
@RequestMapping("/dynamic-price")
public class PriceController {

    @PostMapping(path = "/getAccessToken")
    public Mono<String> getAccessToken() {

        // TODO: GET ACCESS TOKEN
        // get access token

        // create and configure WebClient

        WebClient accessTokenClient = WebClient.builder()
                .baseUrl("https://api.srgssr.ch/oauth/v1/accesstoken?grant_type=client_credentials")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .defaultHeader(HttpHeaders.CONTENT_LENGTH, "0")
                .defaultHeader("Cache-Control", "no-cache")
                //.defaultHeader("Postman-Token", "24264e32-2de0-f1e3-f3f8-eab014bb6d76")
                .defaultHeader("Authorization", "Basic " + Base64Utils
                        .encodeToString(("vhCOppbnnNPlnlonuRqXqIXOzWs9SZKE" + ":" + "R79BNPhRB6IGNN07").getBytes(UTF_8)))
                .build();

        // sending request
        WebClient.UriSpec<WebClient.RequestBodySpec> uriSpec = accessTokenClient.post();

        // preparing the request - define the url
        WebClient.RequestBodySpec bodySpec = uriSpec.uri("");

        // preparing the request - define the body
        WebClient.RequestHeadersSpec<?> headersSpec = bodySpec.bodyValue("");

        // preparing a request - define the headers
        WebClient.ResponseSpec responseSpec = headersSpec
                .header(HttpHeaders.CONTENT_LENGTH, "0")

                .accept(MediaType.ALL, MediaType.ALL)
                .retrieve();


        // handling response
        Mono<String> response = headersSpec.retrieve().bodyToMono(String.class);

        // TODO: CALL GEOLOCATION API
        // call geolocation api



        // TODO: CALL WEATHER API
        return response;
    }

    @GetMapping(path = "/specificCase", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public PriceRest getInfoSpecific() throws IOException {

        // TODO: CURRENT DAY

        String uri = "https://www.srf.ch/meteo/wetter/Airolo/46.5292,8.6081";

        // convert html into a Document
        Document doc = Jsoup.connect(uri).get();

        try {
            Document doc404 = Jsoup.connect("https://www.srf.ch/meteo/wetter/Airolo/46.5292,8.6081").get();
        } catch (HttpStatusException ex) {
            //...
        }

        // navigate and find what i am looking for
        String wind = doc.select("section.weather-details:nth-child(1) > ul:nth-child(2) > li:nth-child(3)").text();
        String temp_high = doc.select("button.active > span:nth-child(4) > span:nth-child(1)").text();
        String temp_low = doc.select("button.active > span:nth-child(5) > span:nth-child(1)").text();
        String day = doc.select("section.weather-details:nth-child(1) > div:nth-child(1) > span:nth-child(2)").text();

        PriceRest returnValue = new PriceRest();
        returnValue.setDay(day);
        returnValue.setLocation("Airolo");

        WeatherRest weather = new WeatherRest();
        weather.setDay(day);
        weather.setTemp_high(temp_high);
        weather.setTemp_low(temp_low);
        weather.setWind(wind);

        List<WeatherRest> weatherList = new ArrayList<>();
        weatherList.add(weather);

        returnValue.setWeather(weatherList);

        return returnValue;
    }

}
