package environment;

import agent.DemographicItem;

public interface Natality {

	public double birthProbability(DemographicItem female);
	
	public double birthProbability();
	
	public int multipleBirth(DemographicItem female);
	
	public boolean newChild(DemographicItem female);

}
