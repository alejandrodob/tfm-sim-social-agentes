package pruebaMADAM;

import pruebaMADAM.MadamPerson.Caracteristicas;
import agent.Person;
import agent.behavior.Behavior;
import sim.util.Int2D;
import ec.util.MersenneTwisterFast;

public class Man extends MadamPerson {
	
	private Caracteristicas caracteristicas;
	private int nivelExigencia;
	private int edadMuerte;
	
	public Man(Int2D location, int age, boolean coupled, int totalCaract, int numCarac) {
		super(location, age, coupled, false, numCarac, numCarac);
		behavior.addBehaviorMod(DemographicBehavior.getInstance(),1);
	}
	
	public Man(Int2D location, int age, boolean coupled, Caracteristicas caract) {
		super(location, age, coupled, false, caract);
		behavior.addBehaviorMod(DemographicBehavior.getInstance(),1);
	}

}
