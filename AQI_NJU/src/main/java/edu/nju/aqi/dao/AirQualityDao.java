package edu.nju.aqi.dao;

import edu.nju.aqi.model.AirQuality;

import java.util.List;

public interface AirQualityDao {

	List<AirQuality> getAllAirQuality();
	
	boolean addAirQuality(AirQuality airQuality);

	boolean addAirQualityList(List<AirQuality> airQualityList);
	
	List<AirQuality> getTodaysAirQuality(String city);
	
	List<AirQuality> get24HoursAirQuality(String city);
	
	List<AirQuality> getPastHoursAirQuality(String city, int hourNum);
	
	List<AirQuality> getPastDaysAirQuality(String city, int dayNum);
	
	List<AirQuality> getAllTodaysAirQuality();
	
	AirQuality getCurrentAirQuality(String city);
	
	AirQuality getCurrentAirQualityByChinese(String city);

	List<AirQuality> getAllCurrentAirQuality();

	List<AirQuality> getAirQualityByNameList(List<String> nameList);

}
