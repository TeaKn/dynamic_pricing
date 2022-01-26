package com.price.ui.model.response;

import java.util.ArrayList;

public class ForecastFlux {

    private Object geolocation; // ne rabm tok podatkov zto arraylist
    private Forecast forecast; // te podatke rabm, najbolj rabm day [] podatke

    public Object getGeolocation() {
        return geolocation;
    }

    public void setGeolocation(Object geolocation) {
        this.geolocation = geolocation;
    }

    public Forecast getForecast() {
        return forecast;
    }

    public void setForecast(Forecast forecast) {
        this.forecast = forecast;
    }
}
