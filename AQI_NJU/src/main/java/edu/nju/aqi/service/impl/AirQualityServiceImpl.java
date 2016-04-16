package edu.nju.aqi.service.impl;

import edu.nju.aqi.bo.AirQualityBo;
import edu.nju.aqi.dao.AirQualityDao;
import edu.nju.aqi.dao.LnltDao;
import edu.nju.aqi.model.AirQuality;
import edu.nju.aqi.model.Lnlt;
import edu.nju.aqi.service.AirQualityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AirQualityServiceImpl implements AirQualityService {

    @Autowired
    private AirQualityDao airQualityDao;
    @Autowired()
    private LnltDao lnltDao;

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
        List<AirQuality> airQualityList = airQualityDao.getAllAirQuality();
        return airQualityList;
    }


    @Override
    public boolean addAirQuality(AirQuality airQuality) {
        return airQualityDao.addAirQuality(airQuality);
    }

    @Override
    public boolean addAirQualityList(List<AirQuality> airQualityList) {
        return airQualityDao.addAirQualityList(airQualityList);
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

    @Override
    public List<AirQualityBo> getAllCurrentAirQuality() {
        List<AirQualityBo> resultList = new ArrayList<>();
        List<AirQuality> airQualityList = airQualityDao.getAllCurrentAirQuality();
        List<Lnlt> lnltList = lnltDao.getAllLnlt();

        Map<String, String> nameToCoordinateMap = new HashMap<>();
        for (Lnlt lnlt : lnltList) {
            String cityName = lnlt.getCityName();
            String coordinate = "[" + lnlt.getLongitude() + "," + lnlt.getLatitude() + "]";
            nameToCoordinateMap.put(cityName, coordinate);

        }

        for (AirQuality airQuality : airQualityList) {
            String cityName = airQuality.getCity_name();
            if (nameToCoordinateMap.keySet().contains(cityName)) {
                AirQualityBo bo = new AirQualityBo();
                bo.setId(airQuality.getId());
                bo.setAqi(airQuality.getAqi());
                bo.setCityName(airQuality.getCity_name());
                bo.setDate(airQuality.getDate());
                bo.setCo(airQuality.getCo());
                bo.setNo2(airQuality.getNo2());
                bo.setO3(airQuality.getO3());
                bo.setPm25(airQuality.getPm25());
                bo.setIndexType(airQuality.getIndex_type());
                bo.setPrimPollu(airQuality.getPrim_pollu());
                bo.setCoordinates(nameToCoordinateMap.get(airQuality.getCity_name()));
                resultList.add(bo);
            }
        }
        return resultList;
    }

	@Override
	public List<AirQuality> get24HoursAirQuality(String city) {
		return airQualityDao.get24HoursAirQuality(city);
	}

	@Override
	public List<AirQuality> getPastHoursAirQuality(String city, int hourNum) {
		return airQualityDao.getPastHoursAirQuality(city, hourNum);
	}

	@Override
	public AirQuality getCurrentAirQualityByChinese(String city) {
		return airQualityDao.getCurrentAirQualityByChinese(city);
	}
	
	@Override
	public HashMap<String,String> getPastDaysAirQuality(String city, int dayNum){
		return airQualityDao.getPastDaysAirQuality(city, dayNum);
	}


}
