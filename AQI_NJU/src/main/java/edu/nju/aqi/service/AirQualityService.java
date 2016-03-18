package edu.nju.aqi.service;

import java.util.List;

import edu.nju.aqi.bo.AirQualityBo;
import edu.nju.aqi.dao.AirQualityDao;
import edu.nju.aqi.model.AirQuality;

public interface AirQualityService {

	public AirQualityDao getAirQualityDao();
	public void setAirQualityDao(AirQualityDao airQualityDao);
	
	public List<AirQuality> getAllAirQuality();
	
	public boolean addAirQuality(AirQuality airQuality);
	
	public List<AirQuality> getTodaysAirQuality(String city);
	
	public List<AirQuality> getAllTodaysAirQuality();
	
	public AirQuality getCurrentAirQuality(String city);

	List<AirQualityBo> getAllCurrentAirQuality();
}
