package fiberous.fi.example;

import java.util.Scanner;

import fiberous.fi.FiCommand;
import fiberous.fi.FiInputParser;
import fiberous.fi.FiParserState;

/**
 * This command enters you into a menu.
 * @author noahm
 *
 */
public class NestedCommands extends FiCommand {

	public NestedCommands(String commandString) {
		super(commandString);
		
		commandDescription = "Puts you into a menu with more commands";
	}

	@Override
	public void execute() {
		//Creating a new parser
		//'?' is the default command listing string.
		FiInputParser nestedParser = new FiInputParser();
		
		//Allow the EXIT and BACK states to be returned.
		//If not set, only the EXIT state is allowed by default.
		nestedParser.allowAdditionalStates(true, true);
		
		nestedParser.addCommand(new AddCommand("add"));
		nestedParser.setCaret("Menu > ");
		
		while(true) {
			FiParserState state = nestedParser.processCommand();
			
			if(state == FiParserState.EXIT)
				System.exit(0);
			
			//Exit out of this input loop if the back state is returned.
			if(state == FiParserState.BACK)
				break;
			
			if(state == FiParserState.INVALID)
				System.out.println("Invalid command");
		}
	}

}
