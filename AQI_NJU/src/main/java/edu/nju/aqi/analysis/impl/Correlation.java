package edu.nju.aqi.analysis.impl;

import java.lang.reflect.Field;
import java.util.List;


import edu.nju.aqi.analysis.ICorrelation;
import edu.nju.aqi.analysis.helper.Degree;
import edu.nju.aqi.analysis.helper.KeyProperty;
import edu.nju.aqi.analysis.helper.SimilarityUtils;
import edu.nju.aqi.dao.AirQualityDao;
import edu.nju.aqi.model.AirQuality;

public class Correlation implements ICorrelation {

	private double similarity = 0.0;
	private double correlation = 0.0; 
			
	private AirQualityDao airQualityDao;

	public AirQualityDao getAirQualityDao() {
        return airQualityDao;
    }

    public void setAirQualityDao(AirQualityDao airQualityDao) {
        this.airQualityDao = airQualityDao;
    }

	@Override
	public Degree getCorrelation(String city1, String city2) {
		similarity = doSimilarityAnalysis(city1, city2);
		correlation = doCorrelationAnalysis(city1, city2);
		return new Degree(similarity, correlation);
	}
	

	private double doSimilarityAnalysis(String city1, String city2){
		List<AirQuality> airQuality1s = airQualityDao.getTodaysAirQuality(city1);
		List<AirQuality> airQuality2s = airQualityDao.getTodaysAirQuality(city2);
		
		if (airQuality1s == null || airQuality2s == null || airQuality1s.size() == 0 || airQuality2s.size() == 0) {
			return 0.0;
		}

		AirQuality airQuality1 = airQuality1s.get(0);
		AirQuality airQuality2 =airQuality2s.get(0);
		Field[] fields = AirQuality.class.getDeclaredFields();

		int count = 0;
		for (Field field : fields) {
			field.setAccessible(true);
			if (field.getAnnotation(KeyProperty.class) != null) {
				count ++;
			}
		}
		double[] vector1 = new double[count];
		double[] vector2 = new double[count];
		int index = 0;
		for(Field field: fields){
			field.setAccessible(true);
			if (field.getAnnotation(KeyProperty.class) == null) {
				continue;
			}
			try {
				if (field.get(airQuality1) != null && !field.get(airQuality1).equals("")) {
					vector1[index] = Double.parseDouble((String)field.get(airQuality1));
				}
				if (field.get(airQuality2)!= null && !field.get(airQuality2).equals("")) {
					vector2[index] = Double.parseDouble((String)field.get(airQuality2));
				}
				index ++;
			} catch (IllegalArgumentException | IllegalAccessException | SecurityException e) {
				System.out.println("Error: while getting similarity " + e);
			}
		}
		return SimilarityUtils.cosineSimilarity(vector1, vector2);
	}
	
	private double doCorrelationAnalysis(String city1, String city2){
		List<AirQuality> airQuality1s = airQualityDao.get24HoursAirQuality(city1);
		List<AirQuality> airQuality2s = airQualityDao.get24HoursAirQuality(city2);
		
		if (airQuality1s == null || airQuality2s == null || airQuality1s.size() == 0 || airQuality2s.size() == 0) {
			return 0.0;
		}
		int len1 = airQuality1s.size();
		int len2 = airQuality2s.size();
		int len = len1 > len2 ? len2: len1;
		double[] vector1 = new double[len];
		double[] vector2 = new double[len];
		int index = 0;
		for(int i = 0; i < len1; i ++){
			String time1 = airQuality1s.get(i).getDate();
			for(int j = 0; j > len2; j ++){
				if (time1.equals(airQuality2s.get(j).getDate())) {
					vector1[index] = Double.parseDouble(airQuality1s.get(i).getAqi());
					vector2[index] = Double.parseDouble(airQuality2s.get(j).getAqi());
					index ++;
					break;
				}
			}
		}
		return SimilarityUtils.pearsonCorrelation(vector1, vector2);
	}
}
