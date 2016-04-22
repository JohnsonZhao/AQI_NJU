package edu.nju.aqi.dao;

import edu.nju.aqi.model.District;

import java.util.List;

/**
 * Created by lulei on 16/4/21.
 */
public interface DistrictDao {
    List<District> getRelatedCities(String cityName);

    String getCityProvince(String cityName);

    String getCityProvinceByChinese(String chineseName);
}
