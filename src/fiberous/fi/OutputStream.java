package fiberous.fi;

/**
 * Implement an object using this interface so the {@link InputParser} can directly write to your preferred output.
 * @author noahm
 *
 */
public interface OutputStream {
	public void print(String s);
	public void println(String s);
}
