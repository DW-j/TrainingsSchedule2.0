package de.TrainingsSchedule.utility.other;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import de.TrainingsSchedule.commands.other.CommandAdd;
import de.TrainingsSchedule.commands.other.CommandCreate;
import de.TrainingsSchedule.commands.other.CommandDelete;
import de.TrainingsSchedule.commands.other.CommandEdit;
import de.TrainingsSchedule.commands.other.CommandGoal;
import de.TrainingsSchedule.commands.other.CommandHelp;
import de.TrainingsSchedule.commands.other.CommandShow;
import de.TrainingsSchedule.commands.print.command.CommandPrint;
import de.TrainingsSchedule.elements.main.TrainingsSchedule;
import de.TrainingsSchedule.utility.files.FileReader;
import de.TrainingsSchedule.utility.throwables.ThrowableAbort;
import de.TrainingsSchedule.utility.throwables.ThrowableCommand;
import de.TrainingsSchedule.utility.throwables.ThrowableData;
import de.TrainingsSchedule.utility.throwables.ThrowableExit;
import de.TrainingsSchedule.utility.throwables.ThrowableParameter;
import lombok.Getter;

public class CommandHandler {
	
	private static CommandHandler commandHandler = null;
	
	private CommandHandler() {}
	
	public static CommandHandler getInstance() {
		if(commandHandler==null) {
			commandHandler = new CommandHandler();
		}
		return commandHandler;
	}

	public String evaluateCommand(String input) throws ThrowableExit{
		
		try {
			Command command = new Command(input);
			TrainingsSchedule trainingsSchedule = (TrainingsSchedule) FileReader.getInstance().readXml("trainingsschedule", TrainingsSchedule.class);
						
			switch(command.getActionParameter()) {
			
			case "exit":
				throw new ThrowableExit();
				
			case "help":
				CommandHelp commandHelp = new CommandHelp();
				return commandHelp.toString();
				
			case "create":
				CommandCreate commandCreate = new CommandCreate();
				return commandCreate.createPlan(trainingsSchedule);
				
			case "add":
				CommandAdd commandAdd = new CommandAdd();
				return commandAdd.addDay(trainingsSchedule);
			
			case "show":
				CommandShow commandShow = new CommandShow();
				switch(command.getObjectParameters().get(0)) {
				
				case "plan":
					return commandShow.showPlan(trainingsSchedule);
				case "days":
					return commandShow.showDays(trainingsSchedule);
				case "exercise":
					return commandShow.showExercise(trainingsSchedule, command.getNumberParameters());
				default:
					throw new ThrowableParameter();
				}
			
			case "delete":
				CommandDelete commandDelete = new CommandDelete();
				return commandDelete.deleteDay(command.getObjectParameters().get(0), trainingsSchedule);
			
			case "goals":
				CommandGoal commandGoal = new CommandGoal();
				return commandGoal.showGoals(trainingsSchedule);
				
			case "print":
				CommandPrint commandPrint = new CommandPrint();
				return commandPrint.print(trainingsSchedule);
			
			case "edit":
				CommandEdit commandEdit = new CommandEdit();
				if(command.getObjectParameters().get(0).equals("exercise")&&command.getObjectParameters().get(1).equals("add")) {
					return commandEdit.addExercise(trainingsSchedule);
				}
				if(command.getObjectParameters().get(0).equals("exercise")&&command.getObjectParameters().get(1).equals("delete")) {
					return commandEdit.deleteExercise(trainingsSchedule);
				}
				if(command.getObjectParameters().get(0).equals("day")&&command.getObjectParameters().get(1).equals("add")) {
					return commandEdit.addDay(trainingsSchedule);
				}
				if(command.getObjectParameters().get(0).equals("day")&&command.getObjectParameters().get(1).equals("delete")) {
					return commandEdit.deleteDay(trainingsSchedule);
				}
				throw new ThrowableParameter();
				
			default:
				throw new ThrowableCommand();
			}
			
		}catch (ThrowableParameter throwableParameter) {
			return "Wrong parameter given.";
		}catch (ThrowableCommand throwableCommand) {
			return "Unknown command.";
		}catch (ThrowableAbort throwableAbort) {
			return "Process was aborted.";
		} catch (ThrowableData throwableData) {
			return "No data exists.";
		}catch(IOException e) {
			return "An error related to the file system occured.";
		}catch (Exception e) {
			e.printStackTrace();
			return "An unknown error occured.";
		}
	}
	
}

@Getter
class Command {

	private String actionParameter;
	private List<String> objectParameters = new ArrayList<String>();
	private List<Integer> numberParameters;
	
	public Command(String input) throws ThrowableParameter, ThrowableCommand, Exception{
		String[] parameters = input.split(" ");
		actionParameter = parameters[0].toLowerCase();
		try {
			objectParameters.add(parameters[1].toLowerCase());
			if(!StringUtils.isNumeric(parameters[2])) {
				objectParameters.add(parameters[2].toLowerCase());
			}else {
				numberParameters = Arrays.asList(Arrays.copyOfRange(parameters, 2, parameters.length)).stream().map(p -> Integer.parseInt(p)).collect(Collectors.toList());
			}
		}catch(Exception e) {}
	}
	
}