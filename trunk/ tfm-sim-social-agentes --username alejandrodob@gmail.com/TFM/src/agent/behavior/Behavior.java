package agent.behavior;

import model.SimpleWorld;
import agent.DemographicItem;

public interface Behavior {

	public void addBehaviorMod(BehaviorModule behaviorMod);
	
	public void addBehaviorMod(BehaviorModule behaviorMod, int priority);// the lower priority is, the sooner its behave() method will be called

	public void removeBehaviorMod(BehaviorModule behaviorMod);

	public void behave(DemographicItem individual, SimpleWorld environment);

	public boolean isEmptyBehavior();
	
	public Iterable<BehaviorModule> getBehaviors();

}
