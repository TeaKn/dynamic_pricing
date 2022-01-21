package com.price.ui.model.response;

import java.util.ArrayList;

public class SixtyMin {

    private String local_date_time;
    private Integer TTT_C;
    private Integer TTL_C;
    private Integer TTH_C;
    private Integer PROBPCP_PERCENT;
    private Integer RRR_MM;
    private Integer FF_KMH;
    private Integer FX_KMH;
    private Integer DD_DEG;
    private Integer SYMBOL_CODE;
    private String type;
    private ArrayList<String> cur_color;

    public String getLocal_date_time() {
        return local_date_time;
    }

    public void setLocal_date_time(String local_date_time) {
        this.local_date_time = local_date_time;
    }

    public Integer getTTT_C() {
        return TTT_C;
    }

    public void setTTT_C(Integer TTT_C) {
        this.TTT_C = TTT_C;
    }

    public Integer getTTL_C() {
        return TTL_C;
    }

    public void setTTL_C(Integer TTL_C) {
        this.TTL_C = TTL_C;
    }

    public Integer getTTH_C() {
        return TTH_C;
    }

    public void setTTH_C(Integer TTH_C) {
        this.TTH_C = TTH_C;
    }

    public Integer getPROBPCP_PERCENT() {
        return PROBPCP_PERCENT;
    }

    public void setPROBPCP_PERCENT(Integer PROBPCP_PERCENT) {
        this.PROBPCP_PERCENT = PROBPCP_PERCENT;
    }

    public Integer getRRR_MM() {
        return RRR_MM;
    }

    public void setRRR_MM(Integer RRR_MM) {
        this.RRR_MM = RRR_MM;
    }

    public Integer getFF_KMH() {
        return FF_KMH;
    }

    public void setFF_KMH(Integer FF_KMH) {
        this.FF_KMH = FF_KMH;
    }

    public Integer getFX_KMH() {
        return FX_KMH;
    }

    public void setFX_KMH(Integer FX_KMH) {
        this.FX_KMH = FX_KMH;
    }

    public Integer getDD_DEG() {
        return DD_DEG;
    }

    public void setDD_DEG(Integer DD_DEG) {
        this.DD_DEG = DD_DEG;
    }

    public Integer getSYMBOL_CODE() {
        return SYMBOL_CODE;
    }

    public void setSYMBOL_CODE(Integer SYMBOL_CODE) {
        this.SYMBOL_CODE = SYMBOL_CODE;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<String> getCur_color() {
        return cur_color;
    }

    public void setCur_color(ArrayList<String> cur_color) {
        this.cur_color = cur_color;
    }
}
