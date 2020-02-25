package de.TrainingsSchedule.commands.other;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import de.TrainingsSchedule.elements.goals.GoalList;
import de.TrainingsSchedule.elements.main.TrainingsSchedule;
import de.TrainingsSchedule.elements.templates.DayTemplate;
import de.TrainingsSchedule.elements.templates.ExerciseTemplate;
import de.TrainingsSchedule.elements.templates.PlanTemplate;
import de.TrainingsSchedule.utility.files.FileWriter;
import de.TrainingsSchedule.utility.other.Communicator;
import de.TrainingsSchedule.utility.text.Message;
import de.TrainingsSchedule.utility.throwables.ThrowableAbort;
import de.TrainingsSchedule.utility.throwables.ThrowableExit;
import de.TrainingsSchedule.utility.throwables.ThrowableRepeat;

public class CommandCreate {

	public String createPlan(TrainingsSchedule trainingsScheduleOld) throws NumberFormatException, ThrowableAbort, JAXBException, ThrowableExit {
		
		Communicator communicator = Communicator.getInstance();
		
		int dayNumber = Integer.parseInt(communicator.getInput(Message.getEnterMessage("the number of days", "the plan")));
		
		List<DayTemplate> dayTemplates = new ArrayList<DayTemplate>();
		while(dayNumber>dayTemplates.size()) {
			dayTemplates.add(createDayTemplate(dayTemplates.size()+1));
		}
		
		PlanTemplate planTemplate = new PlanTemplate(dayTemplates);
		
		communicator.output(planTemplate.toString());
		communicator.getAbortConfirmInput("to save the plan template");	
		
		TrainingsSchedule trainingsSchedule = new TrainingsSchedule(planTemplate, trainingsScheduleOld.getPlan(), new GoalList());
		FileWriter.getInstance().writeXml("TrainingsSchedule", trainingsSchedule, TrainingsSchedule.class);	
		
		return "Plan successful created.";
	}
	
	public DayTemplate createDayTemplate(int dayId) throws ThrowableExit, ThrowableAbort {
		Communicator communicator = Communicator.getInstance();
		List<ExerciseTemplate> exerciseTemplates = new ArrayList<ExerciseTemplate>();
		int exerciseNumber = 0;
		while(exerciseNumber <= 0) {
			try {
				exerciseNumber = Integer.parseInt(communicator.getInput(Message.getEnterMessage("the number of exercises", "day", dayId)));	
				communicator.getConfirmInput();
			} catch (ThrowableRepeat e) {
				communicator.output(Message.getRepeatingMessage());
			} catch (Exception e) {
				communicator.output(Message.getWrongInputMessage());
			}
		}
		
		while(exerciseNumber>exerciseTemplates.size()) {
			ExerciseTemplate exerciseTemplate = createExerciseTemplate(dayId, exerciseTemplates.size()+1);
			exerciseTemplates.add(exerciseTemplate);
		}
		return new DayTemplate(dayId, exerciseTemplates);
	}
	
	public ExerciseTemplate createExerciseTemplate(int dayId, int exerciseId) throws ThrowableExit, ThrowableAbort {
		Communicator communicator = Communicator.getInstance();
		ExerciseTemplate exerciseTemplate = null;
		while(exerciseTemplate==null) {
			try {
				String exerciseName = communicator.getAbortableRepeatableInput(Message.getEnterMessage("the name of the exercise", "exercise", exerciseId, "day", dayId));
				int setNumber = Integer.parseInt(communicator.getAbortableRepeatableInput(Message.getEnterMessage("the number of sets", "exercise", exerciseId, "day", dayId)));			
				int variationNumber = Integer.parseInt(communicator.getAbortableRepeatableInput(Message.getEnterMessage("the number of variations", "exercise", exerciseId, "day", dayId)));
				List<String> variations = new ArrayList<String>();
				List<Integer> goalTypes = new ArrayList<Integer>();
				
				while(variationNumber>variations.size()) {
					variations.add(communicator.getAbortableRepeatableInput(Message.getEnterMessage(String.format("the %d. variation", variations.size()+1), "exercise", exerciseId)));
					goalTypes.add(Integer.parseInt(communicator.getAbortableRepeatableInput(Message.getEnterMessage("the goaltype", "variation", variations.size(), Message.getGoaltypeDescription()))));
				}
				if(variationNumber<1) {
					goalTypes.add(Integer.parseInt(communicator.getAbortableRepeatableInput(Message.getEnterMessage("the goaltype", "exercise", Message.getGoaltypeDescription()))));
					variations.add("-");
				}
				if(goalTypes.stream().filter(i -> i!=1&&i!=2).count()>0) {
					throw new Exception();
				}
				communicator.getConfirmInput();
				exerciseTemplate = new ExerciseTemplate(exerciseId, dayId, setNumber, exerciseName, variations, goalTypes);
			} catch (ThrowableRepeat e) {
				communicator.output(Message.getRepeatingMessage());
			} catch (Exception e) {
				communicator.output(Message.getWrongInputMessage());
			}
		}
		return exerciseTemplate;
	}

}
