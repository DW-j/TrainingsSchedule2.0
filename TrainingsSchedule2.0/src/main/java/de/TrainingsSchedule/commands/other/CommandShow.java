package de.TrainingsSchedule.commands.other;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import de.TrainingsSchedule.elements.main.TrainingsSchedule;
import de.TrainingsSchedule.elements.specifics.Day;
import de.TrainingsSchedule.elements.specifics.Plan;
import de.TrainingsSchedule.elements.templates.ExerciseTemplate;
import de.TrainingsSchedule.elements.templates.PlanTemplate;
import de.TrainingsSchedule.utility.other.Table;
import de.TrainingsSchedule.utility.text.ListConverter;
import de.TrainingsSchedule.utility.throwables.ThrowableData;

public class CommandShow {

	public String showPlan(TrainingsSchedule trainingsSchedule) {
		PlanTemplate planTemplate = trainingsSchedule.getPlanTemplate();
		return planTemplate.toString();
	}
	
	public String showDays(TrainingsSchedule trainingsSchedule) throws ThrowableData {
		Plan plan = trainingsSchedule.getPlan();
		if(plan.getDays().size()<1) {
			throw new ThrowableData();
		}
		return plan.toString();		
	}

	public String showExercise(TrainingsSchedule trainingsSchedule, List<Integer> parameters) throws ThrowableData {
		
		int dayID = parameters.get(0);
		int exerciseID = parameters.get(1);
		
		List<String> exerciseTables = new ArrayList<String>();
		try {
			ExerciseTemplate exerciseTemplate = trainingsSchedule.getPlanTemplate().getDayTemplates().get(dayID-1).getExerciseTemplates().get(exerciseID-1);
			List<Day> days = trainingsSchedule.getPlan().getDays().stream().filter(d -> d.getId()==dayID).collect(Collectors.toList());
			days.stream().forEach(d -> d.getExercises().get(exerciseID-1));
			
			List<String> exerciseVariations = exerciseTemplate.getVariations();
			List<String> header = Arrays.asList(new String[] {"Date", "ID", "Name",  "Variation", "Weight", "Reps"});

			if(exerciseVariations.size()<1) {
				exerciseVariations.add("-");
			}
			
			for(String variation: exerciseVariations) {
				Table table = new Table(header, variation);
				days.stream().forEach(d -> d.getExercises().stream().filter(e -> e.getVariation().equals(variation)).forEach(e -> table.addRow(e.toRow(true))));
				exerciseTables.add(table.toString());
			}
			
		}catch (Exception e) {
			throw new ThrowableData();
		}
		
		return ListConverter.getInstance().listLists(exerciseTables);
	}
	
}