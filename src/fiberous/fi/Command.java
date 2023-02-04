package fiberous.fi;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * The base class which should be extended by all other commands.
 * You must override the {@link execute()} method to perform whatever action that is needed.
 * You must define the commandString (what input is needed to execute the command).
 * Once a new command class has been created, it must be instantiated and added to the {@link InputParser}.
 * Whenever input is passed to the InputParser, if it matches the class you created, the execute method will run.
 * @author noahm
 *
 */
public class Command {
	
	public String commandString;
	public ArrayList<String> arguments;
	public String commandDescription;
	
	/**
	 * You must determine what string this command will respond to (the commandString parameter) and whether this command accepts arguments or not (the acceptsArguments parameter).
	 * @param commandString
	 * @param acceptsArguments
	 */
	public Command(String commandString) {
		this.commandString = commandString;
		
		arguments = new ArrayList<>();
		commandDescription = "";
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
			
		//Cleanup from the last time the command was called.
		arguments.clear();
			
		//Check for obviously different commands and proceed accordingly
		if(cString.length() < commandString.length())
			return false;
			
		String compareString = cString.substring(0, commandString.length());
		
		//Compare the command string and scan for any arguments
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
			
			scanner.close();
			
			return true;
		} else
			return false;
	}
}
