package edu.nju.aqi.meta;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	
	public static final String FORMAT_DEFAULT = "yyyy_MM_dd_HH";
	public static final String FORMAT_DAY = "yyyy_MM_dd";

	public static String getDayStr(){
		return getDateStr(FORMAT_DAY);
	}
	
	public static String getDateStr(){
		return getDateStr(FORMAT_DEFAULT);
	}
	
	public static String getDateStr(Date date){
		SimpleDateFormat format = new SimpleDateFormat(FORMAT_DEFAULT);
		return format.format(date);
	}
	
	public static String getDateStr(String formatStr){
		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		Date date = new Date();
		String dateStr = format.format(date);
		return dateStr;
	}
	
	/**
	 * convert UNIX timeStamp to Date String<br/>
	 * default format yyyy_MM_dd_HH 
	 * @param timestampString
	 * @return
	 */
	public static String TimeStamp2Date(String timestampString) {
		return TimeStamp2Date(timestampString, FORMAT_DEFAULT);
	}
	
	public static String TimeStamp2Date(String timestampString, String format) {
		Long timestamp = Long.parseLong(timestampString)*1000;
		String date = new SimpleDateFormat(format).format(new Date(timestamp));
		return date;
	}
	
	/**
	 * convert date to unix timeStamp
	 * @param date
	 * @return
	 */
	public static String Date2TimeStamp(Date date){
		return String.valueOf(date.getTime());
	}
	
	public static java.sql.Timestamp getTimestamp(){
		Date date = new Date();
		java.sql.Timestamp time = new java.sql.Timestamp(date.getTime());
		return time;
		
	}
}
