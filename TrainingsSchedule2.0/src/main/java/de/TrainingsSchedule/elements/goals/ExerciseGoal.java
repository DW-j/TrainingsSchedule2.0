package de.TrainingsSchedule.elements.goals;

import javax.xml.bind.annotation.XmlAttribute;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ExerciseGoal {

	@XmlAttribute
	private int dayId, exerciseId, goalReps;
	@XmlAttribute
	private double goalWeight;
	
}