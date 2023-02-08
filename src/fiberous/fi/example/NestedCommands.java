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
	
	Scanner console;

	public NestedCommands(String commandString, Scanner s) {
		super(commandString);
		console = s;
		
		commandDescription = "Puts you into a menu with more commands";
	}

	@Override
	public void execute() {
		FiInputParser nestedParser = new FiInputParser("?");
		
		//Allow the EXIT and BACK states to be returned.
		nestedParser.allowAdditionalStates(true, true);
		
		nestedParser.addCommand(new AddCommand("add"));
		
		while(true) {
			System.out.print("Menu > ");
			
			FiParserState state = nestedParser.doCommand(console.nextLine());
			
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
