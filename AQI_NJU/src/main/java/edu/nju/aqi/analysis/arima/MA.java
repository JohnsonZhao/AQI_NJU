package edu.nju.aqi.analysis.arima;

import java.util.Vector;

/**
 * MA模型
 * @author: margine
 * @time: 2016年4月13日
 */
public class MA {
	double[] stdoriginalData={};
	int q;
	ARMAMath armamath=new ARMAMath();
	
	/**
	 * MA模型
	 * @param stdoriginalData 预处理后的数据
	 * @param q MA模型的阶数
	 */
	public MA(double [] stdoriginalData,int q)
	{
		this.stdoriginalData=stdoriginalData;
		this.q=q;
	}
	
	/**
	 * 返回MA模型的参数
	 * @return
	 */
	public Vector<double[]> MAmodel()
	{
		Vector<double[]> v=new Vector<double[]>();
		v.add(armamath.getMApara(armamath.autocorData(stdoriginalData,q), q));
		return v;
	}
		
}
