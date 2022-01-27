package com.price.shared.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PriceDTO implements Serializable {

    private ArrayList<VenueDTO> venueDTO;
    private List<WeatherDTO> weatherDTO;
    private ArrayList<DemandDTO> demandDTO;

    public ArrayList<VenueDTO> getVenueDTO() {
        return venueDTO;
    }

    public void setVenueDTO(ArrayList<VenueDTO> venueDTO) {
        this.venueDTO = venueDTO;
    }

    public List<WeatherDTO> getWeatherDTO() {
        return weatherDTO;
    }

    public void setWeatherDTO(List<WeatherDTO> weatherDTO) {
        this.weatherDTO = weatherDTO;
    }

    public ArrayList<DemandDTO> getDemandDTO() {
        return demandDTO;
    }

    public void setDemandDTO(ArrayList<DemandDTO> demandDTO) {
        this.demandDTO = demandDTO;
    }
}
