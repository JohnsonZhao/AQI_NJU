package edu.nju.aqi.service.impl;

import java.util.List;

import edu.nju.aqi.dao.AirQualityDao;
import edu.nju.aqi.model.AirQuality;
import edu.nju.aqi.service.AirQualityService;

public class AirQualityServiceImpl implements AirQualityService {

	private AirQualityDao airQualityDao;
	@Override
	public AirQualityDao getAirQualityDao() {
		return airQualityDao;
	}

	@Override
	public void setAirQualityDao(AirQualityDao airQualityDao) {
		this.airQualityDao = airQualityDao;
	}

	@Override
	public List<AirQuality> getAllAirQuality() {
		return airQualityDao.getAllAirQuality();
	}

	@Override
	public boolean addAirQuality(AirQuality airQuality) {
		return airQualityDao.addAirQuality(airQuality);
	}

	@Override
	public List<AirQuality> getTodaysAirQuality(String city) {
		return airQualityDao.getTodaysAirQuality(city);
	}

	@Override
	public List<AirQuality> getAllTodaysAirQuality() {
		return airQualityDao.getAllTodaysAirQuality();
	}

	@Override
	public AirQuality getCurrentAirQuality(String city) {
		return airQualityDao.getCurrentAirQuality(city);
	}

}
