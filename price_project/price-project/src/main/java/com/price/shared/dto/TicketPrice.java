package com.price.shared.dto;

import java.time.LocalDate;

public class TicketPrice {

    private LocalDate date;
    private Double price; // dynamic price
    private Double demandPrice; // arima price prediction
    private long demandNumber;
    private Double windChill; // wind chill in celsius
    private Double weatherInfluence; // effect of weather in percentage
    private Double basePrice;
    private Double weatherPrice; // price after weather influence has been applied, currently to demand price
    private Double priceMax;
    private Double priceMin;

    public Double getDemandPrice() {
        return demandPrice;
    }

    public void setDemandPrice(Double demandPrice) {
        this.demandPrice = demandPrice;
    }

    public Double getWindChill() {
        return windChill;
    }

    public void setWindChill(Double weatherPrice) {
        this.windChill = weatherPrice;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Double basePrice) {
        this.basePrice = basePrice;
    }

    public Double getWeatherInfluence() {
        return weatherInfluence;
    }

    public void setWeatherInfluence(Double weatherInfluence) {
        this.weatherInfluence = weatherInfluence;
    }

    public Double getWeatherPrice() {
        return weatherPrice;
    }

    public void setWeatherPrice(Double weatherPrice) {
        this.weatherPrice = weatherPrice;
    }

    public Double getPriceMax() {
        return priceMax;
    }

    public void setPriceMax(Double priceMax) {
        this.priceMax = priceMax;
    }

    public Double getPriceMin() {
        return priceMin;
    }

    public void setPriceMin(Double priceMin) {
        this.priceMin = priceMin;
    }

    public long getDemandNumber() {
        return demandNumber;
    }

    public void setDemandNumber(long demandNumber) {
        this.demandNumber = demandNumber;
    }
}
