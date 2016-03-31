package edu.nju.aqi.analysis.helper;

public class Degree {
    private String desc;
    private double actualNum;
    public Degree(double actualNum){
        setActualNum(actualNum);
    }
    
    public String getDesc() {
    	return desc;
	}
    
    public double getActualNum(){
    	return actualNum;
    }
    
    public void setActualNum(double actualNum) {
		this.actualNum = actualNum;
		if (Double.compare(actualNum, 0.2) <= 0) {
			this.desc = "0.0 - 0.2：极弱相关";
		}
		else if (Double.compare(actualNum, 0.2) > 0 && Double.compare(actualNum, 0.4) <= 0) {
			this.desc = "0.2 - 0.4：弱相关";
		}
		else if (Double.compare(actualNum, 0.4) > 0 && Double.compare(actualNum, 0.6 ) <=0) {
			this.desc = "0.4 - 0.6：中等强度相关";
		}
		else if (Double.compare(actualNum, 0.6) > 0 && Double.compare(actualNum, 0.8) <= 0) {
			this.desc = "0.6 - 0.8：强相关";
		}
		else if(Double.compare(actualNum, 0.8) > 0){
			this.desc = "0.8 - 1.0：极强相关";
		}
	}
    @Override
    public String toString() {
        return this.desc;
    }
    
}
