package com.project.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
	
	public static String fechaString(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy"); 
		String currentData = sdf.format(date); 
		return currentData;
	}
	
	public static String horaString(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm"); 
		String currentData = sdf.format(date); 
		return currentData;
}
	
	public static Date parseDate(String date) {
	     try {
	         return new SimpleDateFormat("dd-MM-yyyy").parse(date);
	     } catch (ParseException e) {
	         return null;
	     }
	  }
	
	public static Date parseDateTime(String date) {
	     try {
	         return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(date);
	     } catch (ParseException e) {
	         return null;
	     }
	  }

}
