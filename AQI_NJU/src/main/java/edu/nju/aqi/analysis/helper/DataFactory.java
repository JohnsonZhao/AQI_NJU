package edu.nju.aqi.analysis.helper;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.neuroph.core.learning.SupervisedTrainingElement;
import org.neuroph.core.learning.TrainingElement;
import org.neuroph.core.learning.TrainingSet;

import edu.nju.aqi.analysis.impl.WeatherFactory;
import edu.nju.aqi.dao.WeatherDao;
import edu.nju.aqi.model.AirQuality;
import edu.nju.aqi.model.Weather;
import edu.nju.aqi.model.WeatherVoAirQuality;

public class DataFactory {
	private int inputDim = 0;
	private int outputDim = 0;
	private String[] inputDataNames;
	private String[] outputDataNames;

	private WeatherDao weatherDao;

	public void setWeatherDao(WeatherDao weatherDao) {
		this.weatherDao = weatherDao;
	}

	public WeatherDao getWeatherDao() {
		return weatherDao;
	}

	public DataFactory() {
		initInput();
		initOutput();
	}

	private void initInput() {
		inputDataNames = getPropertyNames(Weather.class);
		inputDim = inputDataNames.length;
	}

	private void initOutput() {
		outputDataNames = getPropertyNames(AirQuality.class);
		outputDim = outputDataNames.length;
	}

	public int getInputDim() {
		return inputDim;
	}

	public String[] getInputPropertyNames() {
		return inputDataNames;
	}

	public int getOutputDim() {
		return outputDim;
	}

	public String[] getOutputPropertyNames() {
		return outputDataNames;
	}

	private String[] getPropertyNames(Class<?> clazz) {
		ArrayList<String> names = new ArrayList<>();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			if (field.getAnnotation(KeyProperty.class) == null) {
				continue;
			}
			names.add(field.getName());
		}
		return (String[]) names.toArray(new String[names.size()]);
	}

	public TrainingSet getTrainingData() {
		TrainingSet set = new TrainingSet(inputDim, outputDim);
		List<WeatherVoAirQuality> weatherVoAirQuality = weatherDao.getWeatherVoAirQuality();
		System.out.println("input:" + inputDim);
		System.out.println("output:" + outputDim);
		Iterator<WeatherVoAirQuality> iterator = weatherVoAirQuality.iterator();
		System.out.println(weatherVoAirQuality.size());
		while (iterator.hasNext()) {
			WeatherVoAirQuality vo = iterator.next();
			System.out.println(vo.toString());
			SupervisedTrainingElement element = new SupervisedTrainingElement(getInputData(vo.getClass(),vo),
					getOutputData(vo.getClass(), vo));
			set.addElement(element);
		}
		return set;

	}

	public TrainingSet getRealData(String cityName) {
		TrainingSet set = new TrainingSet(inputDim);
		WeatherFactory weatherFactory = new WeatherFactory();
		List<Weather> weathers = weatherFactory.getForecastWeather(cityName);
		Iterator<Weather> iterator = weathers.iterator();
		int index = 1;
		while (iterator.hasNext()) {
			Weather weather = iterator.next();
			System.out.println(index++ +":" + weather.toString());
			TrainingElement element = new TrainingElement(getInputData(weather.getClass(), weather));
			set.addElement(element);
		}
		return set;
	}

	private double[] getInputData(Class<?> vo, Object o) {
		return getData(getInputPropertyNames(), vo, o);
	}

	private double[] getOutputData(Class<?> vo, Object o) {
		return getData(getOutputPropertyNames(), vo , o);
	}

	private double[] getData(String[] headers, Class<?> vo, Object o) {
		int len = headers.length;
		double[] data = new double[len];
		for (int i = 0; i < len; i++) {
			Field field;
			try {
				field = vo.getDeclaredField(headers[i]);
				field.setAccessible(true);
				Object object = field.get(o);
				if (object == null || object.equals("")) {
					data[i] = 0.0;
				} else {
					String tmp = (String) field.get(o);
					data[i] = Double.parseDouble(tmp);
				}
			} catch (NoSuchFieldException e) {
//				System.out.println("skip, no such field");
			} catch (IllegalAccessException e) {
				System.out.println("Error: while access value " + e);
			}
		}
		return data;
	}

	public static void main(String[] args) {
		Field field;
		try {
			field = WeatherVoAirQuality.class.getDeclaredField("so2");
			System.err.println(field.getName());
		} catch (NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}
	}
}
