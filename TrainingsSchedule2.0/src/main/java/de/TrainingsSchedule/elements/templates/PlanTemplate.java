package de.TrainingsSchedule.elements.templates;

import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import de.TrainingsSchedule.utility.text.ListConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PlanTemplate {

	@XmlElement
	private List<DayTemplate> dayTemplates;
	
	public String toString() {
		return ListConverter.getInstance().listLists(dayTemplates.stream().map(d -> d.toTable().toString()).collect(Collectors.toList()));
	}
	
}