package agent.behavior;

import agent.DemographicItem;

public interface Behavior {

	public void addBehaviorMod(BehaviorModule behaviorMod);

	public void removeBehaviorMod(BehaviorModule behaviorMod);

	public void behave(DemographicItem individual);

	public boolean isEmptyBehavior();

}
