package fiberous.example;

import fiberous.fi.FiCommand;

public class HiddenCommand extends FiCommand {

	public HiddenCommand(String commandString) {
		super(commandString);
		isVisible = false;
	}

	@Override
	public void execute() {
		System.out.println("This is a hidden command.");
		System.out.println("Use this functionality if you don't want a command listed for some reason.");
	}

}
