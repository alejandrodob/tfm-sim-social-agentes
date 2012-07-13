package agent.behavior;

import model.SimpleWorld;
import agent.DemographicItem;

public interface BehaviorModule {

	public void behave(DemographicItem individual, SimpleWorld environment);

}
