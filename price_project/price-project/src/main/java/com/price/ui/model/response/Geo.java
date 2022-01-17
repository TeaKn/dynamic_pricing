package com.price.ui.model.response;

import java.util.List;

public class Geo {

    private String id;
    private Double lat;
    private Double lon;
    private String station_id;
    private String timezone;
    private String default_name;
    private String alarm_region_id;
    private String alarm_region_name;
    private String district;
    private List<String> geolocation_names;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public String getStation_id() {
        return station_id;
    }

    public void setStation_id(String station_id) {
        this.station_id = station_id;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getDefault_name() {
        return default_name;
    }

    public void setDefault_name(String default_name) {
        this.default_name = default_name;
    }

    public String getAlarm_region_id() {
        return alarm_region_id;
    }

    public void setAlarm_region_id(String alarm_region_id) {
        this.alarm_region_id = alarm_region_id;
    }

    public String getAlarm_region_name() {
        return alarm_region_name;
    }

    public void setAlarm_region_name(String alarm_region_name) {
        this.alarm_region_name = alarm_region_name;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public List<String> getGeolocation_names() {
        return geolocation_names;
    }

    public void setGeolocation_names(List<String> geolocation_names) {
        this.geolocation_names = geolocation_names;
    }
}
