package fiberous.example;

import fiberous.fi.FiCommand;

/**
 * This command takes all of it's arguments and prints them out.
 * @author noahm
 *
 */
public class ArgTestCommand extends FiCommand {

	public ArgTestCommand(String commandString) {
		super(commandString);
		
		commandDescription = "Usage example: args -t test.txt";
	}
	
	@Override
	public void execute() {
		
		if(arguments.size() == 0) {
			System.out.println("No arguments were input!");
			return;
		}
		
		System.out.println("Arguments: ");
		
		for(int i = 0; i < arguments.size(); i++) {
			System.out.print(arguments.get(i) + ", ");
		}
		System.out.println();
	}
}
