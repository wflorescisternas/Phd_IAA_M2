public class Main {

	public static void main(String[] args) {
		for (int i=0; i<1;i++){
			try {
				new Swarm().execute();

			} catch (Exception e) {
				StdOut.printf("%s\n%s", e.getMessage(), e.getCause());
			}
		}
	}
}