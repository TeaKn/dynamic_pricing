package com.price.shared.dto;

import java.time.LocalDate;

public class ForecastDemand {

    private LocalDate date;
    private Double demand;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getDemand() {
        return demand;
    }

    public void setDemand(Double demand) {
        this.demand = demand;
    }
}
