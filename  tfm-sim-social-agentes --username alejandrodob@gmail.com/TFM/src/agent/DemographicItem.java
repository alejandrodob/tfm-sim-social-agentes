package agent;

import agent.behavior.Behavior;
import agent.behavior.BehaviorModule;
import sim.engine.SimState;
import sim.engine.Steppable;
import sim.engine.Stoppable;
import sim.util.Int2D;

public class DemographicItem implements Steppable {

	private Int2D location;
	private Behavior behavior;
	private Stoppable stop; //call stop.stop() to remove this agent from the simulation

	public DemographicItem() {
		super();
	}

	public DemographicItem(Int2D location, Behavior behavior) {
		super();
		this.location = location;
		this.behavior = behavior;
	}

	public Int2D getLocation() {
		return location;
	}

	public void setLocation(Int2D location) {
		this.location = location;
	}

	public Behavior getBehavior() {
		return behavior;
	}

	public void setBehavior(Behavior behavior) {
		this.behavior = behavior;
	}

	public void setStop(Stoppable stop) {
		this.stop = stop;
	}

	public Stoppable getStop() {
		return stop;
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