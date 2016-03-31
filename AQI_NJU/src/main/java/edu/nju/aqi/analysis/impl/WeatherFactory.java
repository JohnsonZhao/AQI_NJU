package edu.nju.aqi.analysis.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import edu.nju.aqi.meta.DateUtils;
import edu.nju.aqi.meta.HttpRequestUtils;
import edu.nju.aqi.model.Weather;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class WeatherFactory {
	public static final String APPID_DEFAULT = "bcf690ecb16b701393b8b0877dfe0806";
	private String appid;
	private static final String URL_BASE = "http://api.openweathermap.org/data/2.5/";
	private static final String SEPARATOR = "/";
	private static final String CONNECTOR = "&";
	private static final String ENDMARK = "?";

	public WeatherFactory(){
		this.appid = APPID_DEFAULT;
	}
	public WeatherFactory(String appid) {
		this.appid = appid;
	}

	public void setAppID(String appid) {
		this.appid = appid;
	}

	public List<Weather> getHistoryWeather(String cityid, Date startTime, Date endTime, int amount) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(URL_BASE);
		// request type
		buffer.append(WeatherType.TYPE_HISTORY.desc);
		buffer.append(SEPARATOR);

		// cityid parameter
		buffer.append("city?id=").append(cityid);
		buffer.append(CONNECTOR);

		// start time parameter
		String startTimestamp = DateUtils.Date2TimeStamp(startTime);
		buffer.append("&type=hour&start=").append(startTimestamp);
		buffer.append(CONNECTOR);

		// end time parameter
		String endTimestamp = DateUtils.Date2TimeStamp(endTime);
		buffer.append("end=").append(endTimestamp);

		String url = buffer.toString();
		String result = null;
		try {
			result = HttpRequestUtils.doGet(url);
		} catch (Exception e) {
			System.err.println("Error: while get data from url \"" + url + "\"@" + e);
		}
		if (result != null) {
		}
		return null;

	}

	public Weather getCurrentWeather(String cityName) {
		String url = generateURL(cityName, WeatherType.TYPE_CURRENT);
		return getWeather(url, cityName);
	}
	
//	public Weather getCurrentWeather(String lon, String lat){
//		String url = generateURL(lon, lat, WeatherType.TYPE_CURRENT);
//		return getWeather(url, c);
//	}
	
	public List<Weather> getForecastWeather(String cityName) {
		String url = generateURL(cityName, WeatherType.TYPE_FORCASR);
		return getWeathers(url, cityName);
	}
	
//	public List<Weather> getForecastWeather(String lon, String lat){
//		String url = generateURL(lon, lat, WeatherType.TYPE_FORCASR);
//		return getWeathers(url);
//	}
	
	private Weather getWeather(String url, String cityName){
		String result = null;
		try {
			result = HttpRequestUtils.doGet(url);
		} catch (Exception e) {
			System.err
					.println("Error: while get current data from url \"" + url + "\"@" + e);
		}
		if (result != null) {
			Weather weather = parserWeather(JSONObject.fromObject(result));
			if (weather != null) {
				weather.setCityName(cityName);
				weather.setType(WeatherType.TYPE_CURRENT.getDesc());
				/*construct air_quality foreign key*/
				StringBuffer buffer = new StringBuffer(cityName);
				buffer.append("_");
				buffer.append(DateUtils.getDateStr());
				weather.setQualityId(buffer.toString());
				return weather;
			}
		}
		return null;
	}

	private List<Weather> getWeathers(String url, String cityName){
		ArrayList<Weather> weathers = new ArrayList<>();
		String result = null;
		try {
			 result = HttpRequestUtils.doGet(url);
		} catch (Exception e) {
			System.err
					.println("Error: while get forcast data from url \"" + url + "\"@" + e);
		}
		if (result != null) {
			JSONObject whole = JSONObject.fromObject(result);
			JSONArray list = whole.getJSONArray(JSONConstants.KEY_LIST);
			@SuppressWarnings("unchecked")
			Iterator<JSONObject> iterator = list.iterator();
			while (iterator.hasNext()) {
				Weather weather = parserWeather(iterator.next());
				if (weather != null) {
					weather.setCityName(cityName);
					weather.setType(WeatherType.TYPE_FORCASR.getDesc());
					weathers.add(weather);
				}
			}
		}
		return weathers;
	}
	
	private String generateURL(String lon, String lat, WeatherType type){
		StringBuffer body = new StringBuffer("lat=" +lat);
		body.append(CONNECTOR);
		body.append("lon=" + lon);
		return getURL(type, body);
	}

	private String generateURL(String cityName, WeatherType type){
		StringBuffer body = new StringBuffer("q=");
		body.append(cityName);
		return getURL(type, body);
	}
	
	private String getURL(WeatherType type, StringBuffer body){
		StringBuffer urlBuffer = new StringBuffer(URL_BASE);
		urlBuffer.append(type.desc);
		urlBuffer.append(ENDMARK);
		urlBuffer.append(body);
		urlBuffer.append(CONNECTOR);
		urlBuffer.append("appid=").append(appid);
		return urlBuffer.toString();
	}
	
	private Weather parserWeather(JSONObject object) {
		if (object == null || !object.containsKey(JSONConstants.KEY_MAIN)) {
			return null;
		}
		Weather weather = new Weather();
		weather.setDate(DateUtils.geTimestamp());

		JSONObject mainObject = object.getJSONObject(JSONConstants.KEY_MAIN);

		String temp = mainObject.getString(JSONConstants.KEY_TEMP);
		weather.setTemp(temp);
		String pressure = mainObject.getString(JSONConstants.KEY_PRESSURE);
		weather.setPressure(pressure);
		String humidity = mainObject.getString(JSONConstants.KEY_HUMIDITY);
		weather.setHumidity(humidity);

		JSONObject windObject = object.getJSONObject(JSONConstants.KEY_WIND);
		String windSpeed = windObject.getString(JSONConstants.KEY_WIND_SPEED);
		weather.setWindSpeed(windSpeed);

		if (object.containsKey(JSONConstants.KEY_RAIN)) {
			JSONObject rainObject = object.getJSONObject(JSONConstants.KEY_RAIN);
				if (rainObject.containsKey(JSONConstants.KEY_RAIN_3H)) {
					String rain = rainObject.getString(JSONConstants.KEY_RAIN_3H);
					weather.setRain(rain);
				}
		}

		if (object.containsKey(JSONConstants.KEY_CLOUDS)) {
			JSONObject cloudsObject = object.getJSONObject(JSONConstants.KEY_CLOUDS);
			String clouds = cloudsObject.getString(JSONConstants.KEY_CLOUDS_ALL);
			weather.setClouds(clouds);
		}
		return weather;
	}

	public enum WeatherType {
		TYPE_FORCASR("forecast"), TYPE_CURRENT("weather"), TYPE_HISTORY("history");

		private String desc;

		WeatherType(String desc) {
			this.desc = desc;
		}

		public String getDesc() {
			return desc;
		}
	}
	
	private class JSONConstants{
		static final String KEY_LIST = "list";
		static final String KEY_MAIN = "main";
		static final String KEY_TEMP = "temp";
		static final String KEY_RAIN = "rain";
		static final String KEY_RAIN_3H = "3h";
		static final String KEY_CLOUDS = "clouds";
		static final String KEY_CLOUDS_ALL = "all";
		static final String KEY_HUMIDITY = "humidity";
		static final String KEY_PRESSURE = "pressure";
		static final String KEY_WIND = "wind";
		static final String KEY_WIND_SPEED = "speed";
	}
}
