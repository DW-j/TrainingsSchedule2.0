package de.TrainingsSchedule.utility.text;

import java.util.List;

public class ListConverter {

	private static ListConverter listConverter = null;
	
	private ListConverter() {}
	
	public static ListConverter getInstance() {
		if(listConverter==null) {
			listConverter = new ListConverter();
		}
		return listConverter;
	}
	
	public String stringListToString(List<String> list) {
		return list.toString().replace("[", "").replace("]", "");
	}
	
	public String integerListToString(List<Integer> list) {
		return list.toString().replace("[", "").replace("]", "");
	}
	
	public String listLists(List<String> list) {
		return String.format("\n%s\n", String.join("\n\n", list));
	}
	
}