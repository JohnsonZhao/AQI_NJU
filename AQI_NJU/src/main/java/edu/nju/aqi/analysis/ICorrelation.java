package edu.nju.aqi.analysis;
import edu.nju.aqi.analysis.helper.Degree;

public interface ICorrelation {
	
	/**
	 * 
	 * @param city1 cityname
	 * @param city2 cityname
	 * @return 相关性程度
	 */
	public Degree getCorrelation(String city1, String city2);

}
