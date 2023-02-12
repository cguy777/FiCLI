package fiberous.fi;

/**
 * This class is returned by the {@link FiCLI} when processCommand() is called.
 * The int "state" will be set to reflect the type of command that was passed.
 * Depending on the FiCLI configuration, the state will be either VALID, INVALID, EXIT, or BACK (0, 1, 2, or 3).
 * The String "input" is the command that was passed to the FiCLI object.
 * @author noahm
 */

public class FiState {
	public final static int VALID = 0;
	public final static int INVALID = 1;
	public final static int EXIT = 2;
	public final static int BACK = 3;
	
	/**
	 * The state returned from the system based off of the command that was received.
	 */
	public int state;
	/**
	 * The input that was passed as a command.
	 */
	public String input;
	
	public FiState(int state, String input) {
		this.state = state;
		this.input = input;
	}
}