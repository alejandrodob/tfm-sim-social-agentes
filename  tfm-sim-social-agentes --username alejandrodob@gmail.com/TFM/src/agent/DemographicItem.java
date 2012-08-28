package agent;

import model.SimpleWorld;
import sim.engine.SimState;
import sim.engine.Steppable;
import sim.engine.Stoppable;
import sim.util.Int2D;
import agent.behavior.Behavior;
import agent.behavior.BehaviorModule;
import field.AbstractField2D;

public class DemographicItem implements Steppable {

	protected Int2D location;
	protected Behavior behavior; //could be an empty container, if needed
	protected Stoppable stop; //call stop.stop() to remove this agent from the simulation
	protected AbstractField2D field = null; //the field in where the agent moves (or stays)
	protected int ageInYears;
	protected int ageInSimulationSteps;
	protected final static int stepsPerYear = 50;//maybe should be in the class World, as it is a general parameter of the simulation

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

	public AbstractField2D getField() {
		return field;
	}

	public void setField(AbstractField2D field) {
		this.field = field;
	}
	
	public int getAge() {
		return ageInYears;
	}

	public void setAge(int age) {
		ageInYears = age;
	}
	
	public int getSteps() {
		return ageInSimulationSteps;
	}

	@Override
	public void step(SimState state) {
		behavior.behave(this,(SimpleWorld) state);
	}

	public void addBehaviorModule(BehaviorModule behaviorMod) {
		behavior.addBehaviorMod(behaviorMod);
	}

	public void removeBehaviorModule(BehaviorModule behaviorMod) {
		behavior.removeBehaviorMod(behaviorMod);
	}

}
