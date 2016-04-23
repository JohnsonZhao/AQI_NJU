package edu.nju.aqi.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.nju.aqi.analysis.ICorrelationAnalysis;
import edu.nju.aqi.analysis.IPrediction;
import edu.nju.aqi.analysis.helper.AreaCorrelation;
import edu.nju.aqi.analysis.helper.IndexCorrelation;
import edu.nju.aqi.model.AirQuality;
import edu.nju.aqi.service.AnalysisService;

public class AnalysisServiceImpl implements AnalysisService{
	
	private ICorrelationAnalysis corrlation;
	
	private IPrediction prediction;
	
	public void setPrediction(IPrediction prediction) {
		this.prediction = prediction;
	}
	
	public IPrediction getPrediction(){
		return this.prediction;
	}
	
	public ICorrelationAnalysis getCorrlation(){
		return this.corrlation;
	}
	
	public void setCorrelation(ICorrelationAnalysis correlation) {
		this.corrlation = correlation;
	}
	
	
	@Override
	public AreaCorrelation getCorrelation(String city1, String city2) {
		AreaCorrelation degree = corrlation.getCorrelation(city1, city2);
		StringBuffer buffer = new StringBuffer(degree.getDesc());
		buffer.append("\n similarity is ");
		buffer.append(degree.getSimilar());
		buffer.append("\n correlation is ");
		buffer.append(degree.getCorrealtion());
		
		return degree;
	}
	
	@Override
	public List<IndexCorrelation> getCorrelation(String city){
		List<IndexCorrelation> correlations =corrlation.getCorrelation(city);
		int maxIndex = 0;
		int minIndex = 0;
		for (int i= 0; i< correlations.size(); i++) {
			double correlation = correlations.get(i).getCorrealtion();
			if (Double.compare(maxIndex, correlation) > 0) {
				maxIndex = i;
			}
			else if (Double.compare(minIndex, correlation) < 0) {
				minIndex = i;
			}
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append("与AQI指数关联性最强的指数为：");
		buffer.append(correlations.get(maxIndex).getCompareKey());
		buffer.append(",");
		buffer.append(correlations.get(maxIndex).getDesc());
		buffer.append("\n");
		
		buffer.append("与AQI指数关联性最弱的指数为：");
		buffer.append(correlations.get(minIndex).getCompareKey());
		buffer.append(",");
		buffer.append(correlations.get(minIndex).getDesc());
		buffer.append("\n");
		
		return correlations;
	}

	@Override
	public List<AirQuality> predict(String cityName) {
		List<AirQuality> airQualities = new ArrayList<>();
		List<Map<String, Object>> list= prediction.execute(cityName);
		if (list == null || list.size() == 0) {
			return null;
		}
		for(Map<String, Object> map: list){
			/**
			 * 使用反射设置类成员变量
			 */
			AirQuality airQuality = new AirQuality();
			Method[] methods = airQuality.getClass().getDeclaredMethods();
			for(Method method:methods){
				String name = method.getName();
				if (!name.startsWith("set")) {
					continue;
				}
				String key = name.substring(3).toLowerCase();
				if (map.containsKey(key)) {
					try {
						method.invoke(airQuality, String.valueOf(map.get(key)));
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						System.out.println("Error: while invoking method " + e);
					}
				}
			}
			airQualities.add(airQuality);
		}
		return airQualities;
	}
	

}
