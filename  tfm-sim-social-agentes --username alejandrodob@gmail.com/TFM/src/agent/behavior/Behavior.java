package agent.behavior;

import model.World;
import agent.DemographicItem;

public interface Behavior {

	public void addBehaviorMod(BehaviorModule behaviorMod);

	public void removeBehaviorMod(BehaviorModule behaviorMod);

	public void behave(DemographicItem individual, World environment);

	public boolean isEmptyBehavior();
	
	public Iterable<BehaviorModule> getBehaviors();

}
