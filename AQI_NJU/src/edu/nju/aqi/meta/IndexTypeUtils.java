package edu.nju.aqi.meta;

public class IndexTypeUtils {

	/*
	 * 0-50 （一级）优
	 * 51-100 （二级）良
	 */
	private static String[] types={"一级（优）","二级（良）","三级（轻度污染）","四级（中度污染）","五级（重度污染）","六级（严重污染）"};
	public static String indexType(String aqi){
		String type = "unknown";
		int aqiInt = Integer.parseInt(aqi);
		if(aqiInt>0&&aqiInt<=50){
			return types[0];
		}else if(aqiInt>50&&aqiInt<=100){
			return types[1];
		}else if(aqiInt>100&&aqiInt<=150){
			return types[2];
		}else if(aqiInt>150&&aqiInt<=200){
			return types[3];
		}else if(aqiInt>200&&aqiInt<=300){
			return types[4];
		}else if(aqiInt>300){
			return types[5];
		}else
			return type;
	}
}
