package pruebaMADAM;

import agent.DemographicItem;
import ec.util.MersenneTwisterFast;
import environment.Fertility;

public class FertilityExample implements Fertility {

	//SINGLETON

	private static MersenneTwisterFast random = new MersenneTwisterFast();
	private static FertilityExample INSTANCE = new FertilityExample();
	
	private FertilityExample() {}
	
	public static FertilityExample getInstance() {
		return INSTANCE;
	}
	

	@Override
	public double birthProbability(DemographicItem female) {
		MujerPrueba woman = (MujerPrueba) female;
		return Math.exp(-0.0875*woman.getAge());
	}
	
	@Override
	public boolean newChild(DemographicItem female) {
		//si es mujer, est� en edad fertil, est� casada puede que tenga un hijo
		MujerPrueba woman = (MujerPrueba) female;
		if ((woman instanceof MujerPrueba) && (woman.getAge() >= 16)
				&& (woman.getAge() <= 50) && woman.isCoupled()
				&& woman.numHijos() < woman.getNumHijosMax())
			return (random.nextBoolean(birthProbability(female)));//no muy realista, pero bueno
		return false;
	}

	@Override
	public double birthProbability() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double crudeBirthRate() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double generalFertilityRate() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int meanAgeOfMother() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int meanNumberOfChildrenPerWoman() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int meanTimeBetweenBirths() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double ageSpecificFertilityRate(int age) {
		// TODO Auto-generated method stub
		return 0;
	}



}
