package edu.nju.aqi.analysis.helper;

import edu.nju.aqi.analysis.AbstractCorrelation;

/**
 * 区域指数比较，默认以AQI作为比较基准
 * @author: margine
 * @time: 2016年4月15日
 */
public class IndexCorrelation extends AbstractCorrelation{
	/*基准指数名称*/
	private String baseKey;
	/*比较指数名称*/
	private String compareKey;
	/**
	 * 关联程度
	 */
	public IndexCorrelation(String baseKey, String compareKey, double correlation) {
		this.baseKey = baseKey;
		this.compareKey = compareKey;
		this.correlation = correlation;
		setCorrealtion(correlation);
	}
	
	/**
	 * 获取比较的基准属性，默认为AQI
	 * @return
	 */
	public String getBaseKey(){
		return this.baseKey;
	}
	
	/**
	 * 获取参与比较的属性，如SO2，NO2，CO等属性
	 * @return
	 */
	public String getCompareKey(){
		return this.compareKey;
	}

	@Override
	public String getDesc() {
		return this.desc;
	}
	
	@Override
	public String toString(){
		return this.desc;
	}
	
}
