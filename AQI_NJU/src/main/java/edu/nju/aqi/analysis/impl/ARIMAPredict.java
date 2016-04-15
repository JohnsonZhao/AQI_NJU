package edu.nju.aqi.analysis.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.nju.aqi.analysis.IPrediction;
import edu.nju.aqi.analysis.arima.ARIMA;
import edu.nju.aqi.analysis.helper.DataFactory;
import edu.nju.aqi.meta.DateUtils;

/**
 * 基于ARIMA模型，进行时间序列分析
 * @author: margine
 * @time: 2016年4月13日
 */
public class ARIMAPredict implements IPrediction{
	/**周期为24小时*/
	private final static int PERIOD = 24;
	private boolean isTrained = false;
	/**预测的属性*/
	private final static String KEY_AQI = "aqi";
	private final static String KEY_DATE = "date";
	private final static String KEY_CITYNAME = "city_name";
	private int[] result;
	/**今天的aqi数据*/
	private double[] todayRowData;
	private DataFactory dataFactory;
	private int mod;
	
	public void setDataFactory(DataFactory dataFactory) {
		this.dataFactory = dataFactory;
	}
	public DataFactory getDataFacotory(){
		return this.dataFactory;
	}
	
	@Override
	public List<Map<String, Object>> execute(String cityName) {
		doTrain(cityName);
		while(isTrained){
			return doPredict(cityName);
		}
		return null;
	}

	@Override
	public void doTrain(String cityName) {
		double[] rowData = dataFactory.getHistoryAqis(cityName);
		if (rowData.length < PERIOD) {
			throw new RuntimeException("training data is not enough, at least " + PERIOD + ", while actual num is " + rowData.length);
		}
		mod = rowData.length % PERIOD;
		todayRowData = new double[mod];
//		System.arraycopy(rowData, rowData.length - mod, todayRowData, 0, todayRowData.length);
		ARIMA arima = new ARIMA(rowData, PERIOD);
		result = arima.getPredictValues();
//		ajustWeight();
		isTrained = true;
	}

	@Override
	public List<Map<String, Object>> doPredict(String cityName) {
		List<Map<String, Object>> predictValues = new ArrayList<Map<String,Object>>(PERIOD);
		Calendar calendar = Calendar.getInstance();
	    calendar.setTime(new Date());
	    int hour =  calendar.get(Calendar.HOUR_OF_DAY);
	    /**
	     * 预报从下一小时开始的值
	     */
		for(int i= result.length - hour ; i< result.length; i++){
			Map<String, Object> map = new HashMap<>(1);
			map.put(KEY_AQI, (double)result[i]);
			calendar.add(Calendar.HOUR_OF_DAY, 1);
			map.put(KEY_CITYNAME, cityName);
			map.put(KEY_DATE, DateUtils.getDateStr(calendar.getTime()));
			predictValues.add(map);
		}
		/**
		 * 连接第二天的数据
		 */
		for(int i = 0; i< result.length - hour; i++){
			Map<String, Object> map = new HashMap<>(1);
			map.put(KEY_AQI, (double)result[i]);
			calendar.add(Calendar.HOUR_OF_DAY, 1);
			map.put(KEY_CITYNAME, cityName);
			map.put(KEY_DATE, DateUtils.getDateStr(calendar.getTime()));
			predictValues.add(map);
		}
		return predictValues;
	}

	@Override
	public void setError(double error) {
	}

	/**
	 * 根据预测值和实际值的比较进行调整
	 */
	private void ajustWeight(){
		if (todayRowData ==null || todayRowData.length == 0) {
			return;
		}
		double total = 0.0;
		for(int i = 0; i< todayRowData.length; i++){
			total += todayRowData[i]/(double)result[i];
		}
		double weight = total/todayRowData.length;
		for(int i = 0; i < result.length; i++){
			result[i] *= weight;
		}
	}
}
