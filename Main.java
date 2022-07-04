
public class Main {

	public static void main(String[] args) {
		for (int j=0; j<30;j++){
		try {
			new Swarm().execute();
		} catch (Exception e) {
			StdOut.printf("%s\n%s", e.getMessage(), e.getCause());
		}
	}
	}
}