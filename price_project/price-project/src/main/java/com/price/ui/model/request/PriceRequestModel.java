package com.price.ui.model.request;

import com.price.shared.dto.DemandDTO;
import com.price.shared.dto.VenueDTO;
import com.price.shared.dto.WeatherDTO;

import java.util.ArrayList;
import java.util.List;

public class PriceRequestModel {

    // when the service layer will be implemented, remove the current implemented variables, i return only the prices not demand

    private ArrayList<VenueDTO> venue;
    private List<WeatherDTO> dayWeather;
    private String start_time;
    private String end_time;
    private ArrayList<DemandDTO> demand;

}
