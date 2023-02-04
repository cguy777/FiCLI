package fiberous.fi.example;

import fiberous.fi.Command;

public class DisplayCommand extends Command {
	
	public DisplayCommand(String commandString, boolean acceptsArguments) {
		super(commandString);
	}

	//Put in here whatever you want to happen
	//We are displaying the record of some object...
	@Override
	public void execute() {
		System.out.println("Displaying a record of some sort");

	}
	
}
