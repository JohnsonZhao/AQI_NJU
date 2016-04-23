package edu.nju.aqi.analysis.helper;

import edu.nju.aqi.analysis.AbstractCorrelation;

/**
 * 区域间指数比较<br/>
 * 以时间为轴，对AQI进行比较获取关联程度<br/>
 * 对区域内的多个指数进行相似度分析获取相似程度
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
    
    /**
     * 获取区域间关联性分析的描述
     * @return
     */
    public String getDesc() {
    	return desc;
	}
    
    /**
     * 相似度值，判断相似的阈值为-0.5-0.5
     * @return
     */
    public double getSimilar(){
    	return similar;
    }
    
    /**
     * 相似程度枚举类型描述
     * @return
     */
    public SimilarType getSimilarType(){
    	return similarType;
    }
    
    private void setSimilar(double similar){
    	StringBuffer buffer = new StringBuffer();
    	buffer.append("城市间指数的相似度为：");
    	buffer.append(similar);
    	buffer.append("\n");
		buffer.append("相似度阈值为：");
		buffer.append("\n");
		buffer.append(THRESHOLD);
		buffer.append("相似度评价为：");
    	this.similar = similar;
    	if (Double.compare(similar, THRESHOLD) >= 0 || Double.compare(similar, -THRESHOLD) <= 0) {
			similarType = SimilarType.SIMILAR;
			buffer.append("相似度高");
			buffer.append("\n");
			this.desc += buffer.toString();
		}
    	else {
			similarType = SimilarType.NOT_SIMILAR;
			buffer.append("相似度低");
			buffer.append("\n");
			this.desc +=buffer.toString();
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
