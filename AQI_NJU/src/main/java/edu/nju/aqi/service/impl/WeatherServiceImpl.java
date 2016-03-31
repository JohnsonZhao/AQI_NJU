package edu.nju.aqi.service.impl;

import java.util.List;



import edu.nju.aqi.dao.WeatherDao;
import edu.nju.aqi.model.Weather;
import edu.nju.aqi.service.WeatherService;

public class WeatherServiceImpl implements WeatherService {
	
	private WeatherDao weatherDao;

	public void setWeatherDao(WeatherDao weatherDao) {
		this.weatherDao = weatherDao;
	}
	
	public WeatherDao getWeatherDao() {
		return weatherDao;
	}
	
	@Override
	public boolean addWeather(Weather weather) {
		return weatherDao.addWeather(weather);
	}

	@Override
	public boolean addWeathers(List<Weather> weathers) {
		return weatherDao.addWeatherList(weathers);
	}

	@Override
	public Weather getCurrentWeather(String cityName) {
		return weatherDao.getCurrentWeather(cityName);
	}
	
}
