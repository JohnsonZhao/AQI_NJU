package edu.nju.aqi.analysis;

import java.util.List;
import java.util.Map;

public interface IPrediction {
	
	/**
	 * 模型生成+获取预测结果
	 * @param cityName
	 * @return
	 */
	public List<Map<String, Object>> execute(String cityName);

	/**
	 * 模型生成
	 */
    public void doTrain(String cityName);

    /**
     * 获取预测结果
     * Note：前置条件是模型已经生成（已用历史数据训练过）
     * @param cityName
     * @return
     */
    public List<Map<String, Object>> doPredict(String cityName);

    /**
     * 设定误差范围
     * @param error
     */
    public void setError(double error);

}
