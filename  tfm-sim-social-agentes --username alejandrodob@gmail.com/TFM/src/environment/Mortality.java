package environment;

import agent.DemographicItem;

public interface Mortality {

	public double annualDeathProbability(DemographicItem persona);
	
	public boolean timeToDie(DemographicItem persona);
	
	public int ageOfDeath(DemographicItem persona);
	
	

}
