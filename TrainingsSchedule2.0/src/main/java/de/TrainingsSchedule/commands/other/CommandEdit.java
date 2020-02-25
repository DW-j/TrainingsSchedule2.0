package de.TrainingsSchedule.commands.other;

import javax.xml.bind.JAXBException;

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

public class CommandEdit {

	public String addDay(TrainingsSchedule trainingsSchedule) throws NumberFormatException, ThrowableAbort, ThrowableExit, JAXBException {
		Communicator communicator = Communicator.getInstance();
		
		communicator.output(trainingsSchedule.getPlanTemplate().toString());
		
		int dayId = getId("day", "add");
		
		CommandCreate commandCreate = new CommandCreate();
		DayTemplate dayTemplate = commandCreate.createDayTemplate(dayId);
		trainingsSchedule.getPlanTemplate().getDayTemplates().add(dayId-1, dayTemplate);
		for(int i=0; i<trainingsSchedule.getPlanTemplate().getDayTemplates().size(); i++) {
			trainingsSchedule.getPlanTemplate().getDayTemplates().get(i).setId(i+1);
		}
		
		FileWriter.getInstance().writeXml("trainingsschedule", trainingsSchedule, TrainingsSchedule.class);
		
		return "DayTemplate added succesfully.";
	}
	
	public String deleteDay(TrainingsSchedule trainingsSchedule) throws NumberFormatException, ThrowableAbort, ThrowableExit, JAXBException {
		Communicator communicator = Communicator.getInstance();
		
		communicator.output(trainingsSchedule.getPlanTemplate().toString());
		int dayId = getId("day", "delete");

		PlanTemplate planTemplate = trainingsSchedule.getPlanTemplate();
		planTemplate.getDayTemplates().remove(dayId-1);
		for(int i=0; i<planTemplate.getDayTemplates().size(); i++) {
			planTemplate.getDayTemplates().get(i).setId(i+1);
			for(ExerciseTemplate exerciseTemplate: planTemplate.getDayTemplates().get(i).getExerciseTemplates()) {
				exerciseTemplate.setDayId(i+1);
			}
		}
		trainingsSchedule.setPlanTemplate(planTemplate);
		
		FileWriter.getInstance().writeXml("trainingsschedule", trainingsSchedule, TrainingsSchedule.class);
		
		return String.format("DayTemplate %d successfully deleted.", dayId);
	}
	
	public String addExercise(TrainingsSchedule trainingsSchedule) throws NumberFormatException, ThrowableAbort, ThrowableExit, JAXBException {
		Communicator communicator = Communicator.getInstance();
		
		communicator.output(trainingsSchedule.getPlanTemplate().toString());
		int dayId = getId("day", "edit");
		int exerciseId = getId("exercise", "add");
		
		CommandCreate commandCreate = new CommandCreate();
		ExerciseTemplate exerciseTemplate = commandCreate.createExerciseTemplate(dayId, exerciseId);
		trainingsSchedule.getPlanTemplate().getDayTemplates().get(dayId-1).getExerciseTemplates().add(exerciseId-1, exerciseTemplate);
		for(int i=0; i<trainingsSchedule.getPlanTemplate().getDayTemplates().get(dayId).getExerciseTemplates().size(); i++) {
			trainingsSchedule.getPlanTemplate().getDayTemplates().get(dayId-1).getExerciseTemplates().get(i).setId(i+1);
		}
		
		FileWriter.getInstance().writeXml("trainingsschedule", trainingsSchedule, TrainingsSchedule.class);
		
		return "ExerciseTemplate successfully added.";
	}
	
	public String deleteExercise(TrainingsSchedule trainingsSchedule) throws NumberFormatException, ThrowableAbort, ThrowableExit, JAXBException {
		Communicator communicator = Communicator.getInstance();
		
		communicator.output(trainingsSchedule.getPlanTemplate().toString());
		int dayId = getId("day", "edit");
		int exerciseId = getId("exercise", "delete");
		
		DayTemplate dayTemplate = trainingsSchedule.getPlanTemplate().getDayTemplates().get(dayId-1);
		dayTemplate.getExerciseTemplates().remove(exerciseId-1);
		for(int i=0; i<dayTemplate.getExerciseTemplates().size(); i++) {
			dayTemplate.getExerciseTemplates().get(i).setId(i+1);
		}
	
		trainingsSchedule.getPlanTemplate().getDayTemplates().set(dayId-1, dayTemplate);
		
		FileWriter.getInstance().writeXml("trainingsschedule", trainingsSchedule, TrainingsSchedule.class);
		
		return String.format("ExerciseTemplate successfully deleted.", dayId);
	}
	
	private int getId(String element, String action) throws ThrowableAbort, ThrowableExit {
		Communicator communicator = Communicator.getInstance();
		while(true) {
			try {
				return Integer.parseInt(communicator.getAbortableRepeatableInput(Message.getEnterMessageAction("ID", element, action)));
			} catch (ThrowableRepeat e) {
				communicator.output(Message.getRepeatingMessage());
			} catch (Exception e) {
				communicator.output(Message.getWrongInputMessage());
			}
		}
	}
	
}