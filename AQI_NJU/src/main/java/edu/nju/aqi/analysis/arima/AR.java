package edu.nju.aqi.analysis.arima;

import java.util.Vector;

/**
 * AR模型
 * @author: margine
 * @time: 2016年4月13日
 */
public class AR {

	double[] stdoriginalData={};
	int p;
	ARMAMath armamath=new ARMAMath();
	


	/**
	 * AR模型
	 * @param stdoriginalData
	 * @param p MA模型的阶数
	 */
	public AR(double [] stdoriginalData,int p)
	{
		this.stdoriginalData=stdoriginalData;
		this.p=p;
	}
	
	/**
	 * 返回AR模型参数
	 * @return
	 */
	public Vector<double[]> ARmodel()
	{
		Vector<double[]> v=new Vector<double[]>();
		v.add(armamath.parcorrCompute(stdoriginalData, p, 0));
		return v;
	}
}
