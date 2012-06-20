package pruebaMADAM;

import pruebaMADAM.HombrePrueba.Caracteristicas;
import ec.util.MersenneTwisterFast;
import agent.Woman;

public class MujerPrueba extends Woman {

	private Caracteristicas caracteristicas;
	private int nivelExigencia;
	
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
}
