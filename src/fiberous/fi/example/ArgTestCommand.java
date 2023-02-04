package fiberous.fi.example;

import fiberous.fi.Command;

public class ArgTestCommand extends Command {

	public ArgTestCommand(String commandString, boolean acceptsArguments) {
		super(commandString);
	}
	
	@Override
	public void execute() {
		System.out.println("Arguments: ");
		
		for(int i = 0; i < arguments.size(); i++) {
			System.out.print(arguments.get(i) + ", ");
		}
	}
}
