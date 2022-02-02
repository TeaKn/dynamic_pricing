package com.price.io.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;


@Table("weather")
@Data
public class WeatherEntity {

    @Id
    private Long id;

    @Column
    private String local_date_time;

    @Column
    private Integer TX_C;

    @Column
    private Integer TX_N;

    @Column
    private Integer FF_KMH;

    @Column
    private Integer PROBPCP_PERCENT;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocal_date_time() {
        return local_date_time;
    }

    public void setLocal_date_time(String local_date_time) {
        this.local_date_time = local_date_time;
    }

    public Integer getTX_C() {
        return TX_C;
    }

    public void setTX_C(Integer TX_C) {
        this.TX_C = TX_C;
    }

    public Integer getTX_N() {
        return TX_N;
    }

    public void setTX_N(Integer TX_N) {
        this.TX_N = TX_N;
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
