package edu.nju.aqi.dao;

import java.util.Date;
import java.util.List;

import edu.nju.aqi.model.Weather;
import edu.nju.aqi.model.WeatherVoAirQuality;

public interface WeatherDao {

	public boolean addWeather(Weather weather);
	
	public boolean addWeatherList(List<Weather> weathers);
	
	/**
	 * @param startTime
	 * @param endTime
	 * @return all cities' historical weather 
	 */
	public List<Weather> getHistoryWeathers(Date startTime, Date endTime);
	
	/**
	 * 
	 * @param cityName
	 * @param startTime yyyy-MM-dd HH:mm:ss
	 * @param endTime yyyy-MM-dd HH:mm:ss
	 * @return specified city's weather
	 */
	public List<Weather> getHistoryWeather(String cityName, String startTime, String endTime);
	
	/**
	 * @return all cities' current weather
	 */
	public List<Weather> getCurrentWeathers();
	
	public Weather getCurrentWeather(String cityName);
	
	/**
	 * 
	 * @param cityName
	 * @return 5 days/3 hour
	 */
	public List<Weather> getForcastWeather(String cityName); 
	
	/**
	 * get weather and airQuality data of specify city 
	 * @param cityName
	 * @return
	 */
	public List<WeatherVoAirQuality> getWeatherVoAirQuality(String cityName);
	
	/**
	 * get weather and airQualtiy data of all cities
	 * @return
	 */
	public List<WeatherVoAirQuality> getWeatherVoAirQuality();
}
