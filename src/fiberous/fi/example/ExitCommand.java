package fiberous.fi.example;

import fiberous.fi.Command;

public class ExitCommand extends Command {

	//the command parameters are defined here, in this case.
	public ExitCommand() {
		super("exit", false);
	}
	
	//Put in here whatever you want to happen
	//We are exiting the application, in this case.
	@Override
	public void execute() {
		System.exit(0);
	}

}
