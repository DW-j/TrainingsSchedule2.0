package de.TrainingsSchedule.elements.goals;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
@Getter
public class GoalList {

	@XmlElement
	private List<ExerciseGoal> exerciseGoals;
	
}