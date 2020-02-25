package de.TrainingsSchedule.utility.text;

public class Message {

	public static String getEnterMessage(String item) {
		return String.format("Please enter %s.", item);
	}
	
	public static String getEnterMessage(String item, String subitem1) {
		return String.format("Please enter %s of the %s.", item, subitem1);
	}
	
	public static String getEnterMessage(String item, String subitem, int subitemNumber) {
		return String.format("Please enter %s of the %d. %s.", item, subitemNumber, subitem);
	}
	
	public static String getEnterMessage(String item, String subitem1, int subitem1Number, String subitem2, int subitem2Number) {
		return String.format("Please enter %s of the %d. %s of the %d. %s.", item, subitem1Number, subitem1, subitem2Number, subitem2);
	}
	
	public static String getEnterMessage(String item, String subitem, String additionalMessage) {
		return String.format("Please enter %s of the %s. %s", item, subitem, additionalMessage);	
	}
	
	public static String getEnterMessage(String item, String subitem, int subitemNumber, String additionalMessage) {
		return String.format("Please enter %s of the %d. %s. %s", item, subitemNumber, subitem, additionalMessage);
	}
	
	public static String getEnterMessageAction(String subItemType, String item, String action) {
		return String.format("Please enter the %s of the %s to %s it.", subItemType, item, action);
	}
	
	public static String getRepeatingMessage() {
		return "Repeating process.";
	}
	
	public static String getWrongInputMessage() {
		return "Wrong input. "+getRepeatingMessage();
	}
	
	public static String getNoDataMessage() {
		return "No data existing.";
	}
	
	public static String getGoaltypeDescription() {
		return "Goaltype 1 describes an increase in weight, goaltype 2 describes an increase in reps / time.";
	}	
	
}