package de.TrainingsSchedule.utility.other;

import java.util.Scanner;

import de.TrainingsSchedule.utility.text.Message;
import de.TrainingsSchedule.utility.throwables.ThrowableAbort;
import de.TrainingsSchedule.utility.throwables.ThrowableExit;
import de.TrainingsSchedule.utility.throwables.ThrowableRepeat;

public class Communicator {

	private Scanner sc;
	private static Communicator communicator = null;
	
	private Communicator() {
		sc = new Scanner(System.in);
	}
	
	public static Communicator getInstance() {
		if(communicator==null) {
			communicator = new Communicator();
		}
		return communicator;
	}
	
	public String getInput(String message) throws ThrowableExit {
		System.out.println(message);
		String input = sc.nextLine();
		if(input.equals("exit")) {
			throw new ThrowableExit();
		}
		return input;
	}
	
	public boolean getConfirmInput() throws ThrowableExit, ThrowableRepeat, ThrowableAbort {
		String input = getAbortableRepeatableInput("Type 'repeat' to repeat the process. Otherwise press 'enter'.");
		if(input.equals("")) {
			return true;
		}
		if(input.equals("repeat")) {
			throw new ThrowableRepeat();
		}
		return getConfirmInput();
	}
	
	public String getAbortableRepeatableInput(String message) throws ThrowableAbort, ThrowableRepeat, ThrowableExit {
		System.out.println(message);
		String input = sc.nextLine();
		if(input.equals("abort")) {
			throw new ThrowableAbort();
		}
		if(input.equals("repeat")) {
			throw new ThrowableRepeat();
		}
		if(input.equals("exit")) {
			throw new ThrowableExit();
		}
		return input;
	}
	
	public boolean getAbortConfirmInput(String action) throws ThrowableAbort, ThrowableExit {
		String input = getInput(String.format("Press 'enter' to %s. Otherwise type 'abort'.", action));
		if(input.equals("")) {
			return true;
		}
		if(input.equals("abort")) {
			throw new ThrowableAbort();
		}
		return getAbortConfirmInput(action);
	}
	
	public String getSingleInput(String message) throws ThrowableAbort, ThrowableExit {
		String input = null;
		while(input==null) {
			try {
				input = getAbortableRepeatableInput(message);
				getConfirmInput();
			} catch (ThrowableRepeat e) {
				input = null;
				output(Message.getRepeatingMessage());
			}catch (Exception e) {
				input = null;
				output(Message.getWrongInputMessage());
			}
		}
		return input;
	}
	
	public void output(String message) {
		System.out.println(message);
	}
	
	public void close() {
		sc.close();
	}
	
}