package edu.nju.aqi.analysis.helper;

import edu.nju.aqi.analysis.AbstractCorrelation;

/**
 * 区域指数比较程度
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
	
	public String getCompareKey(){
		return this.compareKey;
	}

	@Override
	public String getDesc() {
		return this.desc;
	}
	
	public String toString(){
		return this.desc;
	}
	
}
