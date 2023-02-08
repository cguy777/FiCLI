package fiberous.fi;

import java.util.ArrayList;

/**
 * Parses text input, and executes commands accordingly.
 * Add {@link FiCommand} objects via the addCommand() method.
 * Will list the available commands and their configured descriptions when the listCommandsString is input.
 * The listCommandsString is configurable via an appropriate constructor.
 * Output from this class can be directed to anything by creating an object that implements {@link FiOutputStream} and passing it through the appropriate constructor.
 * @author noahm
 *
 */
public class FiInputParser {
	
	private ArrayList<FiCommand> commands;
	private String listCommandsString;
	private FiOutputStream oStream;
	
	private String exitString = "exit";
	private String backString = "back";
	
	private String exitDescription = "Exits the application";
	private String backDescription = "Exits this menu";
	
	private boolean canUseExit = true;
	private boolean canUseBack = false;


	/**
	 * Creates a new InputParser with a configurable {@link FiOutputStream}.
	 * @param os The {@link FiOutputStream} object that this parser will use to directly output data.
	 * @param listCommandsString What input string should show all of the commands that have been configured.
	 */
	public FiInputParser(FiOutputStream os, String listCommandsString) {
		commands = new ArrayList<>();
		oStream = os;
		this.listCommandsString = listCommandsString;
	}
	
	/**
	 * Creates a new InputParser using the default {@link FiSystemOutput} {@link FiOutputStream}.
	 * @param listCommandsString What input string should show all of the commands that have been configured.
	 */
	public FiInputParser(String listCommandsString) {
		commands = new ArrayList<>();
		oStream = new FiSystemOutput();
		this.listCommandsString = listCommandsString;
	}
	
	/**
	 * Creates a new InputParser using the default {@link FiSystemOutput} {@link FiOutputStream} and a default listCommndsString of '?'.
	 */
	public FiInputParser() {
		commands = new ArrayList<>();
		oStream = new FiSystemOutput();
		listCommandsString = "?";
	}
	
	/**
	 * Adds a command that will be executed when input matches it's defined syntax.
	 * @param command
	 */
	public void addCommand(FiCommand command) {
		sortAndAddCommand(command);
	}
	
	/**
	 * Sets the list of commands through an {@linkplain ArrayList} containing {@link FiCommand} objects.
	 * @param commands
	 */
	public void setCommands(ArrayList<FiCommand> commands) {
		this.commands = commands;
	}
	
	/**
	 * Returns an ArrayList containing all of the currently configured commands.
	 * @return
	 */
	public ArrayList<FiCommand> getCommands() {
		return commands;
	}
	
	/**
	 * Removes all commands.
	 */
	public void clearCommands() {
		commands.clear();
	}
	
	/**
	 * Determines what String will return {@link FiParserState}.EXIT when doCommand() is called.
	 * This may back synonymous with the FiParserState.BACK state in certain contexts.
	 * In general, this state is used to determine if the application should exit.
	 * Using this method also automatically allows FiParserState.EXIT to be returned.
	 * @param s
	 */
	public void setExitString(String s) {
		exitString = s;
		canUseExit = true;
	}
	
	/**
	 * Determines what String will return {@link FiParserState}.BACK when doCommand() is called.
	 * This may back synonymous with the FiParserState.EXIT state in certain contexts.
	 * In general, this state is used to determine if this parser should close, and not the application itself.
	 * It is useful in menu systems where nested parsers are implemented.
	 * Using this method also automatically allows FiParserState.BACK to be returned.
	 * @param s
	 */
	public void setBackString(String s) {
		backString = s;
		canUseBack = true;
	}
	
	/**
	 * Determines if the doCommand method can return with FiParserState.EXIT or BACK.
	 * @param exit
	 * @param back
	 */
	public void allowAdditionalStates(boolean exit, boolean back) {
		canUseExit = exit;
		canUseBack = back;
	}
	
	/**
	 * Sets the description that the exit command will show (when FiParserState.EXIT is allowed to be returned.)
	 * @param desc
	 */
	public void setExitDescription(String desc) {
		exitDescription = desc;
	}
	
	/**
	 * Sets the description that the back command will show (when FiParserState.BACK is allowed to be returned.)
	 * @param desc
	 */
	public void setBackDescription(String desc) {
		backDescription = desc;
	}
	
	/**
	 * Checks a string that is passed and attempts to match it against a stored command or other special command.
	 * If the string is a valid command, it executes the command, and returns true.
	 * If not, nothing happens and it returns false, which allows you to create your own error handling.
	 * @return
	 */
	public FiParserState doCommand(String commandString) {
		
		//Check for the string that should list the commands
		if(commandString.compareTo(listCommandsString) == 0) {
			listCommands();
			return FiParserState.VALID;
		}
		
		//Check for the string that should generally exit the application, if allowed
		if(canUseExit && commandString.compareTo(exitString) == 0)
			return FiParserState.EXIT;
		
		//Check for the string that should generally exit this parser, if allowed
		if(canUseBack && commandString.compareTo(backString) == 0)
			return FiParserState.BACK;
		
		for(int i = 0; i < commands.size(); i++) {
			FiCommand command = commands.get(i);
			
			if(command.isCommand(commandString)) {
				command.execute();
				return FiParserState.VALID;
			}
		}
		
		//Returns false to allow for extensive error handling.
		return FiParserState.INVALID;
	}
	
	/**
	 * Returns the number of currently configured commands.
	 * @return
	 */
	public int numOfCommands() {
		return commands.size();
	}
	
	
	/**
	 * Lists all configured commands, plus additional, special commands, if allowed.
	 */
	private void listCommands() {
		oStream.print("\nAvailable Commands:\n");
		
		if(canUseExit)
			oStream.println(exitString + "\t" + exitDescription);
		
		if(canUseBack)
			oStream.println(backString + "\t" + backDescription);
		
		for(int i = 0; i < numOfCommands(); i++) {
			oStream.println(getCommands().get(i).commandString + "\t" + getCommands().get(i).commandDescription);
		}
	}
	
	/**
	 * Adds commands in alphabetical order.
	 * @param c
	 */
	private void sortAndAddCommand(FiCommand c) {
		
		if(commands.size() == 0) {
			commands.add(c);
			return;
		}
		
		for(int i = 0; i < commands.size(); i++) {
			if(c.commandString.compareTo(commands.get(i).commandString) < 0) {
				commands.add(i, c);
				return;
			}
		}
	}
}
