package de.TrainingsSchedule.commands.other;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBException;

import de.TrainingsSchedule.elements.goals.ExerciseGoal;
import de.TrainingsSchedule.elements.goals.GoalList;
import de.TrainingsSchedule.elements.main.TrainingsSchedule;
import de.TrainingsSchedule.elements.specifics.Exercise;
import de.TrainingsSchedule.utility.files.FileWriter;
import de.TrainingsSchedule.utility.other.Table;

public class CommandGoal {

	TrainingsSchedule loadGoals(TrainingsSchedule trainingsSchedule) throws JAXBException {
		
		Map<String, Exercise> exercises = new HashMap<String, Exercise>();
		trainingsSchedule.getPlan().getDays().stream().forEach(d -> d.getExercises().stream().forEach(e -> exercises.put(e.getVariation(), e)));
		List<ExerciseGoal> exerciseGoals = new ArrayList<ExerciseGoal>();
		
		for(Exercise exercise: exercises.values()) {
			ExerciseGoal exerciseGoal = new ExerciseGoal(exercise.getName(), exercise.getVariation(), exercise.getDayId(), exercise.getId(), (int)Math.ceil(exercise.getReps().stream().collect(Collectors.averagingInt(i -> i))), exercise.getWeight());
			exerciseGoals.add(exerciseGoal);
		}
		
		GoalList goalList = new GoalList(exerciseGoals);
		trainingsSchedule.setGoalList(goalList);
		FileWriter.getInstance().writeXML("TrainingsSchedule", trainingsSchedule, TrainingsSchedule.class);
		
		return trainingsSchedule;
	}
	
	public String showGoals(TrainingsSchedule trainingsSchedule) throws JAXBException {
		trainingsSchedule = loadGoals(trainingsSchedule);
		
		List<String> header = Arrays.asList(new String[] {"DayID", "ExerciseID", "Name", "Variation", "Weight goal", "Rep goal"});
		Table table = new Table(header);
		trainingsSchedule.getGoalList().getExerciseGoals().stream().forEach(g -> table.addRow(g.toRow()));
		
		return table.toString();
	}
} 