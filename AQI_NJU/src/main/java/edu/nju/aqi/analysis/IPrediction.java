package edu.nju.aqi.analysis;

import java.util.List;
import java.util.Map;

public interface IPrediction {
	
	/**
	 * 
	 * @param trainingData 训练数据
	 * @param realData 真实数据输入
	 */
	public List<Map<String, Double>> execute(String cityName);

	/**
	 * 
	 * @param trainingData 训练数据
	 */
    public void doTrain();

    /**
     * 输入真实数据，使用模型进行求解预测
     * @param cityName
     */
    public List<Map<String, Double>> doPredict(String cityName);

    /**
     * 设定误差范围
     * @param error
     */
    public void setError(double error);

    public void setLayer(int layer);
}
