package edu.nju.aqi.dao;

import edu.nju.aqi.model.AirQuality;

import java.util.List;

public interface AirQualityDao {

	public List<AirQuality> getAllAirQuality();
	
	public boolean addAirQuality(AirQuality airQuality);

	public boolean addAirQualityList(List<AirQuality> airQualityList);
	
	public List<AirQuality> getTodaysAirQuality(String city);
	
	public List<AirQuality> getAllTodaysAirQuality();
	
	public AirQuality getCurrentAirQuality(String city);

	List<AirQuality> getAllCurrentAirQuality();
}
