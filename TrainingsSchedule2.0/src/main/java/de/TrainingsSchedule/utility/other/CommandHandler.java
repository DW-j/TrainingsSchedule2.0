package de.TrainingsSchedule.utility.other;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import de.TrainingsSchedule.commands.other.CommandAdd;
import de.TrainingsSchedule.commands.other.CommandCreate;
import de.TrainingsSchedule.commands.other.CommandDelete;
import de.TrainingsSchedule.commands.other.CommandGoal;
import de.TrainingsSchedule.commands.other.CommandHelp;
import de.TrainingsSchedule.commands.other.CommandShow;
import de.TrainingsSchedule.commands.print.CommandPrint;
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
			
			switch(command.getActionParameter().toLowerCase()) {
			
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
				switch(command.getObjectParameter().toLowerCase()) {
				
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
				return commandDelete.deleteDay(command.getObjectParameter(), trainingsSchedule);
			
			case "goals":
				CommandGoal commandGoal = new CommandGoal();
				return commandGoal.showGoals(trainingsSchedule);
				
			case "print":
				CommandPrint commandPrint = new CommandPrint();
				return commandPrint.print(trainingsSchedule);
				
			default:
				throw new ThrowableCommand();
			}
			
		}catch (ThrowableParameter throwableParameter) {
			return "Wrong parameter given.";
		}catch (ThrowableCommand throwableCommand) {
			return "Unknown command.";
		}catch (ThrowableAbort throwableAbort) {
			return "Process was aborted.";
		} catch (ThrowableData e) {
			return "No data exists.";
		}catch (Exception e) {
			e.printStackTrace();
			return "An unknown error occured.";
		}
	}
	
}

@Getter
class Command {

	private String actionParameter, objectParameter;
	private List<Integer> numberParameters;
	
	public Command(String input) throws ThrowableParameter, ThrowableCommand, Exception{
		String[] parameters = input.split(" ");
		actionParameter = parameters[0];
		try {
			objectParameter = parameters[1];
			numberParameters = Arrays.asList(Arrays.copyOfRange(parameters, 2, parameters.length)).stream().map(p -> Integer.parseInt(p)).collect(Collectors.toList());
		}catch(Exception e) {}
	}
	
}