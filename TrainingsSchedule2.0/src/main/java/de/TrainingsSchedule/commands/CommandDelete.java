package de.TrainingsSchedule.commands;

import java.text.ParseException;
import java.util.Date;
import java.util.NoSuchElementException;

import javax.xml.bind.JAXBException;

import de.TrainingsSchedule.elements.main.TrainingsSchedule;
import de.TrainingsSchedule.elements.specifics.Day;
import de.TrainingsSchedule.elements.specifics.Plan;
import de.TrainingsSchedule.utility.files.FileWriter;
import de.TrainingsSchedule.utility.other.Communicator;
import de.TrainingsSchedule.utility.other.Constants;
import de.TrainingsSchedule.utility.throwables.ThrowableAbort;
import de.TrainingsSchedule.utility.throwables.ThrowableData;
import de.TrainingsSchedule.utility.throwables.ThrowableExit;

public class CommandDelete {

	public String deleteDay(String date, TrainingsSchedule trainingsSchedule) throws ParseException, ThrowableAbort, ThrowableExit, ThrowableData, JAXBException {
		
		Date dayDate = Constants.dateFormat.parse(date);
		Plan plan = trainingsSchedule.getPlan();
		Day day;
		try {
			day = plan.getDays().stream().filter(d -> d.getDate().equals(dayDate)).findFirst().get();
		}catch (NoSuchElementException e) {
			throw new ThrowableData();
		}
		Communicator communicator = Communicator.getInstance();
		
		communicator.output('\n'+day.toTable()+'\n');
		communicator.getAbortConfirmInput("delete this day");
		plan.getDays().remove(day);
		
		trainingsSchedule = new TrainingsSchedule(trainingsSchedule.getPlanTemplate(), plan, trainingsSchedule.getGoalList());
		FileWriter.getInstance().writeXML("trainingsschedule", trainingsSchedule, TrainingsSchedule.class);
		
		return "Day successul deleted.";
	}
	
}