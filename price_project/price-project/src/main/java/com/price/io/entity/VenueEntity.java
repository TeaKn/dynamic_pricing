package com.price.io.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import reactor.util.annotation.NonNull;

@Table("venues")
@Data
public class VenueEntity {

    // use this model class to persist to the database

    //@Id
    //private Long id; // autoincremented id

    @Id
    private String name;

    @Column
    private Double price_max;

    @Column
    private Double price_min;

    @Column
    private Double adult_base_price;

    public String getId() {
        return name;
    }

    public void setId(String id) {
        this.name = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
