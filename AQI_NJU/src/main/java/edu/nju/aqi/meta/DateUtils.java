package edu.nju.aqi.meta;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

	public static String getDateStr(){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String dateStr = format.format(date);
		String newString = dateStr.split("-")[0]+"_"+dateStr.split("-")[1]+"_"+dateStr.split("-")[2];
		return newString;
	}
}
