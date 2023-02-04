package fiberous.fi.example;

import java.util.Scanner;

import fiberous.fi.InputParser;
import fiberous.fi.SystemOutput;

/*
 * Quick example of how this works...
 */
public class TestCLI {
	
	public static void main(String[]args) {
		Scanner console = new Scanner(System.in);
		InputParser parser = new InputParser(new SystemOutput(), "?");
		
		//The parameters for ExitCommand are defined within the class constructor
		//But the command syntax is hidden, so this is not my preference...
		parser.addCommand(new DisplayCommand("get record", false));
		parser.addCommand(new ExitCommand());
		parser.addCommand(new ArgTestCommand("args", true));
		
		while(true) {
			if(!parser.doCommand(console.nextLine()))
				System.out.println("Invalid command");
		}
	}
}
