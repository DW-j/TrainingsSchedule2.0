package de.TrainingsSchedule.elements.specifics;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import de.TrainingsSchedule.utility.other.TimeFormat;
import de.TrainingsSchedule.utility.text.ListConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@XmlRootElement
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Exercise {

	@XmlAttribute
	private int id, dayId;
	@XmlAttribute
	private String name;
	@XmlAttribute
	private Date date;
	private String variation;
	@XmlElementWrapper
	@XmlElement
	private List<Integer> reps;
	@XmlAttribute
	private double weight;
	@XmlElementWrapper
	@XmlElement
	private List<Integer> timeSets;
	
	@XmlAttribute
	public void setVariation(String variation) {
		this.variation = variation;
	}

	public List<String> toRow(boolean dateIncluded) {
		ListConverter listConverter = ListConverter.getInstance();
		List<String> row = new ArrayList<String>(){
			private static final long serialVersionUID = 1L;
		{
			if(dateIncluded) {
				add(TimeFormat.getDateformat().format(date));
			}
			add(id+"");
			add(name);
			add(variation);
			add(weight+"kg");
			add(listConverter.stringListToString(listConverter.convertTimeSets(reps, timeSets)));
		}};
		return row;
	}
	
}