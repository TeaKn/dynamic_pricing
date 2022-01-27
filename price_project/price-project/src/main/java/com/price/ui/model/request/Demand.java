package com.price.ui.model.request;

public class Demand {

    private Integer demand_previous_day;
    private Integer demand_previous_week_same_day;
    private Integer arima_prediction;

    public Integer getDemand_previous_day() {
        return demand_previous_day;
    }

    public void setDemand_previous_day(Integer demand_previous_day) {
        this.demand_previous_day = demand_previous_day;
    }

    public Integer getDemand_previous_week_same_day() {
        return demand_previous_week_same_day;
    }

    public void setDemand_previous_week_same_day(Integer demand_previous_week_same_day) {
        this.demand_previous_week_same_day = demand_previous_week_same_day;
    }

    public Integer getArima_prediction() {
        return arima_prediction;
    }

    public void setArima_prediction(Integer arima_prediction) {
        this.arima_prediction = arima_prediction;
    }
}
