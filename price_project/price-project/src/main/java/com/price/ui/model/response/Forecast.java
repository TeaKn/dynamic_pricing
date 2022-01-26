package com.price.ui.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Forecast {

    @JsonProperty("60minutes")
    private Object sixtyMin;

    private List<Day> day;
    private List<String> hour;

}
