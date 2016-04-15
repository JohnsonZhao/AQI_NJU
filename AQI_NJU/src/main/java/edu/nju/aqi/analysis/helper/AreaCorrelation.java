package edu.nju.aqi.analysis.helper;

import edu.nju.aqi.analysis.AbstractCorrelation;

/**
 * 区域间指数比较
 * @author: margine
 * @time: 2016年4月15日
 */
public class AreaCorrelation extends AbstractCorrelation{
	/*相似度阈值*/
	private static final double THRESHOLD = 0.5;
    private String desc;
    private double similar;
    private SimilarType similarType;
    
    public AreaCorrelation(double similar, double correaltion){
    	setSimilar(similar);
        setCorrealtion(correaltion);
    }
    
    public String getDesc() {
    	return desc;
	}
    
    public double getSimilar(){
    	return similar;
    }
    
    public SimilarType getSimilarType(){
    	return similarType;
    }
    
    private void setSimilar(double similar){
    	this.similar = similar;
    	if (Double.compare(similar, THRESHOLD) >= 0 || Double.compare(similar, -THRESHOLD) <= 0) {
			similarType = SimilarType.NOT_SIMILAR;
			this.desc = "-0.5 ~ 0.5 相似度高\n";
		}
    	else {
			similarType = SimilarType.SIMILAR;
			this.desc ="相似度低\n";
		}
    }
    @Override
    public String toString() {
        return this.desc;
    }
    
    enum SimilarType{
    	SIMILAR,
    	NOT_SIMILAR
    }
}
