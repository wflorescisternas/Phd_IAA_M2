public class Particle extends Problem {

	private int[] x = new int[nVars];
	private int[] p = new int[nVars];
	private double[] v = new double[nVars];

	//nVars es la cantidad de registros que tiene el problema (arreglo)

	public Particle() {
		for (int j = 0; j < nVars; j++) {
			x[j] = StdRandom.uniform(2);
			v[j] = 0;
		}
	}

	protected int computeFitness() {
		return computeFitness(x);
	}

	protected int computeFitnessPBest() {
		return computeFitness(p);
	}

	protected boolean isBetterThanPBest() {
		return computeFitness() < computeFitnessPBest();
	}

	protected boolean isBetterThan(Particle g) {
		return computeFitnessPBest() < g.computeFitnessPBest();
	}

	protected void updatePBest() {
		System.arraycopy(x, 0, p, 0, x.length);
	}

	protected boolean isFeasible() {
		return checkConstraint(x);
	}
	//protected void move(Particle g, double theta, double alpha, double beta)
	protected void move(int F, double c1, double c2, double c3, int ub, int lb) {
	
		for (int j = 0; j < nVars; j++) {
			if (j==0){
				// actualizar el lider de las salpas: ecuación 3.1
				// Consideramos F=1 solo por que la comida es el punto inicial.
				if (c3>=0){
					v[j]= F + c1 * ((ub - lb) * c2 + lb);
				} else {
					v[j]= F - c1 * ((ub - lb) * c2 + lb);
				}
			} else {
				//Actualizamos la posición de las salpas: ecuación 3.4
				v[j]=(0.5)*(v[j]+v[j-1]);
			}  
			/* Actualizar posicion */
			//x[j] = toBinary(x[j] + v[j]);
			x[j] = toBinary(v[j]);
		}
	}

	private float diff() {
		return computeFitness(p) - optimum();
	}

	private float rpd() {
		return diff() / optimum() * 100f;
	}

	private String showSolution() {
		return java.util.Arrays.toString(p);
	}

	private int toBinary(double x) {
		return StdRandom.uniform() <= (1 / (1 + Math.pow(Math.E, -x))) ? 1 : 0;
	}

	protected void copy(Object object) {
		if (object instanceof Particle) {
			System.arraycopy(((Particle) object).x, 0, this.x, 0, nVars);
			System.arraycopy(((Particle) object).p, 0, this.p, 0, nVars);
			System.arraycopy(((Particle) object).v, 0, this.v, 0, nVars);
		}
	}

	@Override
	public String toString() {
		//return String.format("Valor Optimo: %d, fitness: %d, diff: %.1f, rpd: %.2f%%, p: %s", optimum(),
		//		computeFitnessPBest(), diff(), rpd(), showSolution());
		return String.format("Valor_Optimo: %d, fitness: %d, diff: %.1f, rpd: %.2f%%", optimum(),
				computeFitnessPBest(), diff(), rpd());
	}
}