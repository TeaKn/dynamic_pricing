package com.price.ui.model.response;

public class WeatherRest {

    private String day;
    private String temp_high;
    private String temp_low;
    private String wind;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTemp_high() {
        return temp_high;
    }

    public void setTemp_high(String temp_high) {
        this.temp_high = temp_high;
    }

    public String getTemp_low() {
        return temp_low;
    }

    public void setTemp_low(String temp_low) {
        this.temp_low = temp_low;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }
}
