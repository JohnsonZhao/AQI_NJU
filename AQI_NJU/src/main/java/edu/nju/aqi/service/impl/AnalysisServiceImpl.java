package edu.nju.aqi.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import edu.nju.aqi.analysis.ICorrelation;
import edu.nju.aqi.analysis.IPrediction;
import edu.nju.aqi.analysis.helper.Degree;
import edu.nju.aqi.model.AirQuality;
import edu.nju.aqi.service.AnalysisService;

public class AnalysisServiceImpl implements AnalysisService{
	
	private ICorrelation corrlation;
	
	private IPrediction prediction;
	
	public void setPrediction(IPrediction prediction) {
		this.prediction = prediction;
	}
	
	public IPrediction getPrediction(){
		return this.prediction;
	}
	
	public ICorrelation getCorrlation(){
		return this.corrlation;
	}
	
	public void setCorrelation(ICorrelation correlation) {
		this.corrlation = correlation;
	}
	
	
	@Override
	public String getCorrelation(String city1, String city2) {
		Degree degree = corrlation.getCorrelation(city1, city2);
		StringBuffer buffer = new StringBuffer(degree.getDesc());
		buffer.append("\n similarity is ");
		buffer.append(degree.getSimilar());
		buffer.append("\n correlation is ");
		buffer.append(degree.getCorrealtion());
		
		return buffer.toString();
	}

	@Override
	public List<AirQuality> predict(String cityName) {
		List<AirQuality> airQualities = new ArrayList<>();
		List<Map<String, Double>> list= prediction.execute(cityName);
		if (list == null || list.size() == 0) {
			return null;
		}
		for(Map<String, Double> map: list){
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
