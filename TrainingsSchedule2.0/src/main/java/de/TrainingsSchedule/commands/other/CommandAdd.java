package de.TrainingsSchedule.commands.other;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.xml.bind.JAXBException;

import de.TrainingsSchedule.elements.main.TrainingsSchedule;
import de.TrainingsSchedule.elements.specifics.Day;
import de.TrainingsSchedule.elements.specifics.Exercise;
import de.TrainingsSchedule.elements.specifics.Plan;
import de.TrainingsSchedule.elements.templates.DayTemplate;
import de.TrainingsSchedule.elements.templates.ExerciseTemplate;
import de.TrainingsSchedule.elements.templates.PlanTemplate;
import de.TrainingsSchedule.utility.files.FileWriter;
import de.TrainingsSchedule.utility.other.Communicator;
import de.TrainingsSchedule.utility.other.Constants;
import de.TrainingsSchedule.utility.other.Table;
import de.TrainingsSchedule.utility.text.Message;
import de.TrainingsSchedule.utility.throwables.ThrowableAbort;
import de.TrainingsSchedule.utility.throwables.ThrowableExit;
import de.TrainingsSchedule.utility.throwables.ThrowableRepeat;

public class CommandAdd {

	public String addDay(TrainingsSchedule trainingsSchedule) throws JAXBException, ThrowableAbort, ThrowableExit {
		
		Communicator communicator = Communicator.getInstance();
		
		PlanTemplate planTemplate = trainingsSchedule.getPlanTemplate();
		Plan plan = trainingsSchedule.getPlan();
		
		DayTemplate dayTemplate = null;
		Day day = null;
		while(day==null) {
			try {
				int dayId = Integer.parseInt(communicator.getAbortableRepeatableInput(Message.getEnterMessage("the id of the day to be added")));
				final int dayNo = dayId;
				dayTemplate = planTemplate.getDayTemplates().stream().filter(d -> d.getId()==dayNo).findFirst().get();
				Date dayDate = Constants.dateFormat.parse(communicator.getAbortableRepeatableInput(Message.getEnterMessage(String.format("the date in the '%s' format", Constants.dateFormatString))));
				int dayTime = Integer.parseInt(communicator.getAbortableRepeatableInput(Message.getEnterMessage("the workout time of the day")));
				double dayWeight = Double.parseDouble(communicator.getAbortableRepeatableInput(Message.getEnterMessage("the body weight of the day")));
				day = new Day(dayId, dayDate, dayWeight, dayTime);
				communicator.getConfirmInput();
			}catch(NoSuchElementException e) {
				communicator.output(Message.getNoDataMessage());
			}catch(Exception e) {
				communicator.output(Message.getWrongInputMessage());
			}catch(ThrowableRepeat e) {
				communicator.output(Message.getRepeatingMessage());
			}
		}

		List<ExerciseTemplate> exerciseTemplates = dayTemplate.getExerciseTemplates();
		List<Exercise> exercises = new ArrayList<Exercise>();
		while(exercises.size()<dayTemplate.getExerciseTemplates().size()) {
			ExerciseTemplate exerciseTemplate = exerciseTemplates.get(exercises.size());
			int exerciseId = exerciseTemplate.getId();
			int dayId = exerciseTemplate.getDayId();
			String exerciseName = exerciseTemplate.getName();
			try {
				communicator.output(String.format("The current exercise is %s.", exerciseName));
				
				String exerciseVariation = null;
				if(exerciseTemplate.getVariations().size()>1) {
					Table variationTable = new Table(Arrays.asList(new String[]{"ID", "Variation"}));
					variationTable.addColumn(IntStream.range(0, exerciseTemplate.getVariations().size()).mapToObj(i -> i+"").collect(Collectors.toList()));
					variationTable.addColumn(exerciseTemplate.getVariations());
					communicator.output(variationTable.toString());
					exerciseVariation = exerciseTemplate.getVariations().get(Integer.parseInt(communicator.getAbortableRepeatableInput(Message.getEnterMessage("the ID of the desired variation"))));
				}else {
					exerciseVariation = exerciseTemplate.getVariations().get(0);
				}
				
				List<String> repList = new ArrayList<String>();
				for(int i=0; i<exerciseTemplate.getSetNumber(); i++) {
					repList.add(("set"+(i+1)));
				}
				String inputPattern = "'weight "+String.join(" ", repList)+"'";
				List<String> numberInput = Arrays.asList(communicator.getAbortableRepeatableInput(Message.getEnterMessage("the weight and sets of the exercise by the following pattern "+inputPattern+". If the set is a timeset, start it with 'd:' and provide it in seconds")).split(" "));
				if(numberInput.size()<exerciseTemplate.getSetNumber()+1) {
					throw new Exception();
				}
				double exerciseWeight = Double.parseDouble(numberInput.get(0));
				List<Integer> timeSets = new ArrayList<Integer>();
				numberInput = numberInput.subList(1, numberInput.size());
				for(int i=0; i<numberInput.size(); i++) {
					if(numberInput.get(i).startsWith("d:")) {
						timeSets.add(i+1);
						numberInput.set(i, numberInput.get(i).replace("d:", ""));
					}
				}
				List<Integer> exerciseReps = numberInput.stream().map(i -> Integer.parseInt(i)).collect(Collectors.toList());
				communicator.getConfirmInput();
				Exercise exercise = new Exercise(exerciseId, dayId, exerciseName, exerciseVariation, exerciseReps, exerciseWeight, timeSets);
				exercises.add(exercise);
			}catch (Exception e) {
				communicator.output(Message.getWrongInputMessage());
			}catch(ThrowableRepeat e) {
				communicator.output(Message.getRepeatingMessage());
			}
		}
		
		day.setExercises(exercises);
		communicator.output(day.toTable());
		communicator.getAbortConfirmInput("to save the day");
		
		plan.addDay(day);
		trainingsSchedule = new TrainingsSchedule(planTemplate, plan, trainingsSchedule.getGoalList());
		
		CommandGoal commandGoal = new CommandGoal();
		trainingsSchedule = commandGoal.loadGoals(trainingsSchedule);
		
		FileWriter.getInstance().writeXml("trainingsschedule", trainingsSchedule, TrainingsSchedule.class);
		
		return "Day successful added.";
	}

}