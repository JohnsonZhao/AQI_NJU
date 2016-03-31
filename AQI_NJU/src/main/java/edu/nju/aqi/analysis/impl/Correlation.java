package edu.nju.aqi.analysis.impl;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import edu.nju.aqi.analysis.ICorrelation;
import edu.nju.aqi.analysis.helper.Degree;
import edu.nju.aqi.analysis.helper.KeyProperty;
import edu.nju.aqi.dao.AirQualityDao;
import edu.nju.aqi.model.AirQuality;

public class Correlation implements ICorrelation{
	private static final double MINIUM = 0.0;
    private static final double MAX = 1.0;
    
//    @Autowired
    private AirQualityDao airQualityDao;
    
	@Override
	public Degree getCorrelation(String city1, String city2) {
		AirQuality airQuality1 = airQualityDao.getCurrentAirQuality(city1);
		AirQuality airQuality2 = airQualityDao.getCurrentAirQuality(city2);
		
		Map<String, Double> map1 = new HashMap<>();
		Map<String, Double> map2 = new HashMap<>();
		Field[] fields = AirQuality.class.getDeclaredFields();
		for (Field field : fields) {
			if (field.getAnnotation(KeyProperty.class) == null) {
				continue;
			}
			String fieldName = field.getName();
			try {
				if (field.get(airQuality1) != null && !field.get(airQuality1).equals("")) {
					map1.put(fieldName, field.getDouble(airQuality1));
				}
				if (field.get(airQuality2) != null && !field.get(airQuality2).equals("")) {
					map2.put(fieldName, field.getDouble(airQuality2));
				}
			} catch (IllegalArgumentException | IllegalAccessException | SecurityException e) {
				System.out.println("Error: while getting correlation " + e);
			}
		}
		return getCorrelation(map1, map2);
	}
	
	private Degree getCorrelation(Map<String, Double> map1, Map<String, Double> map2){
		 double correlation = 0.0;
	        double sum1 = 0.0, sum2 = 0.0;
	        double squareSum1 = 0.0, squareSum2 = 0.0;
	        double productSum = 0.0;
	        int commonItemLen = 0;

	        Iterator<String> iterator1 = map1.keySet().iterator();
	        while (iterator1.hasNext()) {
	            String key1 = iterator1.next();
	            Double value1 = map1.get(key1);
	            Double value2 = map2.get(key1);
	            if (value2 == null) {
	                continue;
	            }
	            commonItemLen++;
	            //求和
	            sum1 += value1;
	            sum2 += value2;

	            //平方和
	            squareSum1 += Math.pow(value1, 2);
	            squareSum2 += Math.pow(value2, 2);

	            //乘积和
	            productSum += value1 * value2;
	        }
	        if (commonItemLen < 1) {
	            correlation = MINIUM;
	           return new Degree(correlation);
	        }
	        double num = commonItemLen * productSum - sum1 * sum2;
	        double den = Math.sqrt((commonItemLen * squareSum1 - Math.pow(sum1, 2)) * (commonItemLen * squareSum2 - Math.pow(sum2, 2)));
	        correlation = (Double.compare(den, 0.0) == 0) ? MAX : num/den;
	        return new Degree(correlation);
	}

}
