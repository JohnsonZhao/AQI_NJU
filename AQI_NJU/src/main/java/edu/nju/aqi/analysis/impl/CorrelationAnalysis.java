package edu.nju.aqi.analysis.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import edu.nju.aqi.analysis.ICorrelationAnalysis;
import edu.nju.aqi.analysis.helper.AreaCorrelation;
import edu.nju.aqi.analysis.helper.IndexCorrelation;
import edu.nju.aqi.analysis.helper.KeyProperty;
import edu.nju.aqi.analysis.helper.CorrelationMathUtils;
import edu.nju.aqi.dao.AirQualityDao;
import edu.nju.aqi.model.AirQuality;

public class CorrelationAnalysis implements ICorrelationAnalysis {

	private double similarity = 0.0;
	private double correlation = 0.0;
	private final double ZERO = 0.0;
	private final static String KEY_BASE = "aqi";

	private AirQualityDao airQualityDao;

	public AirQualityDao getAirQualityDao() {
		return airQualityDao;
	}

	public void setAirQualityDao(AirQualityDao airQualityDao) {
		this.airQualityDao = airQualityDao;
	}

	@Override
	public AreaCorrelation getCorrelation(String city1, String city2) {
		similarity = doSimilarityAnalysis(city1, city2);
		correlation = doCorrelationAnalysis(city1, city2);
		return new AreaCorrelation(similarity, correlation);
	}

	@Override
	public List<IndexCorrelation> getCorrelation(String city) {
		List<AirQuality> airQualities = airQualityDao.getPastHoursAirQuality(city, 100);
		int size = airQualities.size();
		double[] aqis = new double[size];
		double[] so2s = new double[size];
		double[] no2s = new double[size];
		double[] cos = new double[size];
		double[] o3s = new double[size];
		double[] pm10s = new double[size];
		double[] pm25s = new double[size];
		int index = 0;
		for (AirQuality airQuality : airQualities) {
			System.out.println(airQuality.toString());
			double aqi = Double.parseDouble(airQuality.getAqi());
			double so2 = Double.parseDouble(airQuality.getSo2());
			double no2 = Double.parseDouble(airQuality.getNo2());
			double co = Double.parseDouble(airQuality.getCo());
			double o3 = Double.parseDouble(airQuality.getO3());
			double pm10 = Double.parseDouble(airQuality.getPm10());
			double pm25 = Double.parseDouble(airQuality.getPm25());

			if (Double.compare(ZERO, aqi) == 0 || Double.compare(ZERO, so2) == 0 || Double.compare(ZERO, no2) == 0
					|| Double.compare(ZERO, co) == 0 || Double.compare(ZERO, o3) == 0 || Double.compare(ZERO, pm10) == 0
					|| Double.compare(ZERO, pm25) == 0) {
				continue;
			}
			aqis[index] = aqi;
			so2s[index] = so2;
			no2s[index] = no2;
			cos[index] = co;
			o3s[index] = o3;
			pm10s[index] = pm10;
			pm25s[index] = pm25;
			index++;
		}
		List<IndexCorrelation> indexCorrelations = new ArrayList<>(6);
		IndexCorrelation so2Index = new IndexCorrelation(KEY_BASE, "so2",
				CorrelationMathUtils.pearsonCorrelation(aqis, so2s));
		indexCorrelations.add(so2Index);

		IndexCorrelation no2Index = new IndexCorrelation(KEY_BASE, "no2",
				CorrelationMathUtils.pearsonCorrelation(aqis, no2s));
		indexCorrelations.add(no2Index);

		IndexCorrelation coIndex = new IndexCorrelation(KEY_BASE, "co",
				CorrelationMathUtils.pearsonCorrelation(aqis, cos));
		indexCorrelations.add(coIndex);

		IndexCorrelation o3Index = new IndexCorrelation(KEY_BASE, "o3",
				CorrelationMathUtils.pearsonCorrelation(aqis, o3s));
		indexCorrelations.add(o3Index);

		IndexCorrelation pm10Index = new IndexCorrelation(KEY_BASE, "pm10",
				CorrelationMathUtils.pearsonCorrelation(aqis, pm10s));
		indexCorrelations.add(pm10Index);

		IndexCorrelation pm25Index = new IndexCorrelation(KEY_BASE, "pm25",
				CorrelationMathUtils.pearsonCorrelation(aqis, pm25s));
		indexCorrelations.add(pm25Index);
		return indexCorrelations;
	}

	private double doSimilarityAnalysis(String city1, String city2) {
		List<AirQuality> airQuality1s = airQualityDao.getTodaysAirQuality(city1);
		List<AirQuality> airQuality2s = airQualityDao.getTodaysAirQuality(city2);

		if (airQuality1s == null || airQuality2s == null || airQuality1s.size() == 0 || airQuality2s.size() == 0) {
			return 0.0;
		}

		AirQuality airQuality1 = airQuality1s.get(0);
		AirQuality airQuality2 = airQuality2s.get(0);
		Field[] fields = AirQuality.class.getDeclaredFields();

		int count = 0;
		for (Field field : fields) {
			field.setAccessible(true);
			if (field.getAnnotation(KeyProperty.class) != null) {
				count++;
			}
		}
		double[] vector1 = new double[count];
		double[] vector2 = new double[count];
		int index = 0;
		for (Field field : fields) {
			field.setAccessible(true);
			if (field.getAnnotation(KeyProperty.class) == null) {
				continue;
			}
			try {
				if (field.get(airQuality1) != null && !field.get(airQuality1).equals("")) {
					vector1[index] = Double.parseDouble((String) field.get(airQuality1));
				}
				if (field.get(airQuality2) != null && !field.get(airQuality2).equals("")) {
					vector2[index] = Double.parseDouble((String) field.get(airQuality2));
				}
				index++;
			} catch (IllegalArgumentException | IllegalAccessException | SecurityException e) {
				System.out.println("Error: while getting similarity " + e);
			}
		}
		return CorrelationMathUtils.cosineSimilarity(vector1, vector2);
	}

	private double doCorrelationAnalysis(String city1, String city2) {
		List<AirQuality> airQuality1s = airQualityDao.get24HoursAirQuality(city1);
		List<AirQuality> airQuality2s = airQualityDao.get24HoursAirQuality(city2);

		if (airQuality1s == null || airQuality2s == null || airQuality1s.size() == 0 || airQuality2s.size() == 0) {
			return 0.0;
		}
		int len1 = airQuality1s.size();
		int len2 = airQuality2s.size();
		int len = len1 > len2 ? len2 : len1;
		double[] vector1 = new double[len];
		double[] vector2 = new double[len];
		int index = 0;
		for (int i = 0; i < len1; i++) {
			String time1 = airQuality1s.get(i).getDate();
			for (int j = 0; j > len2; j++) {
				if (time1.equals(airQuality2s.get(j).getDate())) {
					vector1[index] = Double.parseDouble(airQuality1s.get(i).getAqi());
					vector2[index] = Double.parseDouble(airQuality2s.get(j).getAqi());
					index++;
					break;
				}
			}
		}
		return CorrelationMathUtils.pearsonCorrelation(vector1, vector2);
	}

}
