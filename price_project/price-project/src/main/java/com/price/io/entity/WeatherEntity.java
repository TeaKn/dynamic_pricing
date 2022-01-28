package com.price.io.entity;

public class WeatherEntity {

    private final String date;
    private Integer temp_high;
    private Integer temp_low;
    private Integer temp_average;
    private Integer wind;
    private Integer precipitation;

    public WeatherEntity(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public Integer getTemp_high() {
        return temp_high;
    }

    public void setTemp_high(Integer temp_high) {
        this.temp_high = temp_high;
    }

    public Integer getTemp_low() {
        return temp_low;
    }

    public void setTemp_low(Integer temp_low) {
        this.temp_low = temp_low;
    }

    public Integer getTemp_average() {
        return temp_average;
    }

    public void setTemp_average(Integer temp_average) {
        this.temp_average = temp_average;
    }

    public Integer getWind() {
        return wind;
    }

    public void setWind(Integer wind) {
        this.wind = wind;
    }

    public Integer getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(Integer precipitation) {
        this.precipitation = precipitation;
    }
}
