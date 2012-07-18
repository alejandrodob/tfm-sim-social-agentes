package pruebaMADAM;

import ec.util.MersenneTwisterFast;
import environment.Natality;
import agent.DemographicItem;
import agent.Person;

public class NatalityExample implements Natality {

	//SINGLETON

	private static MersenneTwisterFast random = new MersenneTwisterFast();
	private static NatalityExample INSTANCE = new NatalityExample();
	
	private NatalityExample() {}
	
	public static NatalityExample getInstance() {
		return INSTANCE;
	}
	

	@Override
	public double birthProbability(DemographicItem female) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public boolean newChild(DemographicItem female) {
		//si es mujer, est� en edad fertil, est� casada puede que tenga un hijo
		MujerPrueba woman = (MujerPrueba) female;
		if ((woman instanceof MujerPrueba) && (woman.getAge() >= 16)
				&& (woman.getAge() <= 50) && woman.isCoupled()
				&& woman.numHijos() < woman.getNumHijosMax())
			return (random.nextBoolean(Math.exp(-0.0875*woman.getAge())));//no muy realista, pero bueno
		return false;
	}

	@Override
	public double birthProbability() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int multipleBirth(DemographicItem female) {
		// TODO Auto-generated method stub
		return 0;
	}


}
