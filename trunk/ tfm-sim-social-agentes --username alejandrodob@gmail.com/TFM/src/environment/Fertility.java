package environment;

import agent.DemographicItem;

public interface Fertility {

	public double birthProbability(DemographicItem female);
	
	public double birthProbability();
	
	public int multipleBirth(DemographicItem female);
	
	public boolean newChild(DemographicItem female);

}
