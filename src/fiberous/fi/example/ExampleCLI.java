package fiberous.fi.example;

import fiberous.fi.FiCLI;
import fiberous.fi.FiState;

/*
 * Quick example of how this works...
 */
class ExampleCLI {
	
	public static void main(String[]args) {
		//Creating a parser using the defaults of System.in and System.out.
		//'?' is the default command listing string.
		//When typed, it will list all of the available commands.
		FiCLI parser = new FiCLI("?");
		
		//The commands will be added and sorted in alphabetical order
		parser.addCommand(new NestedCommands("menu"));
		parser.addCommand(new ArgTestCommand("args"));
		parser.addCommand(new DisplayCommand("get record"));
		
		//This command is not needed as this is the default caret.
		parser.setCaret("> ");
		
		while(true) {
			//Creating an FiState object which can allow us to understand what the system saw.
			//It also allows us to see what the input was.
			//the .processCommand() prints the caret and then waits for input.
			FiState state = parser.processCommand();
			
			//Check for the return states
			if(state.state == FiState.EXIT)
				System.exit(0);
			
			if(state.state == FiState.INVALID)
				System.out.println('"' + state.input + '"' + " is an invalid command");
			
		}
	}
}
