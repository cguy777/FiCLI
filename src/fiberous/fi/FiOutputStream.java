package fiberous.fi;

/**
 * Implement an object using this interface so the {@link FiCLI} can directly write to your preferred output.
 * @author noahm
 *
 */
public interface FiOutputStream {
	public void print(String s);
	public void println(String s);
}
