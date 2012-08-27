package environment;

import agent.DemographicItem;

public interface Mortality {

	public double crudeDeathRate();
	
	public double ageSpecificDeathRate(int age); //death rate at persons specific age
	
	public double sexSpecificDeathRate(boolean man); // death rate for specific sex group. true if it's a man
	
	public double ageSexSpecificDeathRate(int age, boolean man);//for specific sex an age group
	
	public int lifeExpectancy(DemographicItem person); //number of years this person is expected to live from the present moment until he/she dies
	
	public int ageOfDeath(DemographicItem person); //the specific age the person will die at. usually, this value will be equal to the current person's age plus the value returned by lifeExpectancy
	
	public double deathProbability(DemographicItem person); //probability of dying at the end of current simulation step
	
	public boolean timeToDie(DemographicItem person);//whether this person will die at the end of current simulation step or not

}
