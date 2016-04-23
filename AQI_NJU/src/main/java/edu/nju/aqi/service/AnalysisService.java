package edu.nju.aqi.service;

import java.util.List;

import edu.nju.aqi.analysis.helper.AreaCorrelation;
import edu.nju.aqi.analysis.helper.IndexCorrelation;
import edu.nju.aqi.model.AirQuality;

public interface AnalysisService {
	
	/**
	 * 获取城市间的时序上的关联度，指数的相似度
	 * @param city1  城市名
	 * @param city2 城市名
	 */
	public AreaCorrelation getCorrelation(String city1, String city2);
	
	/**
	 * 获取某一城市的各指数与AOI相关性程度
	 * @param city 城市名，英文
	 * @return
	 */
	public List<IndexCorrelation> getCorrelation(String city);
	/**
	 * 预测未来空气质量，下一个小时为起点24个小时的数据
	 * @param cityName 城市名，英文
	 * @return
	 * AirQuality对象，aqi, date, cityName三个属性有值
	 */
	public List<AirQuality> predict(String cityName);
	

}
