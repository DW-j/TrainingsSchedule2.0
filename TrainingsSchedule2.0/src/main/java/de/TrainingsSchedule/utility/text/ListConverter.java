package de.TrainingsSchedule.utility.text;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import de.TrainingsSchedule.utility.other.TimeFormat;

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
		return String.join("; ", list);
	}
	
	public String integerListToString(List<Integer> list) {
		return String.join("; ", list.stream().map(i -> i+"").collect(Collectors.toList()));
	}
	
	public String listLists(List<String> list) {
		return String.format("\n%s", String.join("\n", list));
	}
	
	public List<String> convertTimeSets(List<Integer> repList, List<Integer> timeSets){
		List<String> formatedRepList = new ArrayList<String>();
		if(timeSets == null) {
			return repList.stream().map(i -> i+"").collect(Collectors.toList());
		}
		for(int i=0; i<repList.size(); i++) {
			if(timeSets.contains(i)) {
				formatedRepList.add(TimeFormat.secondsToMinutes(repList.get(i)));
			}else {
				formatedRepList.add(repList.get(i)+"");
			}
		}
		return formatedRepList;
	}
	
}