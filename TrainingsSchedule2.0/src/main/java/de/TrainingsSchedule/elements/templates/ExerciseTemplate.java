package de.TrainingsSchedule.elements.templates;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import de.TrainingsSchedule.utility.text.ListConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@XmlRootElement
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ExerciseTemplate {

	@XmlAttribute
	private int id, dayId, setNumber;
	@XmlAttribute
	private String name;
	@XmlElement
	private List<String> variations = new ArrayList<String>();
	@XmlElement
	private List<Integer> goalTypes = new ArrayList<Integer>();
	
	public List<String> toRow() {
		List<String> row = new ArrayList<String>(){
			private static final long serialVersionUID = 1L;
		{
			add(id+"");
			add(name);
			add(setNumber+"");
			add(ListConverter.getInstance().stringListToString(variations));
			add(ListConverter.getInstance().integerListToString(goalTypes));
		}};
		return row;
	}
	
}