
public class Particle extends Problem {

	private int[] x = new int[nVars];
	private int[] p = new int[nVars];
	private double[] v = new double[nVars];

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

	//Se crea el metodo para poder utilizar la ubicación de c1, se llama desde swarm.
/*	
	protected int maximo() {
		int v_maximo = weight[0]; // Declaramos e inicializamos el máximo.
	
		for (int i = 0; i < weight.length(); i++){
			if (v_maximo < weight[i])
				v_maximo = weight[i];
		}
		return v_maximo;
	}
*/

	//Se crea el metodo para poder utilizar la ubicación de c1, se llama desde swarm.
/*
	protected int minimo() {
		int v_minimo = weight[0]; // Declaramos e inicializamos el máximo.
	
		for (int i = 0; i < weight.length(); i++){
			if (v_minimo > weight[i])
				v_minimo = weight[i];
		}
		return v_minimo;
	}
*/


	protected void move(Particle g, int ub, int lb, int maxIteracion) {
		//l1 es el mayor y el l2 el minimo de los registros
		//dentro de los registros de la matriz
			double c1=Math.pow(2*Math.E,Math.pow(-(4/maxIteracion),2));
			double c2=(Math.random())/10;
			double c3=(Math.random())/10;


		for (int j = 0; j < nVars; j++) {
			
			//F=1 corresponde al alimento dentro de la mejor posición.

			int f=1;

			for (int k=0;j<maxIteracion;k++){
				if (k < maxIteracion/2) {
					if (j==1){
						//Se considero en eliminar el alimento (F) el cual se encuentra en el paper, pagina 6/29
						if (c3 >=0.5){
							v[0] = f + c1*(ub-lb)*c2+lb;
						}
						else {
							v[0] = f - c1*(ub-lb)*c2+lb;
						}	
					}
						else {
						v[j] =(0.5)*(v[j]+v[j-1]);
					}
					x[j] = toBinary(x[j] + v[j]);
					//ecuación 3.4
				}else {
					x[k] = (x[k]+x[k-1])/2;
				}
			}
			/* Actualizar velocidad */
			//v[j] = v[j] * theta + alpha * StdRandom.uniform() * (g.p[j] - x[j]) + beta * StdRandom.uniform() * (p[j] - x[j]);
			/* Actualizar posicion */
			x[j] = toBinary(x[j] + v[j]);
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
		return String.format("optimal value: %d, fitness: %d, diff: %.1f, rpd: %.2f%%, p: %s", optimum(),
				computeFitnessPBest(), diff(), rpd(), showSolution());
	}
}