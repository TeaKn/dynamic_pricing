package com.price.ui.model.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.ArrayList;
import java.util.List;

public class Forecast {

    @JsonProperty("60minutes")
    private Object sixtyMin;

    @JsonProperty("day")
    private List<Day> day;

    @JsonProperty("hour")
    private Object hour;


    @JsonGetter("60minutes")
    public Object getSixtyMin() {
        return sixtyMin;
    }

    @JsonSetter("60minutes")
    public void setSixtyMin(Object sixtyMin) {
        this.sixtyMin = sixtyMin;
    }

    @JsonGetter("day")
    public List<Day> getDay() {
        return day;
    }

    @JsonSetter("day")
    public void setDay(List<Day> day) {
        this.day = day;
    }

    @JsonGetter("hour")
    public Object getHour() {
        return hour;
    }

    @JsonSetter("hour")
    public void setHour(Object hour) {
        this.hour = hour;
    }
}
