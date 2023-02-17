package fiberous.fi.example;

import fiberous.fi.FiCommand;

/**
 * This command just displays something...
 * @author noahm
 *
 */
class DisplayCommand extends FiCommand {
	
	public DisplayCommand(String commandString) {
		super(commandString);
		
		commandDescription = "This command displays something";
	}

	//Put in here whatever you want to happen
	//We are displaying the record of some object...
	@Override
	public void execute() {
		System.out.println("Displaying a record of something...");

	}
	
}
