package com.price.ui.controller;

import com.price.ui.model.response.PriceRest;
import com.price.ui.model.response.WeatherRest;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/dynamic-price")
public class PriceController {

    @GetMapping(path = "{location}/{day}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public PriceRest getInfoForDay(@PathVariable String location, @PathVariable String day) {

        PriceRest returnValue = new PriceRest();

        return returnValue;
    }

    //@GetMapping(path = "{location}/")

    @GetMapping(path = "/weathercase")
    private PriceRest getWeatherSpecific() throws IOException {
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
