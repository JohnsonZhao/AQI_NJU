package edu.nju.aqi.service;

import java.util.List;

import edu.nju.aqi.model.Weather;

public interface WeatherService {
	public boolean addWeather(Weather weather);
	
	public boolean addWeathers(List<Weather> weathers);
	
	public Weather getCurrentWeather(String cityName);
	
}
