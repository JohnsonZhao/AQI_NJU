package edu.nju.aqi.service.impl;

import java.util.ArrayList;
import java.util.List;

import edu.nju.aqi.bo.AirQualityBo;
import edu.nju.aqi.dao.AirQualityDao;
import edu.nju.aqi.dao.LnltDao;
import edu.nju.aqi.model.AirQuality;
import edu.nju.aqi.model.Lnlt;
import edu.nju.aqi.service.AirQualityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        for (AirQuality airQuality : airQualityList) {
            String cityName = airQuality.getCity_name();
            for (Lnlt lnlt : lnltList) {
                if (lnlt.getCityName().equals(cityName)) {
                    AirQualityBo bo = new AirQualityBo();
                    bo.setId(airQuality.getId());
                    bo.setAqi(airQuality.getAqi());
                    bo.setCityName(airQuality.getCity_name());
                    bo.setDate(airQuality.getDate());
                    bo.setCo(airQuality.getCo());
                    bo.setIndexType(airQuality.getIndex_type());
                    bo.setPrimPollu(airQuality.getPrim_pollu());
                    bo.setCoordinates("[" + lnlt.getLongitude() + "," + lnlt.getLatitude() + "]");
                    resultList.add(bo);
                }
            }
        }
        return resultList;
    }
}
