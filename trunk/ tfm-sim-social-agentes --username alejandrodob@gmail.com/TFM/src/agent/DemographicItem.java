package agent;

import agent.behavior.Behavior;
import agent.behavior.BehaviorModule;
import sim.engine.SimState;
import sim.engine.Steppable;
import sim.util.MutableInt2D;

public class DemographicItem implements Steppable {
	
	private MutableInt2D location;
	private Behavior behavior;
	
	public DemographicItem() {
		super();
	}

	public DemographicItem(MutableInt2D location, Behavior behavior) {
		super();
		this.location = location;
		this.behavior = behavior;
	}

	public MutableInt2D getLocation() {
		return location;
	}

	public void setLocation(MutableInt2D location) {
		this.location = location;
	}

	public Behavior getBehavior() {
		return behavior;
	}

	public void setBehavior(Behavior behavior) {
		this.behavior = behavior;
	}

	@Override
	public void step(SimState state) {
		behavior.behave(this);
	}

	public void addBehaviorModule(BehaviorModule behaviorMod) {
		behavior.addBehaviorMod(behaviorMod);
	}

	public void removeBehaviorModule(BehaviorModule behaviorMod) {
		behavior.removeBehaviorMod(behaviorMod);
	}

	
}
