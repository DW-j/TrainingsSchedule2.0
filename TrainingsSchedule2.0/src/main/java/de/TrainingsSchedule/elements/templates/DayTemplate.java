package de.TrainingsSchedule.elements.templates;

import java.util.Arrays;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import de.TrainingsSchedule.utility.other.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@XmlRootElement
@NoArgsConstructor
@Getter
@AllArgsConstructor
public class DayTemplate {

	private int id;
	@XmlElement
	private List<ExerciseTemplate> exerciseTemplates;
	
	@XmlAttribute
	public void setId(int id) {
		this.id = id;
	}
	
	public Table toTable() {
		String headline = "Day "+id;
		List<String> header = Arrays.asList(new String[] {"ID", "Name", "Sets", "Variations", "Goaltype/s"});
		Table table = new Table(header, headline);
		for(ExerciseTemplate exerciseTemplate: exerciseTemplates) {
			table.addRow(exerciseTemplate.toRow());
		}
		return table;
	}
	
}