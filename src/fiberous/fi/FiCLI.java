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
public class FiCLI {
	
	private ArrayList<FiCommand> commands;
	private String listCommandsString = "?";
	private FiOutputStream oStream;
	private FiInputStream iStream;
	
	private String exitString = "exit";
	private String backString = "back";
	
	private String exitDescription = "Exits the application";
	private String backDescription = "Exits this menu";
	
	private boolean canUseExit = true;
	private boolean canUseBack = false;
	
	//Set the default spacing to the default length of back and exit
	private int spacing = 9;
	
	private String caret = "> ";

	/**
	 * Creates a new InputParser with a configurable {@link FiOutputStream} and {@link FiInputStream}.
	 * @param os The {@link FiOutputStream} object that this parser will use to directly output data.
	 * @param listCommandsString What input string should show all of the commands that have been configured.
	 */
	public FiCLI(FiInputStream is, FiOutputStream os, String listCommandsString) {
		commands = new ArrayList<>();
		iStream = is;
		oStream = os;
		this.listCommandsString = listCommandsString;
	}
	
	/**
	 * Creates a new InputParser using the default {@link FiSystemOut} {@link FiOutputStream} and the default {@link FiSystemIn} {@link FiInputStream}.
	 * @param listCommandsString What input string should show all of the commands that have been configured.
	 */
	public FiCLI(String listCommandsString) {
		commands = new ArrayList<>();
		iStream = new FiSystemIn();
		oStream = new FiSystemOut();
		this.listCommandsString = listCommandsString;
	}
	
	/**
	 * Creates a new InputParser using the default {@link FiSystemOut} {@link FiOutputStream}, the default {@link FiSystemIn} {@link FiInputStream}, and a default listCommndsString of '?'.
	 */
	public FiCLI() {
		commands = new ArrayList<>();
		iStream = new FiSystemIn();
		oStream = new FiSystemOut();
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
	 * Determines what String will return {@link FiState}.EXIT when doCommand() is called.
	 * This may back synonymous with the FiState.BACK state in certain contexts.
	 * In general, this state is used to determine if the application should exit.
	 * Using this method also automatically allows FiState.EXIT to be returned.
	 * @param s
	 */
	public void setExitString(String s) {
		exitString = s;
		canUseExit = true;
		
		//Ensure our description spacing is correct
		if(exitString.length() + 5 > spacing)
			spacing = exitString.length() + 5;
	}
	
	/**
	 * Determines what String will return {@link FiState}.BACK when doCommand() is called.
	 * This may back synonymous with the FiState.EXIT state in certain contexts.
	 * In general, this state is used to determine if this parser should close, and not the application itself.
	 * It is useful in menu systems where nested parsers are implemented.
	 * Using this method also automatically allows FiState.BACK to be returned.
	 * @param s
	 */
	public void setBackString(String s) {
		backString = s;
		canUseBack = true;
		
		//Ensure our description spacing is correct
		if(backString.length() + 5 > spacing)
			spacing = backString.length() + 5;
	}
	
	/**
	 * Determines if the doCommand method can return with FiState.EXIT or BACK.
	 * @param exit
	 * @param back
	 */
	public void allowAdditionalStates(boolean exit, boolean back) {
		canUseExit = exit;
		canUseBack = back;
	}
	
	/**
	 * Sets the description that the exit command will show (when FiState.EXIT is allowed to be returned.)
	 * @param desc
	 */
	public void setExitDescription(String desc) {
		exitDescription = desc;
	}
	
	/**
	 * Sets the description that the back command will show (when FiState.BACK is allowed to be returned.)
	 * @param desc
	 */
	public void setBackDescription(String desc) {
		backDescription = desc;
	}
	
	/**
	 * Sets the caret.
	 * @param c
	 */
	public void setCaret(String c) {
		caret = c;
	}
	
	/**
	 * Sets the input system.
	 * @param fis
	 */
	public void setInput(FiInputStream fis) {
		iStream = fis;
	}
	
	/**
	 * Sets the output system.
	 * @param fos
	 */
	public void setOutput(FiOutputStream fos) {
		oStream = fos;
	}
	
	/**
	 * Outputs the caret, indicating that input is requested.
	 * Checks a string that is passed and attempts to match it against a stored command or other special command.
	 * If the string is a valid command, it executes the command, and returns true.
	 * If not, nothing happens and it returns false, which allows you to create your own error handling.
	 * @return
	 */
	public FiState processCommand() {
		
		oStream.print(caret);
		
		String commandString = iStream.readLine();
		
		//Check for the string that should list the commands
		if(commandString.compareTo(listCommandsString) == 0) {
			listCommands();
			return new FiState(FiState.VALID, commandString);
		}
		
		//Check for the string that should generally exit the application, if allowed
		if(canUseExit && commandString.compareTo(exitString) == 0)
			return new FiState(FiState.EXIT, commandString);
		
		//Check for the string that should generally exit this parser, if allowed
		if(canUseBack && commandString.compareTo(backString) == 0)
			return new FiState(FiState.BACK, commandString);;
		
		for(int i = 0; i < commands.size(); i++) {
			FiCommand command = commands.get(i);
			
			if(command.isCommand(commandString)) {
				command.execute();
				return new FiState(FiState.VALID, commandString);
			}
		}
		
		//Returns false to allow for extensive error handling.
		return new FiState(FiState.INVALID, commandString);
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
		oStream.println("\nAvailable Commands:");
		
		if(canUseExit) {
			oStream.println(exitString + addSpaces(exitString) + exitDescription);
		}
		
		if(canUseBack) {
			oStream.println(backString + addSpaces(backString) + backDescription);
		}
		
		for(int i = 0; i < numOfCommands(); i++) {
			oStream.println(getCommands().get(i).commandString + addSpaces(getCommands().get(i).commandString) + getCommands().get(i).commandDescription);
		}
	}
	
	/**
	 * Adds commands in alphabetical order.
	 * @param c
	 */
	private void sortAndAddCommand(FiCommand c) {
		
		//Set the maximum spacing to make all of the command descriptions line up.
		if(c.commandString.length() + 5 > spacing)
			spacing = c.commandString.length() + 5;
		
		for(int i = 0; i < commands.size(); i++) {
			if(c.commandString.compareTo(commands.get(i).commandString) < 0) {
				commands.add(i, c);
				return;
			}
		}
		
		commands.add(c);
	}
	
	/**
	 * Adds an equalizing amount of spacing so the command descriptions can all line up and look nice.
	 * @param cs The commandString
	 * @return
	 */
	private String addSpaces(String cs) {
		String additionalSpace = "";
		
		int spaces = spacing - cs.length();
		
		for(int i = 0; i < spaces; i++) {
			additionalSpace += " ";
		}
		
		return additionalSpace;
	}
}
