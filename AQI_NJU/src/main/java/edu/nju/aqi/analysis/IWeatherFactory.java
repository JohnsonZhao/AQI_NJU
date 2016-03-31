package edu.nju.aqi.analysis;

import java.util.List;

import edu.nju.aqi.model.Weather;

public interface IWeatherFactory {
	
	public void setAppID(String appid);
	
	public Weather getCurrentWeather(String cityName);
	
	public List<Weather> getForecastWeather(String cityName);

}
