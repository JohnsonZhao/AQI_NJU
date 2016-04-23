package edu.nju.aqi.analysis;


public abstract class AbstractCorrelation {
	protected double correlation;
	protected CorrelationType correlationType;
	protected String desc="";
    
    /**
     * 获取关联程度描述
     * @return
     */
    public abstract String getDesc();
    

	/**
	 * 获取关联程度实际数值
	 * @return
	 */
	public double getCorrealtion(){
		return correlation;
	}
	/**
	 * 获取关联程度枚举值
	 * @return
	 */
	public CorrelationType getDegreeType(){
		return correlationType;
	}
	
	public void setCorrealtion(double correlation) {
		this.correlation = correlation;
		StringBuffer buffer = new StringBuffer();
		buffer.append("使用皮尔逊相关性分析，关联性指数为：");
		buffer.append(correlation);
		buffer.append("\n");
		buffer.append("关联程度评价为：");
		if (Double.compare(correlation, 0.2) <= 0) {
			correlationType = CorrelationType.VERY_WEAK;
			buffer.append("0.0 - 0.2，极弱相关");
		}
		else if (Double.compare(correlation, 0.2) > 0 && Double.compare(correlation, 0.4) <= 0) {
			correlationType = CorrelationType.WEAK;
			buffer.append("0.2 - 0.4，弱相关");
		}
		else if (Double.compare(correlation, 0.4) > 0 && Double.compare(correlation, 0.6 ) <=0) {
			correlationType = CorrelationType.MEDIUM;
			buffer.append("0.4 - 0.6，中等强度相关");
		}
		else if (Double.compare(correlation, 0.6) > 0 && Double.compare(correlation, 0.8) <= 0) {
			correlationType = CorrelationType.STRONG;
			buffer.append("0.6 - 0.8，强相关");
		}
		else if(Double.compare(correlation, 0.8) > 0){
			correlationType = CorrelationType.VERY_STRONG;
			buffer.append("0.8 - 1.0，极强相关");
		}
		buffer.append("\n");
		this.desc += buffer.toString();
	}
	
	@Override
	public String toString(){
		return this.desc;
	}

	 protected enum CorrelationType{
	    	VERY_WEAK,
	    	WEAK,
	    	MEDIUM,
	    	STRONG,
	    	VERY_STRONG
	    }
}
