package edu.nju.aqi.service;

import edu.nju.aqi.bo.AirQualityBo;
import edu.nju.aqi.dao.AirQualityDao;
import edu.nju.aqi.model.AirQuality;

import java.util.List;

public interface AirQualityService {

	public AirQualityDao getAirQualityDao();
	public void setAirQualityDao(AirQualityDao airQualityDao);
	
	public List<AirQuality> getAllAirQuality();
	
	public boolean addAirQuality(AirQuality airQuality);

	public boolean addAirQualityList(List<AirQuality> airQualityList);
	
	public List<AirQuality> getTodaysAirQuality(String city);
	
	public List<AirQuality> getAllTodaysAirQuality();
	
	public AirQuality getCurrentAirQuality(String city);

	List<AirQualityBo> getAllCurrentAirQuality();
	
	public List<AirQuality> get24HoursAirQuality(String city);
}
