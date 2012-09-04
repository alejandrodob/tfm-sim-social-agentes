package model.demography;

import agent.DemographicItem;

public interface Fertility {
	
	public double crudeBirthRate();
	
	public double generalFertilityRate();
	
	public double birthProbability();

	public double ageSpecificFertilityRate(int age);

	public double birthProbability(DemographicItem female);
	
	public int meanAgeOfMother();
	
	public int meanNumberOfChildrenPerWoman();
	
	public int meanTimeBetweenBirths();
	
	public double orderSpecificFertilityRate(int order);
	
	public boolean newChild(DemographicItem female);
	
	public double femaleBirthProbability();
	
}
