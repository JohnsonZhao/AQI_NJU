package edu.nju.aqi.service.impl;

import edu.nju.aqi.analysis.helper.AreaCorrelation;
import edu.nju.aqi.bo.AirQualityBo;
import edu.nju.aqi.dao.AirQualityDao;
import edu.nju.aqi.dao.DistrictDao;
import edu.nju.aqi.dao.LnltDao;
import edu.nju.aqi.model.AirQuality;
import edu.nju.aqi.model.District;
import edu.nju.aqi.model.Lnlt;
import edu.nju.aqi.service.AirQualityService;
import edu.nju.aqi.service.AnalysisService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.Map.Entry;

@Service
public class AirQualityServiceImpl implements AirQualityService {

    @Autowired
    private AirQualityDao airQualityDao;
    @Autowired
    private LnltDao lnltDao;
    @Autowired
    private DistrictDao districtDao;
    @Autowired
    private AnalysisService analysisService;

    private static final List<String> municipalityList = Arrays.asList("shanghai", "beijing", "tianjin", "chongqing");
    private static final List<String> municipalityNameList = Arrays.asList("上海", "北京", "天津", "重庆");
    private static final int MOST_SIMILAR_NUM = 5;
    
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
                if (district != null)
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

    @Override
    public String getCityProvinceByChinese(String cityName) {
        return districtDao.getCityProvinceByChinese(cityName);
    }

	@Override
	public List<Map.Entry<AirQuality,Double>> getSimilarCitiesInProvince(String city) {
		List<String> cityNameList = new ArrayList<String>();

        // 如果是直辖市,则直接返回;否则,查询数据库中得到该省城市
        if (municipalityList.contains(city)) {
            cityNameList = municipalityList;
        } else {
            List<District> relatedCities = districtDao.getCitiesInProvince(city);
            for (District district : relatedCities) {
                if (district != null)
                    cityNameList.add(district.getPinyin());
            }
        }
        //HashMap<AirQuality,AreaCorrelation> similarities = new HashMap<AirQuality,AreaCorrelation>();
        HashMap<AirQuality, Double> ret = new HashMap<AirQuality,Double>();
        for(int i=0;i<cityNameList.size();i++){
        	AreaCorrelation areaCorrelation = (AreaCorrelation) analysisService.getCorrelation(city, cityNameList.get(i));
        	AirQuality airQuality = airQualityDao.getCurrentAirQuality(cityNameList.get(i));
        	//similarities.put(airQuality, areaCorrelation);
        	ret.put(airQuality, areaCorrelation.getSimilar());
        }
        
        /*List<Map.Entry<AirQuality,AreaCorrelation>> infoIds =
        	    new ArrayList<Map.Entry<AirQuality,AreaCorrelation>>(similarities.entrySet());
        //sort by the similar value of to area correlations
        Collections.sort(infoIds, new Comparator<Map.Entry<AirQuality,AreaCorrelation>>() {   
			@Override
			public int compare(Entry<AirQuality, AreaCorrelation> o1,
					Entry<AirQuality, AreaCorrelation> o2) {
				if ((o2.getValue().getSimilar()-o1.getValue().getSimilar())>0)  
			          return 1;  
			        else if((o2.getValue().getSimilar()-o1.getValue().getSimilar())==0)  
			          return 0;  
			        else   
			          return -1;
			}
        }); */
        /*for(AirQuality aq:similarities.keySet()){
        		ret.put(aq, similarities.get(aq).getSimilar());
        }*/
        List<Map.Entry<AirQuality,Double>> infoIds2 =
        	    new ArrayList<Map.Entry<AirQuality,Double>>(ret.entrySet());
        //sort by the similar value of to area correlations
        Collections.sort(infoIds2, new Comparator<Map.Entry<AirQuality,Double>>() {   
			@Override
			public int compare(Entry<AirQuality, Double> o1,
					Entry<AirQuality, Double> o2) {
				 return  o2.getValue()>o1.getValue()?1:-1;
			}
        });
        /*HashMap<AirQuality,Double> result = new HashMap<AirQuality,Double>();
        for(Map.Entry<AirQuality, Double> entry : infoIds2){
        	result.put(entry.getKey(), entry.getValue());
        }*/
        /*for(int i=0;i<infoIds2.size();i++){
        	result.put(infoIds2.get(i).getKey(), infoIds2.get(i).getValue());
        }*/
		return infoIds2;
	}

}
