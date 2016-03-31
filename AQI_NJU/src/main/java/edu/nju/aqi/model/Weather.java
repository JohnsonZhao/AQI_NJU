package edu.nju.aqi.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import edu.nju.aqi.analysis.helper.KeyProperty;

@Entity
@Table(name="weather")
public class Weather implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -850844824626142088L;
	private int id;
	private String qualityId;
	private String cityName;
	private Timestamp date;
	@KeyProperty
	private String temp;
	@KeyProperty
	private String pressure;
	@KeyProperty
	private String humidity;
	@KeyProperty
	private String windSpeed;
	@KeyProperty
	private String clouds;
	private String rain;
	private String type;
	
	@Id
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getQualityId() {
		return qualityId;
	}
	public void setQualityId(String qualityId) {
		this.qualityId = qualityId;
	}
	
	@Column(columnDefinition="timestamp")
	public Timestamp getDate() {
		return date;
	}
	public void setDate(Timestamp date) {
		this.date = date;
	}
	public String getPressure() {
		return pressure;
	}
	public void setPressure(String pressure) {
		this.pressure = pressure;
	}
	public String getHumidity() {
		return humidity;
	}
	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}
	public String getWindSpeed() {
		return windSpeed;
	}
	public void setWindSpeed(String windSpeed) {
		this.windSpeed = windSpeed;
	}
	public String getClouds() {
		return clouds;
	}
	public void setClouds(String clouds) {
		this.clouds = clouds;
	}
	public String getRain() {
		return rain;
	}
	public void setRain(String rain) {
		this.rain = rain;
	}
	public String getTemp() {
		return temp;
	}
	public void setTemp(String temp) {
		this.temp = temp;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	@Override
	public String toString() {
		return "Weather [id=" + id + ", qualityId=" + qualityId + ", cityName=" + cityName + ", date=" + date
				+ ", temp=" + temp + ", pressure=" + pressure + ", humidity=" + humidity + ", windSpeed=" + windSpeed
				+ ", clouds=" + clouds + ", rain=" + rain + ", type=" + type + "]";
	}

}
