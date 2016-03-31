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
	@KeyProperty
	public String humidity;
	@KeyProperty
	public String clouds;
	@KeyProperty
	public String rain;
	/**
	 * air quality属性
	 */
	@KeyProperty
	public String aqi;
	@KeyProperty
	public String pm25;
	@KeyProperty
	public String pm10;
	@KeyProperty
	public String co;
	@KeyProperty
	public String no2;
	@KeyProperty
	public String o3;
	@KeyProperty
	public String so2;
	@Override
	public String toString() {
		return "WeatherVoAirQuality [cityName=" + cityName + ", date=" + date + ", temp=" + temp + ", pressure="
				+ pressure + ", humidity=" + humidity + ", clouds=" + clouds + ", rain=" + rain + ", aqi=" + aqi
				+ ", pm25=" + pm25 + ", pm10=" + pm10 + ", co=" + co + ", no2=" + no2 + ", o3=" + o3 + ", so2=" + so2
				+ "]";
	}
	
}
