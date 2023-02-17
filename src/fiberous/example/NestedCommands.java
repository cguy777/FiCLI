package fiberous.example;

import fiberous.fi.FiCommand;
import fiberous.fi.FiCLI;
import fiberous.fi.FiState;

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
		FiCLI nestedParser = new FiCLI();
		
		//Allow the EXIT and BACK states to be returned.
		//If not set, only the EXIT state is allowed by default.
		nestedParser.allowAdditionalStates(false, true);
		
		nestedParser.addCommand(new AddCommand("add"));
		nestedParser.setCaret("Menu > ");
		
		while(true) {
			FiState state = nestedParser.processCommand();
			
			//Exit out of this input loop if the back state is returned.
			if(state.state == FiState.BACK)
				break;
			
			if(state.state == FiState.INVALID)
				System.out.println('"' + state.input + '"' + " is an invalid command");
		}
	}

}
