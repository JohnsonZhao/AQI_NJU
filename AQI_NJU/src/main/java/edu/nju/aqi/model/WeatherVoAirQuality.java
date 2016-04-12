package edu.nju.aqi.model;

import edu.nju.aqi.analysis.helper.KeyProperty;

public class WeatherVoAirQuality {
	/**
	 * 公有属性
	 */
	public String cityName;
	public String date;
	
	/**
	 * weather属性
	 */
	@KeyProperty
	public String temp;
	@KeyProperty
	public String pressure;
	public String humidity;
	public String clouds;
	public String windSpeed;
	/**
	 * air quality属性
	 */
	@KeyProperty
	public String aqi;
	public String pm25;
	public String pm10;
	public String co;
	public String no2;
	public String o3;
	public String so2;
	@Override
	public String toString() {
		return "WeatherVoAirQuality [cityName=" + cityName + ", date=" + date + ", temp=" + temp + ", pressure="
				+ pressure + ", humidity=" + humidity + ", clouds=" + clouds + ", windSpeed=" + windSpeed + ", aqi=" + aqi
				+ ", pm25=" + pm25 + ", pm10=" + pm10 + ", co=" + co + ", no2=" + no2 + ", o3=" + o3 + ", so2=" + so2
				+ "]";
	}
	
}
