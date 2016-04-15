package edu.nju.aqi.service;

import java.util.List;

import edu.nju.aqi.analysis.AbstractCorrelation;
import edu.nju.aqi.analysis.helper.IndexCorrelation;
import edu.nju.aqi.model.AirQuality;

public interface AnalysisService {
	
	/**
	 * 获取城市间的关联度
	 * @param city1  城市名
	 * @param city2 城市名
	 */
	public AbstractCorrelation getCorrelation(String city1, String city2);
	
	/**
	 * 获取某一城市的指数相关性
	 * @param city
	 * @return
	 */
	public List<IndexCorrelation> getCorrelation(String city);
	/**
	 * 预测未来空气质量
	 * @param cityName
	 * @return
	 */
	public List<AirQuality> predict(String cityName);
	

}
