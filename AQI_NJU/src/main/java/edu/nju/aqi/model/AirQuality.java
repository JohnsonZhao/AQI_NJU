package edu.nju.aqi.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import edu.nju.aqi.analysis.helper.KeyProperty;

@Entity
@Table(name = "air_quality")
public class AirQuality {

    private String id;
    private String city_name;
    private String date;
    @KeyProperty
    private String aqi;
    private String index_type;
    private String prim_pollu;
    @KeyProperty
    private String pm25;
    @KeyProperty
    private String pm10;
    @KeyProperty
    private String co;
    @KeyProperty
    private String no2;
    @KeyProperty
    private String o3;
    @KeyProperty
    private String so2;

    @Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAqi() {
        return aqi;
    }

    public void setAqi(String aqi) {
        this.aqi = aqi;
    }

    public String getIndex_type() {
        return index_type;
    }

    public void setIndex_type(String index_type) {
        this.index_type = index_type;
    }

    public String getPrim_pollu() {
        return prim_pollu;
    }

    public void setPrim_pollu(String prim_pollu) {
        this.prim_pollu = prim_pollu;
    }

    public String getPm25() {
        return pm25;
    }

    public void setPm25(String pm25) {
        this.pm25 = pm25;
    }

    public String getPm10() {
        return pm10;
    }

    public void setPm10(String pm10) {
        this.pm10 = pm10;
    }

    public String getCo() {
        return co;
    }

    public void setCo(String co) {
        this.co = co;
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

    public String getSo2() {
        return so2;
    }

    public void setSo2(String so2) {
        this.so2 = so2;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

	@Override
	public String toString() {
		return "AirQuality [id=" + id + ", city_name=" + city_name + ", date=" + date + ", aqi=" + aqi + ", index_type="
				+ index_type + ", prim_pollu=" + prim_pollu + ", pm25=" + pm25 + ", pm10=" + pm10 + ", co=" + co
				+ ", no2=" + no2 + ", o3=" + o3 + ", so2=" + so2 + "]";
	}
}
