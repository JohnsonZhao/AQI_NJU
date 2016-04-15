package edu.nju.aqi.analysis.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;


import org.neuroph.core.learning.SupervisedTrainingElement;
import org.neuroph.core.learning.TrainingElement;
import org.neuroph.core.learning.TrainingSet;

import edu.nju.aqi.analysis.IPrediction;
import edu.nju.aqi.analysis.helper.DataFactory;

/**
 * 使用多元线性回归进行预测
 * 
 * @author: margine
 * @time: 2016年4月9日
 */
public class LineRegressionPredict implements IPrediction {
	private boolean isTrained;
	/* 训练数据集 */
	private double[][] trainingData;
	/* 训练数据的组数 */
	private int row;
	private int column;
	/* 参数列表 */
	private double[] theta;
	/* 训练步长 */
	private double alpha;
	/* 迭代次数 */
	private int iteration;
	
	private String cityName;

	private DataFactory dataFactory;

	public void setDataFactory(DataFactory dataFactory) {
		this.dataFactory = dataFactory;
	}

	public DataFactory getDataFactory() {
		return dataFactory;
	}

	public LineRegressionPredict() {
		this.alpha = 0.001;
		this.iteration = 1000;
		isTrained = false;
	}

	@Override
	public List<Map<String, Object>> execute(String cityName) {
		this.cityName = cityName;
		doTrain(cityName);
		while (isTrained) {
			return doPredict(cityName);
		}
		return null;
	}

	@Override
	public void doTrain(String cityName) {
		initTrainingData();
		initTheta();
		for(int i = 0 ; i< row; i++){
			for(int j = 0; j < column; j++){
				System.out.print(trainingData[i][j] +"|");
			}
			System.out.println("");
		}
		int count = 0;
		while ((count++) < iteration) {
			System.out.println(count );
			double[] partialDerivative = computePartialDerivative();
			int len = theta.length;
			for (int i = 0; i < len; i++) {
				theta[i] -= alpha * partialDerivative[i];
			}
			printTheta();
		}
		isTrained = true;
	}

	@Override
	public List<Map<String, Object>> doPredict(String cityName) {
		if (!isTrained) {
			throw new RuntimeException("no training");
		}
		TrainingSet set = dataFactory.getRealData(cityName);
		Vector<TrainingElement> elements = set.trainingElements();
		Iterator<TrainingElement> iterator = elements.iterator();
		List<Map<String, Object>> results = new ArrayList<>(set.size());
		while(iterator.hasNext()){
			TrainingElement element = iterator.next();
			double value = 0.0;
			value += theta[0] * 1.0;
			Vector<Double> inputs = element.getInput();
			for(int i = 0; i< inputs.size(); i++){
				value += theta[i+1]* inputs.get(i);
			}
			Map<String, Object> map = new HashMap<>(1);
			map.put(dataFactory.getOutputPropertyNames()[0], value);
			results.add(map);
		}
		printTheta();
		return results;
				
		
	}

	@Override
	public void setError(double error) {
	}

	/**
	 * 训练数据示例：
 *   x0        x1        x2        y 
    1.0       1.0       2.0       7.2 
    1.0       2.0       1.0       4.9 
    1.0       3.0       0.0       2.6 
    1.0       4.0       1.0       6.3 
    1.0       5.0      -1.0       1.0 
    1.0       6.0       0.0       4.7 
    1.0       7.0      -2.0      -0.6 
    注意！！！！x1，x2，y三列是用户实际输入的数据，x0是为了推导出来的公式统一，特地补上的一列。
    x0,x1,x2是“特征”，y是结果
    
    h(x) = theta0 * x0 + theta1* x1 + theta2 * x2
    
    theta0,theta1,theta2 是想要训练出来的参数
    
               此程序采用“梯度下降法”
	 */
	private void initTrainingData() {
		TrainingSet set = dataFactory.getTrainingData(cityName);
		int inputDim = dataFactory.getInputDim();
		int outputDim = dataFactory.getOutputDim();
		row = set.size();
		column = inputDim + outputDim + 1;
		trainingData = new double[row][column];
		Vector<TrainingElement> elements = set.trainingElements();
		Iterator<TrainingElement> iterator = elements.iterator();
		int index = 0;
		while (iterator.hasNext()) {
			SupervisedTrainingElement element = (SupervisedTrainingElement) iterator.next();
			Vector<Double> inputValues = element.getInput();
			trainingData[index][0] = 1.0;
			for(int i = 0; i< inputDim;i++){
				trainingData[index][i+1] = inputValues.get(i);
			}
			Vector<Double> outputValues = element.getDesiredOutput();
			trainingData[index][column - 1] = outputValues.get(0);
			index ++;
		}
	}

	private void initTheta() {
		theta = new double[column-1];
		for (int i = 0; i < theta.length; i++) {
			theta[i] = 1.0;
		}
	}
	
	private void printTheta(){
		for(int i = 0; i < theta.length; i++){
			System.out.print(theta[i] + "||");
		}
		System.out.println("");
	}

	/**
	 * 对每个参数求偏导
	 * 
	 * @return
	 */
	private double[] computePartialDerivative() {
		double[] patialDerivative = new double[theta.length];
		for (int i = 0; i < theta.length; i++) {
			patialDerivative[i] = computePartialDerivative4Theta(i);
		}
		return patialDerivative;
	}

	/**
	 * 对参数求偏导
	 * 
	 * @param index
	 * @return
	 */
	private double computePartialDerivative4Theta(int index) {
		double sum = 0.0;
		for (int i = 0; i < row; i++) {
			sum += h_theta_x_i_minus_y_i_times_x_j_i(i, index);
		}
		return sum / row;
	}

	private double h_theta_x_i_minus_y_i_times_x_j_i(int i, int j) {
		double[] oneRow = trainingData[i];
		double result = 0.0;

		for(int k=0;k< (oneRow.length-1);k++)
            result+=theta[k]*oneRow[k];
        result-=oneRow[oneRow.length-1];
        result*=oneRow[j];
        return result;
	}

}
