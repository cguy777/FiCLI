package fiberous.fi;

/**
 * Basic {@link FiOutputStream} for System.out.print and System.out.println.
 * This is the default FiOutputStream for the {@link FiInputParser}.
 * @author noahm
 *
 */
public class FiSystemOut implements FiOutputStream {

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
