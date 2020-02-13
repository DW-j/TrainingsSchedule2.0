package de.TrainingsSchedule.controller.main;

import de.TrainingsSchedule.utility.other.CommandHandler;
import de.TrainingsSchedule.utility.other.Communicator;
import de.TrainingsSchedule.utility.throwables.ThrowableExit;

public class ControllerMain {

	public static void main(String[] args) {
		
		Communicator communicator = Communicator.getInstance();
		
		communicator.output("Welcome to the TrainingsSchedule-Application by DW-j.");
		while(true) {
			try {
				String input = communicator.getInput("Please enter a command.");
				String output = CommandHandler.getInstance().evaluateCommand(input);
				communicator.output(output);
			}catch(ThrowableExit throwableExit) {
				communicator.output("Exiting. Thank you for using the TrainingsSchedule-Application by DW-j.");
				break;
			}
		}
		communicator.close();
	}

}