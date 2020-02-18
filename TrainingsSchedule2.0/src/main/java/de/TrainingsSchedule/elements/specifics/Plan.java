package de.TrainingsSchedule.elements.specifics;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import de.TrainingsSchedule.utility.text.ListConverter;
import lombok.Getter;

@XmlRootElement
@Getter
public class Plan {

	@XmlElement
	private List<Day> days = new ArrayList<Day>();
	
	public void addDay(Day day) {
		days.add(day);
	}
	
	public String toString() {
		return ListConverter.getInstance().listLists(days.stream().map(d -> d.toTable().toString()).collect(Collectors.toList()));
	}
}