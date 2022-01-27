package com.price.ui.model.request;

public class DayWeather {

    private String day;
    private Integer temp_high;
    private Integer temp_low;
    private Integer precipitation;
    private Integer wind;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
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

    public Integer getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(Integer precipitation) {
        this.precipitation = precipitation;
    }

    public Integer getWind() {
        return wind;
    }

    public void setWind(Integer wind) {
        this.wind = wind;
    }
}
