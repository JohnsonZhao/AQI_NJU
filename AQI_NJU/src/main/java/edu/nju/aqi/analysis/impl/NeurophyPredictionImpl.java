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
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.DynamicBackPropagation;
import org.neuroph.util.TransferFunctionType;


import edu.nju.aqi.analysis.IPrediction;
import edu.nju.aqi.analysis.helper.DataFactory;
/**
 * 使用神经网络训练预测
 * @author: margine
 * @time: 2016年4月13日
 */
public class NeurophyPredictionImpl implements IPrediction {
	private double error;

	private int layer;
	private MultiLayerPerceptron network;
	private boolean isTrained = false;
	private DataFactory dataFactory;
	
	public void setDataFactory(DataFactory dataFactory){
		this.dataFactory = dataFactory;
	}
	
	public DataFactory getDataFactory(){
		return dataFactory;
	}
	public NeurophyPredictionImpl() {
		this.error = 0.1;
		this.layer = 4;
	}


	@Override
	public void setError(double error) {
		this.error = error;
	}

	public void setLayer(int layer) {
		this.layer = layer;
	}

	@Override
	public List<Map<String, Object>> execute(String cityName) {
		doTrain(cityName);
		while (isTrained) {
			return doPredict(cityName);
		}
		return null;
	}

	@Override
	public void doTrain(String cityName) {
		int inputDim = dataFactory.getInputDim();
		int outputDim =dataFactory.getOutputDim();
		TrainingSet trainingSet = dataFactory.getTrainingData(cityName);	

		network = new MultiLayerPerceptron(TransferFunctionType.TRAPEZOID, inputDim, layer, outputDim);
		DynamicBackPropagation train = new DynamicBackPropagation();

		train.setNeuralNetwork(network);
		network.setLearningRule(train);
		
		Vector<TrainingElement> elements = trainingSet.trainingElements();
		Iterator<TrainingElement> iterator = elements.iterator();
		while (iterator.hasNext()) {
			SupervisedTrainingElement element = (SupervisedTrainingElement) iterator.next();
			Vector<Double> inputs = element.getInput();
			for(Double one: inputs){
				System.err.print(one+"|");
			}
			System.err.println("");
			Vector<Double> outputs = element.getDesiredOutput();
			System.err.println(outputs.get(0));
			
		}

		int epoch = 1;
		do {
			train.doLearningEpoch(trainingSet);
			System.out.println("Epoch " + epoch + ", error = " + train.getTotalNetworkError());
			epoch++;
		} while (train.getTotalNetworkError() > error);

		isTrained = true;
	}

	@Override
	public List<Map<String, Object>> doPredict(String cityName) {
		if (!isTrained) {
			throw new RuntimeException("no training!");
		}
		ArrayList<Map<String, Object>> results = new ArrayList<Map<String, Object>>(14);
		TrainingSet realSet = dataFactory.getRealData(cityName);
		for (TrainingElement element : realSet.trainingElements()) {
			network.setInput(element.getInput());
			network.calculate();
			Vector<Double> actualOutput = network.getOutput();

			Map<String, Object> oneday = new HashMap<String, Object>();
			String[] outputNames = dataFactory.getOutputPropertyNames();
			for (int i = 0; i < outputNames.length; i++) {
				oneday.put(outputNames[i], actualOutput.get(i));
			}
			results.add(oneday);
		}
		return results;
		
	}
	
}
