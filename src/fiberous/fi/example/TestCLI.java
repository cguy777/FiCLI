package fiberous.fi.example;

import fiberous.fi.FiInputParser;
import fiberous.fi.FiParserState;

/*
 * Quick example of how this works...
 */
public class TestCLI {
	
	public static void main(String[]args) {
		//Creating a parser using the defaults of System.in and System.out.
		//'?' is the default command listing string.
		//When typed, it will list all of the available commands.
		FiInputParser parser = new FiInputParser("?");
		
		//The commands will be added and sorted in alphabetical order
		parser.addCommand(new NestedCommands("menu"));
		parser.addCommand(new ArgTestCommand("args"));
		parser.addCommand(new DisplayCommand("get record"));
		
		//This command is not needed as this is the default caret.
		parser.setCaret("> ");
		
		while(true) {
			//Checks prints a caret and waits for input.
			FiParserState state = parser.processCommand();
			
			//Check for the return states
			if(state == FiParserState.EXIT)
				System.exit(0);
			
			if(state == FiParserState.INVALID)
				System.out.println("Invalid command");
			
		}
	}
}
