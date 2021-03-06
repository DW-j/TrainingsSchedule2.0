package de.TrainingsSchedule.elements.specifics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import de.TrainingsSchedule.utility.other.Table;
import de.TrainingsSchedule.utility.other.TimeFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@XmlRootElement
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Day {

	@XmlAttribute
	private int id;
	@XmlAttribute
	private Date date;
	@XmlAttribute
	private double weight;
	@XmlAttribute
	private int time;
	private List<Exercise> exercises = new ArrayList<Exercise>();
	
	public Day(int id, Date date, double weight, int time) {
		this.id = id;
		this.date = date;
		this.weight = weight;
		this.time = time;
	}

	@XmlElement
	public void setExercises(List<Exercise> exercises) {
		this.exercises = exercises;
	}

	public Table toTable() {
		String headline = "Day "+id+", "+TimeFormat.getDateformat().format(date)+", body weight: "+weight+"kg, time: "+time+"min";
		List<String> header = Arrays.asList(new String[] {"ID", "Name", "Variation", "Weight", "Reps"});
		Table table = new Table(header, headline);
		for(Exercise exercise: exercises) {
			table.addRow(exercise.toRow(false));
		}
		return table;
	}
	
}