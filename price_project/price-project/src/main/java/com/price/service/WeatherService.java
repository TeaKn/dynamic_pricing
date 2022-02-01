package com.price.service;

import com.price.shared.dto.WeatherDTO;

public interface WeatherService {

    WeatherDTO saveWeatherPredictionWeek(WeatherDTO weather);

}
