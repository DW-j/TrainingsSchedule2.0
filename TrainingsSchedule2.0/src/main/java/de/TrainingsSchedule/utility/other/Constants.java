package de.TrainingsSchedule.utility.other;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class Constants {

	private static final String dateFormatString = "dd-MM-yyyy";
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatString);
	private static final SimpleDateFormat durationMinutesFormat = new SimpleDateFormat("mm:ss");
	private static final SimpleDateFormat durationHoursFormat = new SimpleDateFormat("HHH:mm:ss");
	
	public static SimpleDateFormat getDateformat() {
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
		return dateFormat;
	}
	public static SimpleDateFormat getDurationminutesformat() {
		durationMinutesFormat.setTimeZone(TimeZone.getTimeZone("GMT"));;
		return durationMinutesFormat;
	}
	public static SimpleDateFormat getDurationhoursformat() {
		durationHoursFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
		return durationHoursFormat;
	}
	
	
}