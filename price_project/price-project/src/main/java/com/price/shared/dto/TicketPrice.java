package com.price.shared.dto;

import java.util.Date;

public class TicketPrice {

    private Date date;
    private Double price;
    private Double demandPrice;
    private Double weatherPrice;

    public Double getDemandPrice() {
        return demandPrice;
    }

    public void setDemandPrice(Double demandPrice) {
        this.demandPrice = demandPrice;
    }

    public Double getWeatherPrice() {
        return weatherPrice;
    }

    public void setWeatherPrice(Double weatherPrice) {
        this.weatherPrice = weatherPrice;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
