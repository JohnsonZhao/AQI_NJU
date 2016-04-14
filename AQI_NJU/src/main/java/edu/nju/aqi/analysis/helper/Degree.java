package edu.nju.aqi.analysis.helper;

public class Degree {
	/*相似度阈值*/
	private static final double THRESHOLD = 0.5;
    private String desc;
    private double similar;
    private double correlation;
    private CorrelationType degreeType;
    private SimilarType similarType;
    
    public Degree(double similar, double correaltion){
        setCorrealtion(correaltion);
    }
    
    public String getDesc() {
    	return desc;
	}
    
    public double getSimilar(){
    	return similar;
    }
    
    public double getCorrealtion(){
    	return correlation;
    }
    public CorrelationType getDegreeType(){
    	return degreeType;
    }
    
    public SimilarType getSimilarType(){
    	return similarType;
    }
    
    public void setSimilar(double similar){
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
    public void setCorrealtion(double correlation) {
		this.correlation = correlation;
		if (Double.compare(correlation, 0.2) <= 0) {
			degreeType = CorrelationType.VERY_WEAK;
			this.desc += "0.0 - 0.2：极弱相关\n";
		}
		else if (Double.compare(correlation, 0.2) > 0 && Double.compare(correlation, 0.4) <= 0) {
			degreeType = CorrelationType.WEAK;
			this.desc += "0.2 - 0.4：弱相关\n";
		}
		else if (Double.compare(correlation, 0.4) > 0 && Double.compare(correlation, 0.6 ) <=0) {
			degreeType = CorrelationType.MEDIUM;
			this.desc += "0.4 - 0.6：中等强度相关\n";
		}
		else if (Double.compare(correlation, 0.6) > 0 && Double.compare(correlation, 0.8) <= 0) {
			degreeType = CorrelationType.STRONG;
			this.desc += "0.6 - 0.8：强相关\n";
		}
		else if(Double.compare(correlation, 0.8) > 0){
			degreeType = CorrelationType.VERY_STRONG;
			this.desc = "0.8 - 1.0：极强相关";
		}
	}
    @Override
    public String toString() {
        return this.desc;
    }
    
    enum CorrelationType{
    	VERY_WEAK,
    	WEAK,
    	MEDIUM,
    	STRONG,
    	VERY_STRONG
    }
    
    enum SimilarType{
    	SIMILAR,
    	NOT_SIMILAR
    }
    
}
