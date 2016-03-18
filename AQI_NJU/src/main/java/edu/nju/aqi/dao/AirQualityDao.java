package edu.nju.aqi.dao;

import java.util.List;

import edu.nju.aqi.model.AirQuality;

public interface AirQualityDao {

	public List<AirQuality> getAllAirQuality();
	
	public boolean addAirQuality(AirQuality airQuality);
	
	public List<AirQuality> getTodaysAirQuality(String city);
	
	public List<AirQuality> getAllTodaysAirQuality();
	
	public AirQuality getCurrentAirQuality(String city);

	List<AirQuality> getAllCurrentAirQuality();
}
