package model.demography;

import agent.DemographicItem;

public interface Mobility {

	public int totalAmountOfEmigrants();
	
	public int totalAmountOfImmigrants();
	
	public double crudeEmigrationRate();
	
	public double crudeImmigrationRate();
	
	public double emigrationProbability(DemographicItem person);
	
}
