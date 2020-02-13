package de.TrainingsSchedule.elements.goals;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ExerciseGoal {

	@XmlAttribute
	String name, variation;
	@XmlAttribute
	private int dayId, exerciseId, goalReps;
	@XmlAttribute
	private double goalWeight;
	
	public List<String> toRow() {
		List<String> row = new ArrayList<String>() {
			private static final long serialVersionUID = 1L;
		{
			add(dayId+"");
			add(exerciseId+"");
			add(name);
			add(variation);
			add(goalWeight+"kg");
			add(goalReps+"");
		}};
		return row;
	}
	
}