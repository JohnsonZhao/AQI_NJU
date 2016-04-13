package edu.nju.aqi.analysis.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.nju.aqi.analysis.IPrediction;
import edu.nju.aqi.analysis.arima.ARIMA;
import edu.nju.aqi.analysis.helper.DataFactory;

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
	private String key = "aqi";
	private int[] result;
	private DataFactory dataFactory;
	
	public void setDataFactory(DataFactory dataFactory) {
		this.dataFactory = dataFactory;
	}
	public DataFactory getDataFacotory(){
		return this.dataFactory;
	}
	
	@Override
	public List<Map<String, Double>> execute(String cityName) {
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
		ARIMA arima = new ARIMA(rowData, PERIOD);
		result = arima.getPredictValues();
		isTrained = true;
	}

	@Override
	public List<Map<String, Double>> doPredict(String cityName) {
		List<Map<String, Double>> predictValues = new ArrayList<Map<String,Double>>(PERIOD);
		for(int i= 0; i<result.length; i++){
			Map<String, Double> map = new HashMap<>(1);
			map.put(key, (double)result[i]);
			predictValues.add(map);
		}
		return predictValues;
	}

	@Override
	public void setError(double error) {
	}

}
