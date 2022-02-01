package com.price.service.impl;

import com.price.io.repositories.WeatherRepository;
import com.price.service.WeatherService;
import com.price.shared.dto.WeatherDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeatherServiceImpl implements WeatherService {

    @Autowired
    WeatherRepository weatherRepository;


    @Override
    public WeatherDTO saveWeatherPredictionWeek(WeatherDTO weather) {

        // week weather prog is persisted into db once per day
        // zadeva je reactive, js bi rada d se vremenska prognoza sama not shranjuje ko se zgodi sprememba pri ssrch


        return null;
    }
}
