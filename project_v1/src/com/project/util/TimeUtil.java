package com.project.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
	
	public String fechaString(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy"); 
		String currentData = sdf.format(date); 
		return currentData;
	}
	
	public String horaString(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm"); 
		String currentData = sdf.format(date); 
		return currentData;
}

}
