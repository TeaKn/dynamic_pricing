package com.price.ui.model.response;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

public class Day {

    public Day() {
    }

    @JsonProperty("local_date_time")
    private String local_date_time;

    @JsonProperty("TX_C")
    private Integer TX_C;

    @JsonProperty("TN_C")
    private Integer TN_C;

    @JsonProperty("PROBPCP_PERCENT")
    private Integer PROBPCP_PERCENT;

    @JsonProperty("RRR_MM")
    private Integer RRR_MM;

    @JsonProperty("FF_KMH")
    private Integer FF_KMH;

    @JsonProperty("FX_KMH")
    private Integer FX_KMH;

    @JsonProperty("DD_DEG")
    private Integer DD_DEG;

    @JsonProperty("SUNSET")
    private Integer SUNSET;

    @JsonProperty("SUNRISE")
    private Integer SUNRISE;

    @JsonProperty("SUN_H")
    private Integer SUN_H;

    @JsonProperty("SYMBOL_CODE")
    private Integer SYMBOL_CODE;

    @JsonProperty("type")
    private String type;

    @JsonProperty("min_color")
    private Object min_color;

    @JsonProperty("max_color")
    private Object max_color;

    @JsonGetter("local_date_time")
    public String getLocal_date_time() {
        return local_date_time;
    }

    @JsonSetter("local_date_time")
    public void setLocal_date_time(String local_date_time) {
        this.local_date_time = local_date_time;
    }

    @JsonGetter("TX_C")
    public Integer getTX_C() {
        return TX_C;
    }


    @JsonSetter("TX_C")
    public void setTX_C(Integer TX_C) {
        this.TX_C = TX_C;
    }

    @JsonGetter("TN_C")
    public Integer getTN_C() {
        return TN_C;
    }

    @JsonSetter("TN_C")
    public void setTN_C(Integer TN_C) {
        this.TN_C = TN_C;
    }

    @JsonGetter("PROBPCP_PERCENT")
    public Integer getPROBPCP_PERCENT() {
        return PROBPCP_PERCENT;
    }

    @JsonGetter("PROBPCP_PERCENT")
    public void setPROBPCP_PERCENT(Integer PROBPCP_PERCENT) {
        this.PROBPCP_PERCENT = PROBPCP_PERCENT;
    }

    @JsonGetter("RRR_MM")
    public Integer getRRR_MM() {
        return RRR_MM;
    }

    @JsonSetter("RRR_MM")
    public void setRRR_MM(Integer RRR_MM) {
        this.RRR_MM = RRR_MM;
    }
    @JsonGetter("FF_KMH")
    public Integer getFF_KMH() {
        return FF_KMH;
    }

    @JsonSetter("FF_KMH")
    public void setFF_KMH(Integer FF_KMH) {
        this.FF_KMH = FF_KMH;
    }

    @JsonGetter("FX_KMH")
    public Integer getFX_KMH() {
        return FX_KMH;
    }

    @JsonSetter("FX_KMH")
    public void setFX_KMH(Integer FX_KMH) {
        this.FX_KMH = FX_KMH;
    }

    @JsonGetter("DD_DEG")
    public Integer getDD_DEG() {
        return DD_DEG;
    }

    @JsonSetter("DD_DEG")
    public void setDD_DEG(Integer DD_DEG) {
        this.DD_DEG = DD_DEG;
    }

    @JsonGetter("SUNSET")
    public Integer getSUNSET() {
        return SUNSET;
    }

    @JsonSetter("SUNSET")
    public void setSUNSET(Integer SUNSET) {
        this.SUNSET = SUNSET;
    }

    @JsonGetter("SUNRISE")
    public Integer getSUNRISE() {
        return SUNRISE;
    }

    @JsonSetter("SUNRISE")
    public void setSUNRISE(Integer SUNRISE) {
        this.SUNRISE = SUNRISE;
    }

    @JsonGetter("SUN_H")
    public Integer getSUN_H() {
        return SUN_H;
    }

    @JsonSetter("SUN_H")
    public void setSUN_H(Integer SUN_H) {
        this.SUN_H = SUN_H;
    }

    @JsonGetter("SYMBOL_CODE")
    public Integer getSYMBOL_CODE() {
        return SYMBOL_CODE;
    }

    @JsonSetter("SYMBOL_CODE")
    public void setSYMBOL_CODE(Integer SYMBOL_CODE) {
        this.SYMBOL_CODE = SYMBOL_CODE;
    }

    @JsonGetter("type")
    public String getType() {
        return type;
    }

    @JsonSetter("type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonGetter("min_color")
    public Object getMin_color() {
        return min_color;
    }

    @JsonSetter("min_color")
    public void setMin_color(Object min_color) {
        this.min_color = min_color;
    }

    @JsonGetter("max_color")
    public Object getMax_color() {
        return max_color;
    }

    @JsonSetter("max_color")
    public void setMax_color(Object max_color) {
        this.max_color = max_color;
    }
}
