package fiberous.fi;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * The base class which should be extended by all other commands.
 * You must override the {@link execute()} method to perform whatever action that is needed.
 * You must define the commandString (what input is needed to execute the command).
 * You must also define the acceptsArguments boolean (whether it should look for information beyond the command itself.
 * Once a new command class has been created, it must be instantiated and added to the {@link InputParser}.
 * Whenever input is passed to the InputParser, if it matches the class you created, the execute method will run.
 * @author noahm
 *
 */
public class Command {
	
	public String commandString;
	public ArrayList<String> arguments;
	public String commandDescription;
	public boolean acceptsArguments;
	
	/**
	 * You must determine what string this command will respond to (the commandString parameter) and whether this command accepts arguments or not (the acceptsArguments parameter).
	 * @param commandString
	 * @param acceptsArguments
	 */
	public Command(String commandString, boolean acceptsArguments) {
		this.commandString = commandString;
		this.acceptsArguments = acceptsArguments;
		
		arguments = new ArrayList<>();
		commandDescription = "Please fill out the description!";
	}
	
	/**
	 * Override this command to perform whatever function is needed.
	 */
	public void execute() {}
	
	/**
	 * Determines if the command that was passed matches the defined command string.
	 * Defines the arguments String if it accepts arguments and contains arguments.
	 * @param cString
	 * @return
	 */
	public boolean isCommand(String cString) {
		//If we don't accept arguments, just compare the two strings
		if(!acceptsArguments) {
			//Using compareTo to avoid some weird regex things
			if(this.commandString.compareTo(cString) == 0)
				return true;
			else
				return false;
		} else {
			
			//Cleanup from the last time the command was called.
			arguments.clear();
			
			//Check for obviously different commands and proceed accordingly
			if(cString.length() < commandString.length())
				return false;
			
			String compareString = cString.substring(0, commandString.length());
			
			if(compareString.compareTo(commandString) == 0) {
				String argumentsString = cString.substring(commandString.length());
				Scanner scanner = new Scanner(argumentsString);
				scanner.useDelimiter(" ");
				
				while(true) {
					if(scanner.hasNext())
						arguments.add(scanner.next());
					else
						break;
				}
				
				return true;
			} else
				return false;
		}
	}
}
