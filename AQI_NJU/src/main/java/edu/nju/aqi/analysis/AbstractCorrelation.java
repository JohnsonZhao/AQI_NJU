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
		if (Double.compare(correlation, 0.2) <= 0) {
			correlationType = CorrelationType.VERY_WEAK;
			this.desc +=" 0.0 - 0.2：极弱相关";
		}
		else if (Double.compare(correlation, 0.2) > 0 && Double.compare(correlation, 0.4) <= 0) {
			correlationType = CorrelationType.WEAK;
			this.desc += "0.2 - 0.4：弱相关";
		}
		else if (Double.compare(correlation, 0.4) > 0 && Double.compare(correlation, 0.6 ) <=0) {
			correlationType = CorrelationType.MEDIUM;
			this.desc += "0.4 - 0.6：中等强度相关";
		}
		else if (Double.compare(correlation, 0.6) > 0 && Double.compare(correlation, 0.8) <= 0) {
			correlationType = CorrelationType.STRONG;
			this.desc += "0.6 - 0.8：强相关";
		}
		else if(Double.compare(correlation, 0.8) > 0){
			correlationType = CorrelationType.VERY_STRONG;
			this.desc += "0.8 - 1.0：极强相关";
		}
		this.desc += ",实际关联系数为：" + correlation;
	}

	 protected enum CorrelationType{
	    	VERY_WEAK,
	    	WEAK,
	    	MEDIUM,
	    	STRONG,
	    	VERY_STRONG
	    }
}
