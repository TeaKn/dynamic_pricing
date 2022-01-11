package com.price.ui.model.response;

import java.util.List;

public class PriceRest {

    private String location;
    private String day;
    private List<WeatherRest> weather;
    private String price;
    private List<String> discounts;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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
