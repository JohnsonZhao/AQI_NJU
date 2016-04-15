package edu.nju.aqi.meta;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CityUtils {

	/*public static final String NANJING = "nanjing";
	public static final String BEIJING = "beijing";
	public static final String SHANGHAI = "shanghai";
	public static final String GUANGZHOU = "guangzhou";
	public static final String SHENZHEN = "shenzhen";
	public static final String SUZHOU = "suzhou";
	public static final String HAIKOU = "haikou";
	public static final String LASA = "lasa";
	public static final String WULUMUQI = "wulumuqi";*/
	
	public static List<String> getCities(){
		List<String> cities = new ArrayList<String>();
		try {
            // read cities.txt from classpath
            String encoding="utf8";
            InputStream inputStream = CityUtils.class.getResourceAsStream("/datasource/cities.txt");
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, encoding);
                BufferedReader reader = new BufferedReader(inputStreamReader);
                try {
                    String lineTxt;
                    while ((lineTxt = reader.readLine()) != null) {
                        cities.add(lineTxt.trim().split(" ")[0]);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    reader.close();
                }
            }
//            File file=new File("/cities.txt");
//            if(file.isFile() && file.exists()){ //判断文件是否存在
//                InputStreamReader read = new InputStreamReader(new FileInputStream(file),encoding);//考虑到编码格式
//                BufferedReader bufferedReader = new BufferedReader(read);
//                String lineTxt = null;
//                while((lineTxt = bufferedReader.readLine()) != null){
//                    cities.add(lineTxt.trim());
//                }
//                read.close();
//            }else{
//            	System.out.println("找不到指定的文件!");
//            }
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}
		return cities;
	}
}
