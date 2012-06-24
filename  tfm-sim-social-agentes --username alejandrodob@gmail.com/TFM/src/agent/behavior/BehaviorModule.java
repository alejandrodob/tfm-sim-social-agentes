package agent.behavior;

import model.World;
import agent.DemographicItem;

public interface BehaviorModule {

	public void behave(DemographicItem individual, World environment);

}
