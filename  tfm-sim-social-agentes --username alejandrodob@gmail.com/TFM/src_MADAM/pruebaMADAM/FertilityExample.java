package pruebaMADAM;

import model.demography.Fertility;
import agent.DemographicItem;
import ec.util.MersenneTwisterFast;

public class FertilityExample implements Fertility {

	//SINGLETON

	private static MersenneTwisterFast random = new MersenneTwisterFast();
	
	private int meanNumberOfChildren = 3;
	
	public int getMeanNumberOfChildren() {
		return meanNumberOfChildren;
	}

	public void setMeanNumberOfChildren(int meanNumberOfChildren) {
		this.meanNumberOfChildren = meanNumberOfChildren;
	}



	public FertilityExample() {}
	
	

	@Override
	public double birthProbability(DemographicItem female) {

		return Math.exp(-0.0875*female.getAge());
	}
	
	@Override
	public boolean newChild(DemographicItem female) {
		//si es mujer, est� en edad fertil, est� casada puede que tenga un hijo
		if ((female.getAge() >= 16)
				&& (female.getAge() <= 50))
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
		return 3;
	}

	@Override
	public int meanTimeBetweenBirths() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double ageSpecificFertilityRate(int age) {
		return Math.exp(-0.0875*age);
	}

	@Override
	public double orderSpecificFertilityRate(int order) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double femaleBirthProbability() {
		//suppose there are 106 male births every 100 female births
		return 100/206;
	}



}
