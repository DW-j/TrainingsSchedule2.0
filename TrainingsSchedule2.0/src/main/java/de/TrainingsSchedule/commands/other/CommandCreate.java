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
		
		int dayNumber = Integer.parseInt(communicator.getSingleInput(Message.getEnterMessage("the number of days", "the plan")));
		
		List<DayTemplate> dayTemplates = new ArrayList<DayTemplate>();
		while(dayNumber>dayTemplates.size()) {
			
			int dayId = dayTemplates.size()+1;
			List<ExerciseTemplate> exerciseTemplates = new ArrayList<ExerciseTemplate>();
			int exerciseNumber = Integer.parseInt(communicator.getSingleInput(Message.getEnterMessage("the number of exercises", "day", dayId)));
			
			while(exerciseNumber>exerciseTemplates.size()) {
				try {
					int exerciseId = exerciseTemplates.size()+1;
					String exerciseName = communicator.getAbortableRepeatableInput(Message.getEnterMessage("the name of the exercise", "exercise", exerciseId, "day", dayNumber));
					int setNumber = Integer.parseInt(communicator.getAbortableRepeatableInput(Message.getEnterMessage("the number of sets", "exercise", exerciseId, "day", dayNumber)));			
					int variationNumber = Integer.parseInt(communicator.getAbortableRepeatableInput(Message.getEnterMessage("the number of variations", "exercise", exerciseId, "day", dayNumber)));
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
					ExerciseTemplate exerciseTemplate = new ExerciseTemplate(exerciseId, dayId, setNumber, exerciseName, variations, goalTypes);
					exerciseTemplates.add(exerciseTemplate);
				} catch (ThrowableRepeat e) {
					communicator.output(Message.getRepeatingMessage());
				} catch (Exception e) {
					communicator.output(Message.getWrongInputMessage());
				}
			}
			DayTemplate dayTemplate = new DayTemplate(dayId, exerciseTemplates);
			dayTemplates.add(dayTemplate);
		}
		
		PlanTemplate planTemplate = new PlanTemplate(dayTemplates);
		
		communicator.output(planTemplate.toString());
		communicator.getAbortConfirmInput("to save the plan template");	
		
		TrainingsSchedule trainingsSchedule = new TrainingsSchedule(planTemplate, trainingsScheduleOld.getPlan(), new GoalList());
		FileWriter.getInstance().writeXML("TrainingsSchedule", trainingsSchedule, TrainingsSchedule.class);	
		
		return "Plan successful created.";
	}

}
