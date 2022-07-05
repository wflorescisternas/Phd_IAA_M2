public class Main {

	public static void main(String[] args) {
		try {
			new Swarm().execute();
		} catch (Exception e) {
			StdOut.printf("%s\n%s", e.getMessage(), e.getCause());
		}
	}
}