package pruebaMADAM;

import environment.NatalityExample;
import agent.Person;
import agent.behavior.BasicDemographicBehavior;

public class DemographicBehavior extends BasicDemographicBehavior {

	@Override
	protected void haveChild(Person person) {
		// Una vez al a�o ( o dos, lo voy cambiando)
		if (person.getSteps()%100 == 0) {
			NatalityExample nat = NatalityExample.getInstance();
			if (nat.newChild(person)) {
				
			}
		}
	}

	
	protected void naceBebe(Mundo mundo){
		Sexo sexo;
		if (mundo.random.nextBoolean()){
			sexo = Sexo.F;
		}
		else sexo = Sexo.M;
		Individuo bebe = new Individuo(0,sexo,new Caracteristicas(caracteristicas.totalCaract,caracteristicas.numCaract,mundo.random));
		mundo.registraNacimiento(bebe,this);
		numHijos++;
	}

	@Override
	protected void die(Person person) {
		// Y si la Muerte ha de llev�rselo, que as� sea
		if (edad > edadMuerte) 
			muere(mundo);
		// Cada 5 a�os se actualiza su probabilidad de morir en los pr�ximos 5
		int edadDespues = getEdad_Real();
		if (cambiaEdadMuerte(edadAntes,edadDespues))
			edadMuerte = DistribMuertes.calculaEdadMuerte(edadDespues);		
	}
	
	protected boolean cambiaEdadMuerte(int edadAntes, int edadDespues){
		return (edadAntes == 0 && edadDespues == 1) || (edadAntes%5 == 4 && edadDespues%5 == 0);
	}

	@Override
	protected void migrate(Person person) {
		// TODO Auto-generated method stub
		
	}

}
