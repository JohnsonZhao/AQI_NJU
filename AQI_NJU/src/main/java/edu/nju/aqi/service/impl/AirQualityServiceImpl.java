package edu.nju.aqi.service.impl;

import edu.nju.aqi.bo.AirQualityBo;
import edu.nju.aqi.dao.AirQualityDao;
import edu.nju.aqi.dao.DistrictDao;
import edu.nju.aqi.dao.LnltDao;
import edu.nju.aqi.model.AirQuality;
import edu.nju.aqi.model.District;
import edu.nju.aqi.model.Lnlt;
import edu.nju.aqi.service.AirQualityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AirQualityServiceImpl implements AirQualityService {

    @Autowired
    private AirQualityDao airQualityDao;
    @Autowired
    private LnltDao lnltDao;
    @Autowired
    private DistrictDao districtDao;

    private static final List<String> municipalityList = Arrays.asList("shanghai", "beijing", "tianjin", "chongqing");
    private static final List<String> municipalityNameList = Arrays.asList("上海", "北京", "天津", "重庆");

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
            String indexType = airQuality.getIndex_type();
            if (indexType.equals("unknown"))
                continue;
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
    public List<AirQuality> getPastDaysAirQuality(String city, int dayNum) {
        return airQualityDao.getPastDaysAirQuality(city, dayNum);
    }

    /**
     * get related city names and aqi
     *
     * @param cityName pinyin
     * @return List<AirQuality>
     */
    @Override
    public List<AirQuality> getRelatedCities(String cityName) {
        List<String> cityNameList = new ArrayList<>();
        List<AirQuality> resultList;

        // 如果是直辖市,则直接返回;否则,查询数据库中得到该省城市
        if (municipalityList.contains(cityName)) {
            cityNameList = municipalityNameList;
        } else {
            List<District> relatedCities = districtDao.getRelatedCities(cityName);
            // 如果为空,则返回空的list
            if (relatedCities == null || relatedCities.size() == 0)
                return new ArrayList<>();
            for (District district : relatedCities) {
                if (!district.getPinyin().equals(cityName))
                    cityNameList.add(district.getName());
            }
        }

        resultList = airQualityDao.getAirQualityByNameList(cityNameList);
        if (resultList == null) {
            return new ArrayList<>();
        } else {
            return resultList;
        }
    }

    @Override
    public String getCityProvince(String cityName) {
        return districtDao.getCityProvince(cityName);
    }

}
