package fiberous.fi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Parses text input, and executes commands accordingly.
 * Add {@link Command} objects via the addCommand() method.
 * @author noahm
 *
 */
public class InputParser {
	
	private ArrayList<Command> commands;
	private String listCommandsString;
	private OutputStream oStream;


	/**
	 * Creates a new InputParser with a configurable {@link OutputStream}.
	 * @param os The {@link OutputStream} object that this parser will use to directly output data.
	 * @param listCommandsString What input string should show all of the commands that have been configured.
	 */
	public InputParser(OutputStream os, String listCommandsString) {
		commands = new ArrayList<>();
		oStream = os;
		this.listCommandsString = listCommandsString;
	}
	
	/**
	 * Creates a new InputParser using the default {@link SystemOutput} {@link OutputStream}.
	 * @param listCommandsString What input string should show all of the commands that have been configured.
	 */
	public InputParser(String listCommandsString) {
		commands = new ArrayList<>();
		oStream = new SystemOutput();
		this.listCommandsString = listCommandsString;
	}
	
	/**
	 * Creates a new InputParser using the default {@link SystemOutput} {@link OutputStream} and a default listCommndsString of '?'.
	 */
	public InputParser() {
		commands = new ArrayList<>();
		oStream = new SystemOutput();
		listCommandsString = "?";
	}
	
	/**
	 * Adds a command that will be executed when input matches it's defined syntax.
	 * @param command
	 */
	public void addCommand(Command command) {
		commands.add(command);
	}
	
	/**
	 * Sets the list of commands through an {@linkplain ArrayList} containing {@link Command} objects.
	 * @param commands
	 */
	public void setCommands(ArrayList<Command> commands) {
		this.commands = commands;
	}
	
	/**
	 * Returns an ArrayList containing all of the currently configured commands.
	 * @return
	 */
	public ArrayList<Command> getCommands() {
		return commands;
	}
	
	/**
	 * Removes all commands.
	 */
	public void clearCommands() {
		commands.clear();
	}
	
	/**
	 * Checks a string that is passed and attempts to match it against a stored command.
	 * If the string is a valid command, it executes the command, and returns true.
	 * If not, nothing happens and it returns false, which allows you to create your own error handling.
	 * @return
	 */
	public boolean doCommand(String commandString) {
		
		//Check for the string that should list the commands
		if(commandString.compareTo(listCommandsString) == 0) {
			listCommands();
			return true;
		}
		
		for(int i = 0; i < commands.size(); i++) {
			Command command = commands.get(i);
			
			if(command.isCommand(commandString)) {
				command.execute();
				return true;
			}
		}
		
		//Returns false to allow for extensive error handling.
		return false;
	}
	
	/**
	 * Returns the number of currenly configured commands.
	 * @return
	 */
	public int numOfCommands() {
		return commands.size();
	}
	
	
	
	private void listCommands() {
		
		oStream.print("\nAvailable Commands:\n");
		for(int i = 0; i < numOfCommands(); i++) {
			oStream.println(getCommands().get(i).commandString + "\t" + getCommands().get(i).commandDescription);
		}
	}
}
