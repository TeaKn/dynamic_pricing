package com.price.io.entity;

public class VenueEntity {

    private final Integer venue_id;
    private Double price_max;
    private Double price_min;
    private Double adult_base_price;

    public VenueEntity(Integer venue_id) {
        this.venue_id = venue_id;
    }

    public Integer getVenue_id() {
        return venue_id;
    }

    public Double getPrice_max() {
        return price_max;
    }

    public void setPrice_max(Double price_max) {
        this.price_max = price_max;
    }

    public Double getPrice_min() {
        return price_min;
    }

    public void setPrice_min(Double price_min) {
        this.price_min = price_min;
    }

    public Double getAdult_base_price() {
        return adult_base_price;
    }

    public void setAdult_base_price(Double adult_base_price) {
        this.adult_base_price = adult_base_price;
    }
}
