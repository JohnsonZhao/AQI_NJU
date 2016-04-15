package edu.nju.aqi.analysis;

import java.util.List;

import edu.nju.aqi.analysis.helper.AreaCorrelation;
import edu.nju.aqi.analysis.helper.IndexCorrelation;

public interface ICorrelationAnalysis {
	
	/**
	 * 
	 * @param city1 cityname
	 * @param city2 cityname
	 * @return 相关性程度
	 */
	public AreaCorrelation getCorrelation(String city1, String city2);
	
	/**
	 * 分析区域间的指数
	 * @param city
	 * @return
	 */
	public List<IndexCorrelation> getCorrelation(String city);
	
}
