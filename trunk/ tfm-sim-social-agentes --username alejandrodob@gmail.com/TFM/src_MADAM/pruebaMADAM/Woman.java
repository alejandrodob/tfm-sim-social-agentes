package pruebaMADAM;

import pruebaMADAM.MadamPerson.Caracteristicas;
import agent.Person;
import agent.behavior.Behavior;
import sim.util.Int2D;
import ec.util.MersenneTwisterFast;

public class Woman extends MadamPerson {

	private int numHijosMax = 3;
	
	public Woman(Int2D location, int age, boolean coupled, int totalCaract, int numCarac) {
		super(location, age, coupled, true, numCarac, numCarac);
		behavior.addBehaviorMod(FemaleDemographicBehavior.getInstance(), 1);
	}
	
	public Woman(Int2D location, int age, boolean coupled, Caracteristicas caract) {
		super(location, age, coupled, true, caract);
		behavior.addBehaviorMod(FemaleDemographicBehavior.getInstance(), 1);
	}
	
	
	public int getNumHijosMax() {
		return numHijosMax;
	}

	public void setNumHijosMax(int numHijosMax) {
		this.numHijosMax = numHijosMax;
	}
	
	public int numHijos() {
		return family.sons().size();
	}

}
