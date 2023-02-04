package fiberous.fi;

import fiberous.fi.OutputStream;

/**
 * Basic {@link OutputStream} for System.out.print() and System.out.println()
 * @author noahm
 *
 */
public class SystemOutput implements OutputStream {

	/**
	 * Wrapper for System.out.print()
	 */
	@Override
	public void print(String s) {
		System.out.print(s);

	}

	/**
	 * Wrapper for System.out.println()
	 */
	@Override
	public void println(String s) {
		System.out.println(s);

	}

}
