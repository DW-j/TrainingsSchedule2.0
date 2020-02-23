package de.TrainingsSchedule.utility.other;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

import lombok.Getter;

public class TimeFormat {

	@Getter
	private static final String dateFormatString = "dd-MM-yyyy";
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatString);
	
	public static SimpleDateFormat getDateformat() {
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
		return dateFormat;
	}
	
	public static String minutesToHours(int minutes) {
		int hours = minutes / 60;
		minutes = minutes % 60;
		return String.format("%d:%d h", hours, minutes);
	}
	
	public static String secondsToMinutes(double seconds) {
		double minutes = seconds / 60;
		seconds = seconds % 60;
		return String.format("%d:%d min", (int)minutes, (int)seconds);
	}

	
	
}