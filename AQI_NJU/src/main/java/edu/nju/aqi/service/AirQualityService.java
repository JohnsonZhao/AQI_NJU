package edu.nju.aqi.service;

import edu.nju.aqi.analysis.helper.AreaCorrelation;
import edu.nju.aqi.bo.AirQualityBo;
import edu.nju.aqi.dao.AirQualityDao;
import edu.nju.aqi.model.AirQuality;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface AirQualityService {

	AirQualityDao getAirQualityDao();
	void setAirQualityDao(AirQualityDao airQualityDao);
	
	List<AirQuality> getAllAirQuality();
	
	boolean addAirQuality(AirQuality airQuality);

	boolean addAirQualityList(List<AirQuality> airQualityList);
	
	List<AirQuality> getTodaysAirQuality(String city);
	
	List<AirQuality> getAllTodaysAirQuality();
	
	AirQuality getCurrentAirQuality(String city);
	
	AirQuality getCurrentAirQualityByChinese(String city);

	List<AirQualityBo> getAllCurrentAirQuality();
	
	List<AirQuality> get24HoursAirQuality(String city);
	
	List<AirQuality> getPastHoursAirQuality(String city, int hourNum);
	
	List<AirQuality> getPastDaysAirQuality(String city, int dayNum);

	List<AirQuality> getRelatedCities(String cityName);

    String getCityProvince(String cityName);

    String getCityProvinceByChinese(String cityName);
    
    List<Map.Entry<AirQuality,Double>> getSimilarCitiesInProvince(String cityName);
}
