package edu.nju.aqi.service;

import java.util.List;

import edu.nju.aqi.model.AirQuality;

public interface AnalysisService {
	
	/**
	 * 获取城市间的关联度
	 * @param city1  城市名
	 * @param city2 城市名
	 */
	public String getCorrelation(String city1, String city2);
	
	/**
	 * 预测未来空气质量
	 * @param cityName
	 * @return
	 */
	public List<AirQuality> predict(String cityName);
	

}
