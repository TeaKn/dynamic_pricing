package com.price.io.entity;

import com.price.ui.model.response.Day;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Date;


@Table("weather")
@Data
public class WeatherEntity {

    @Id
    private long id;

    @Column
    private String location;

    @Column
    private Date local_date_time;

    @Column
    private Integer TX_C;

    @Column
    private Integer TN_C;

    @Column
    private Integer FF_KMH;

    @Column
    private Integer PROBPCP_PERCENT;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getLocal_date_time() {
        return local_date_time;
    }

    public void setLocal_date_time(Date local_date_time) {
        this.local_date_time = local_date_time;
    }

    public Integer getTX_C() {
        return TX_C;
    }

    public void setTX_C(Integer TX_C) {
        this.TX_C = TX_C;
    }

    public Integer getTN_C() {
        return TN_C;
    }

    public void setTN_C(Integer TN_C) {
        this.TN_C = TN_C;
    }

    public Integer getFF_KMH() {
        return FF_KMH;
    }

    public void setFF_KMH(Integer FF_KMH) {
        this.FF_KMH = FF_KMH;
    }

    public Integer getPROBPCP_PERCENT() {
        return PROBPCP_PERCENT;
    }

    public void setPROBPCP_PERCENT(Integer PROBPCP_PERCENT) {
        this.PROBPCP_PERCENT = PROBPCP_PERCENT;
    }
}
