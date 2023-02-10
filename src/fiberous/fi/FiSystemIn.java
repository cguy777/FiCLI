package fiberous.fi;

import java.util.Scanner;

/**
 * A wrapper class for System.in using a {@link Scanner}.
 * Implements {@link FiInputStream}.
 * This is the default input stream.
 * @author noahm
 *
 */
public class FiSystemIn implements FiInputStream {
	
	Scanner console;
	
	public FiSystemIn() {
		console = new Scanner(System.in);
	}
	
	/**
	 * Just reads a line from the console
	 */
	@Override
	public String readLine() {
		return console.nextLine();
	}
	
}
