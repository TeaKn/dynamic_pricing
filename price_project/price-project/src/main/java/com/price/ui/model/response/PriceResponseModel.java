package com.price.ui.model.response;

import com.price.shared.dto.DemandDTO;
import com.price.shared.dto.VenueDTO;
import com.price.shared.dto.WeatherDTO;

import java.util.ArrayList;
import java.util.List;

public class PriceResponseModel {

    // when the service layer will be implemented, remove the current implemented variables, i return only the prices not demand

    private Double dynamic_price;

    public Double getDynamic_price() {
        return dynamic_price;
    }

    public void setDynamic_price(Double dynamic_price) {
        this.dynamic_price = dynamic_price;
    }
}
