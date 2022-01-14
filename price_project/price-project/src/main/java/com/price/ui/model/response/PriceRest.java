package com.price.ui.model.response;

import java.util.List;
import java.util.Map;

public class PriceRest {

    private Map<String, String> geolocation;
    private String day;
    private List<WeatherRest> weather;
    private String price;
    private List<String> discounts;
    private Map<String, String> forecast;

    public Map<String, String> getForecast() {
        return forecast;
    }

    public void setForecast(Map<String, String> forecast) {
        this.forecast = forecast;
    }

    public Map<String, String> getGeolocation() {
        return geolocation;
    }

    public void setGeolocation(Map<String, String> geolocation) {
        this.geolocation = geolocation;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public List<WeatherRest> getWeather() {
        return weather;
    }

    public void setWeather(List<WeatherRest> weather) {
        this.weather = weather;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public List<String> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(List<String> discounts) {
        this.discounts = discounts;
    }

}
