package agent.behavior;

import java.util.ArrayList;

import model.SimpleWorld;

import agent.DemographicItem;

public class ListBehavior implements Behavior {

	private ArrayList<BehaviorModule> behaviors;

	public ListBehavior() {
		behaviors = new ArrayList<BehaviorModule>();
	}

	public ListBehavior(ArrayList<BehaviorModule> behaviors) {
		this.behaviors = behaviors;
	}

	@Override
	public void addBehaviorMod(BehaviorModule behavior) {
		behaviors.add(behavior);
	}

	@Override
	public void removeBehaviorMod(BehaviorModule behavior) {
		behaviors.remove(behavior);
	}

	@Override
	public void behave(DemographicItem individual, SimpleWorld environment) {
		for (BehaviorModule b : behaviors)
			b.behave(individual,environment);
		if (behaviors.isEmpty())
			System.out.println("Agent " + individual.toString()
					+ " doesn't have any BehaviorModules so he does nothing!!");
	}

	@Override
	public boolean isEmptyBehavior() {
		return behaviors.isEmpty();
	}

	@Override
	public Iterable<BehaviorModule> getBehaviors() {
		return behaviors;
	}

}
