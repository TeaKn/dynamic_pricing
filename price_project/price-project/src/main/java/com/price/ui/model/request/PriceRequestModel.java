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

    public ArrayList<VenueDTO> getVenue() {
        return venue;
    }

    public void setVenue(ArrayList<VenueDTO> venue) {
        this.venue = venue;
    }

    public List<WeatherDTO> getDayWeather() {
        return dayWeather;
    }

    public void setDayWeather(List<WeatherDTO> dayWeather) {
        this.dayWeather = dayWeather;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public ArrayList<DemandDTO> getDemand() {
        return demand;
    }

    public void setDemand(ArrayList<DemandDTO> demand) {
        this.demand = demand;
    }
}
