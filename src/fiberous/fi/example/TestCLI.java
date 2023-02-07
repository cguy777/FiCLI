package fiberous.fi.example;

import java.util.Scanner;

import fiberous.fi.FiInputParser;
import fiberous.fi.FiParserState;
import fiberous.fi.FiSystemOutput;

/*
 * Quick example of how this works...
 */
public class TestCLI {
	
	public static void main(String[]args) {
		Scanner console = new Scanner(System.in);
		FiInputParser parser = new FiInputParser("?");
		
		//The parameters for ExitCommand are defined within the class constructor
		//But the command syntax is hidden, so this is not my preference...
		parser.addCommand(new ArgTestCommand("args"));
		parser.addCommand(new DisplayCommand("get record"));
		parser.addCommand(new NestedCommands("menu", console));
		
		while(true) {
			System.out.print("> ");
			
			FiParserState state = parser.doCommand(console.nextLine());
			
			//Check for the return states
			if(state == FiParserState.EXIT)
				System.exit(0);
			
			if(state == FiParserState.INVALID)
				System.out.println("Invalid command");
			
		}
	}
}
