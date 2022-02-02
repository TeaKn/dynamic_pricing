package com.price.shared.dto;

import java.util.Date;

public class ForecastDemand {

    private Date date;
    private Double demand;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getDemand() {
        return demand;
    }

    public void setDemand(Double demand) {
        this.demand = demand;
    }
}
