package edu.nju.aqi.meta;

public class IndexTypeUtils {

	/*
	 * 0-50 ��һ������
	 * 51-100 ����������
	 */
	private static String[] types={"һ�����ţ�","����������","�����������Ⱦ��","�ļ����ж���Ⱦ��","�弶���ض���Ⱦ��","������������Ⱦ��"};
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
