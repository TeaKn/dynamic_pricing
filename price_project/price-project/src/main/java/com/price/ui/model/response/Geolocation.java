package com.price.ui.model.response;

import org.springframework.stereotype.Service;

@Service
public class Geolocation {

    private String district;
    private String id;
    private Geo geolocation;
    private String location_id;
    private String type;
    private Integer language;
    private String translation_type;
    private String name;
    private String country;
    private String province;
    private Integer inhabitants;
    private Integer height;
    private Integer ch;

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Geo getGeolocation() {
        return geolocation;
    }

    public void setGeolocation(Geo geolocation) {
        this.geolocation = geolocation;
    }

    public String getLocation_id() {
        return location_id;
    }

    public void setLocation_id(String location_id) {
        this.location_id = location_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getLanguage() {
        return language;
    }

    public void setLanguage(Integer language) {
        this.language = language;
    }

    public String getTranslation_type() {
        return translation_type;
    }

    public void setTranslation_type(String translation_type) {
        this.translation_type = translation_type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public Integer getInhabitants() {
        return inhabitants;
    }

    public void setInhabitants(Integer inhabitants) {
        this.inhabitants = inhabitants;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getCh() {
        return ch;
    }

    public void setCh(Integer ch) {
        this.ch = ch;
    }
}
