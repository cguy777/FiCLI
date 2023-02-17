package fiberous.fi.example;

import fiberous.fi.FiCommand;

/**
 * This command just adds two arguments together.
 * @author noahm
 *
 */
class AddCommand extends FiCommand {

	public AddCommand(String commandString) {
		super(commandString);
		
		commandDescription = "Usage example: add 5 9";
	}

	@Override
	public void execute() {
		
		try {
			int a = Integer.parseInt(arguments.get(0));
			int b = Integer.parseInt(arguments.get(1));
		
			int sum = a + b;
		
			System.out.println(a + " + " + b + " = " + sum);
		} catch(Exception e) {
			System.out.println("Error while perfoming an addition operation!!!");
		}
	}

}
