package pruebaMADAM;

import agent.Person;
import sim.util.Int2D;
import ec.util.MersenneTwisterFast;

public class HombrePrueba extends Person {
	
	private Caracteristicas caracteristicas;
	private int nivelExigencia;
	private int edadMuerte;
	
	public HombrePrueba(Int2D location, int age, boolean coupled) {
		super(location, null, age, coupled, false);
		caracteristicas = new Caracteristicas(40,20,new MersenneTwisterFast());
		nivelExigencia = caracteristicas.numCaract-1;
		edadMuerte = 100*stepsPerYear;
	}
	
	class Caracteristicas {
		
		int totalCaract;
		int numCaract; //numCaract<=totalCaract
		private boolean[] cuales; //representamos cuales de entre todas las caracteristicas posee
		
		public Caracteristicas(){}
		
		public Caracteristicas(int total, int num, MersenneTwisterFast random) throws IllegalArgumentException{
			if (total>=num) {
				totalCaract = total;
				numCaract = num;
				cuales = new boolean[total];
				for (int i=1;i<total;i++){
					cuales[i] = false;
				}
				int llenar = 0;
				while (llenar<num){
					int i = random.nextInt(total);
					if (!cuales[i]){
						cuales[i] = true;
						llenar++;
					}
				}
			}
			else throw new IllegalArgumentException("el numero de caracteristicas que tiene cada individuo no puede superar al total");
		}
		
		public boolean[] getCaracteristicas(){
			return cuales;
		}

	}

	public Caracteristicas getCaracteristicas() {
		return caracteristicas;
	}

	public void setCaracteristicas(Caracteristicas caracteristicas) {
		this.caracteristicas = caracteristicas;
	}

	public int getNivelExigencia() {
		return nivelExigencia;
	}

	public void setNivelExigencia(int nivelExigencia) {
		this.nivelExigencia = nivelExigencia;
	}

	public int getEdadMuerte() {
		return edadMuerte;
	}
	
	public void setEdadMuerte(int edadMuerte) {
		this.edadMuerte = edadMuerte;
	}

}
