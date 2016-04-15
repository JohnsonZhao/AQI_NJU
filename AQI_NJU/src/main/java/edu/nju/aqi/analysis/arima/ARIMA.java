package edu.nju.aqi.analysis.arima;

import java.util.Vector;

/**
 * ARIMA模型
 * @author: margine
 * @time: 2016年4月13日
 */
public class ARIMA {
	private int period;
	double[] originalData = {};
	double stderrDara = 0;
	double avgsumData = 0;
	ARMAMath armamath = new ARMAMath();

	Vector<double[]> armaARMAcoe = new Vector<double[]>();
	Vector<double[]> bestarmaARMAcoe = new Vector<double[]>();

	public ARIMA(double[] originalData, int period) {
		this.originalData = originalData;
		this.period = period;
	}
	
	/**
	 * 获取下一个Period的预测值集合
	 * @return
	 */
	public int[] getPredictValues(){
		int[] model = getARIMAmodel();
		double predictValue = predictValue(model[0], model[1]);
		int[] results = new int[period];
		int len = originalData.length;
		double prefixValue = predictValue * avgsumData + avgsumData;
		for(int i = 0; i< period; i++){
			//对结果进行反差分处理
			results[i] = (int) (prefixValue + originalData[len - period + i-1]);
		}
		return results;
	}

	/**
	 * 预处理：差分处理,Z-score归一化
	 * 
	 * @return
	 */
	private double[] preDeal() {
		double[] tempData = new double[originalData.length - period];
		for (int i = 0; i < originalData.length - period; i++) {
			tempData[i] = originalData[i + period] - originalData[i];
		}

		// Z-Score
		avgsumData = armamath.avgData(tempData);
		stderrDara = armamath.stderrData(tempData);

		for (int i = 0; i < tempData.length; i++) {
			tempData[i] = (tempData[i] - avgsumData) / stderrDara;
		}

		return tempData;
	}

	/**
	 * 获取ARIM模型
	 * @return
	 */
	private int[] getARIMAmodel() {
		double[] stdoriginalData = this.preDeal();// 预处理后得到的归一化数据
		int paraType = 0;
		double minAIC = 9999999;
		int bestModelindex = 0;
		int[][] model = new int[][] { { 0, 1 }, { 1, 0 }, { 1, 1 }, { 0, 2 }, { 2, 0 }, { 2, 2 }, { 1, 2 }, { 2, 1 } };
		// 对模型进行迭代，选出平均预测误差最小的模型作为使用模型
		for (int i = 0; i < model.length; i++) {

			if (model[i][0] == 0) {
				MA ma = new MA(stdoriginalData, model[i][1]);
				armaARMAcoe = ma.MAmodel(); // ma模型的参数
				paraType = 1;
			} else if (model[i][1] == 0) {
				AR ar = new AR(stdoriginalData, model[i][0]);
				armaARMAcoe = ar.ARmodel(); // mr模型的参数
				paraType = 2;
			} else {
				ARMA arma = new ARMA(stdoriginalData, model[i][0], model[i][1]);
				armaARMAcoe = arma.ARMAmodel();// mar模型的参数
				paraType = 3;
			}

			double temp = getmodelAIC(armaARMAcoe, stdoriginalData, paraType);
			if (temp < minAIC) {
				bestModelindex = i;
				minAIC = temp;
				bestarmaARMAcoe = armaARMAcoe;
			}
		}
		return model[bestModelindex];
	}

	/**
	 * 计算ARMA模型的AIC
	 * 
	 * @param para
	 *            装载模型的参数信息
	 * @param stdoriginalData
	 *            预处理过后的原始数据
	 * @param type
	 *            1.MA;2.AR;3.ARMA
	 * @return
	 */
	private double getmodelAIC(Vector<double[]> para, double[] stdoriginalData, int type) {
		double temp = 0;
		double temp2 = 0;
		double sumerr = 0;
		int p = 0;// ar1,ar2,...,sig2
		int q = 0;// sig2,ma1,ma2...
		int n = stdoriginalData.length;

		if (type == 1) {
			double[] maPara = para.get(0);
			q = maPara.length;
			double[] err = new double[q]; // error(t),error(t-1),error(t-2)...
			err[0] = Math.sqrt(maPara[0]);

			for (int k = q - 1; k < n; k++) {
				temp = 0;

				for (int i = 1; i < q; i++) {
					temp += maPara[i] * err[i];
				}

				// 产生各个时刻的噪声
				for (int j = q - 1; j > 0; j--) {
					err[j] = err[j - 1];
				}
				err[0] = stdoriginalData[k] - (err[0] - temp);
				// 估计的方差之和
				sumerr += err[0] * err[0];

			}
			return n * Math.log(sumerr / (n - (q - 1))) + (q) * Math.log(n);// AIC
																			// 最小二乘估计

		} else if (type == 2) {
			double[] arPara = para.get(0);
			p = arPara.length;
			for (int k = p - 1; k < n; k++) {
				temp = 0;
				for (int i = 0; i < p - 1; i++) {
					temp += arPara[i] * stdoriginalData[k - i - 1];
				}
				temp += Math.sqrt(arPara[p - 1]);
				// 估计的方差和
				sumerr += (stdoriginalData[k] - temp) * (stdoriginalData[k] - temp);
			}

			return n * Math.log(sumerr / (n - (p - 1))) + (p) * Math.log(n);// AIC
																			// 最小二乘估计
		} else {
			double[] arPara = para.get(0);
			double[] maPara = para.get(1);
			p = arPara.length;
			q = maPara.length;
			double[] err = new double[q]; // error(t),error(t-1),error(t-2)...
			err[0] = Math.sqrt(maPara[0]);

			for (int k = p - 1; k < n; k++) {
				temp = 0;
				temp2 = 0;
				for (int i = 0; i < p - 1; i++) {
					temp += arPara[i] * stdoriginalData[k - i - 1];
				}

				for (int i = 1; i < q; i++) {
					temp2 += maPara[i] * err[i];
				}

				for (int j = q - 1; j > 0; j--) {
					err[j] = err[j - 1];
				}
				err[0] = stdoriginalData[k] - (err[0] - temp2 + temp);
				sumerr += err[0] * err[0];
			}

			return n * Math.log(sumerr / (n - (p - 1))) + (p + q - 1) * Math.log(n);
		}
	}
	
	/**
	 * 进一步预测
	 * 
	 * @param p
	 *            ARMA模型的AR的阶数
	 * @param q
	 *            ARMA模型的MA的阶数
	 * @return
	 */
	private double predictValue(int p, int q) {
		double predict = 0;
		double[] stdoriginalData = this.preDeal();
		int n = stdoriginalData.length;
		double temp = 0, temp2 = 0;
		double[] err = new double[q];

		if (p == 0) {
			double[] maPara = bestarmaARMAcoe.get(0);

			for (int k = q - 1; k <= n; k++) {
				temp = 0;

				for (int i = 1; i < q; i++) {
					temp += maPara[i] * err[i];
				}

				// 产生各个时刻的噪声
				for (int j = q - 1; j > 0; j--) {
					err[j] = err[j - 1];
				}
				if (k == n)
					predict = (int) (err[0] - temp); // ����Ԥ��
				else
					err[0] = stdoriginalData[k] - (err[0] - temp);
			}
		} else if (q == 0) {
			double[] arPara = bestarmaARMAcoe.get(0);
			
			System.out.println("begin:");
			for(int i = 0; i< arPara.length; i++){
				System.out.println(arPara[i]);
			}

			for (int k = p - 1; k <= n; k++) {
				temp = 0;
				for (int i = 0; i < p - 1; i++) {
					temp += arPara[i] * stdoriginalData[k - i - 1];
				}
				double param0 = arPara[p-1];
				if (Double.compare(0.0, param0) >0) {
					temp -= Math.sqrt(-param0);
				}else{
					temp += Math.sqrt(param0);
				}
			}
			predict =  temp;

		} else {

			double[] arPara = bestarmaARMAcoe.get(0);
			double[] maPara = bestarmaARMAcoe.get(1);

			err = new double[q]; // error(t),error(t-1),error(t-2)...
			err[0] = Math.sqrt(maPara[0]);

			for (int k = p - 1; k <= n; k++) {
				temp = 0;
				temp2 = 0;
				for (int i = 0; i < p - 1; i++) {
					temp += arPara[i] * stdoriginalData[k - i - 1];
				}

				for (int i = 1; i < q; i++) {
					temp2 += maPara[i] * err[i];
				}

				for (int j = q - 1; j > 0; j--) {
					err[j] = err[j - 1];
				}
				if (k == n)
					predict = (int) (err[0] - temp2 + temp);
				else
					err[0] = stdoriginalData[k] - (err[0] - temp2 + temp);
			}
		}

		return predict;
	}

}
