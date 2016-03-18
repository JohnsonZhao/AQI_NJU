package edu.nju.aqi.bo;

import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by lulei on 16/3/17.
 */
@Component
public class AirQualityBo {
    private String id;
    // 城市坐标
    private String coordinates;
    // 城市名
    private String cityName;
    // 获取的数据时间
    private String date;
    // AQI指数
    private String aqi;
    // AQI指数顺序
    private int order;
    // 空气质量级别
    private String indexType;
    // 首要污染物
    private String primPollu;
    // 污染物含量
    private String pm25;
    private String pm10;
    private String co;
    private String no2;
    private String o3;
    private String so2;

    public String getAqi() {
        return aqi;
    }

    public void setAqi(String aqi) {
        this.aqi = aqi;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCo() {
        return co;
    }

    public void setCo(String co) {
        this.co = co;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIndexType() {
        return indexType;
    }

    public void setIndexType(String indexType) {
        this.indexType = indexType;
    }


    public String getNo2() {
        return no2;
    }

    public void setNo2(String no2) {
        this.no2 = no2;
    }

    public String getO3() {
        return o3;
    }

    public void setO3(String o3) {
        this.o3 = o3;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getPm10() {
        return pm10;
    }

    public void setPm10(String pm10) {
        this.pm10 = pm10;
    }

    public String getPm25() {
        return pm25;
    }

    public void setPm25(String pm25) {
        this.pm25 = pm25;
    }

    public String getPrimPollu() {
        return primPollu;
    }

    public void setPrimPollu(String primPollu) {
        this.primPollu = primPollu;
    }

    public String getSo2() {
        return so2;
    }

    public void setSo2(String so2) {
        this.so2 = so2;
    }
}
