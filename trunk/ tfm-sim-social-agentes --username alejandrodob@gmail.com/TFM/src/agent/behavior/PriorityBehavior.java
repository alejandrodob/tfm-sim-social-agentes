package agent.behavior;

import java.util.ArrayList;
import java.util.PriorityQueue;

import model.SimpleWorld;
import agent.DemographicItem;

public class PriorityBehavior implements Behavior {
	
	private class OrderBehavior implements Comparable {
		BehaviorModule behavior;
		int priority;
		OrderBehavior(BehaviorModule behavior, int priority) {
			this.behavior = behavior;
			this.priority = priority;
		}
		@Override
		public int compareTo(Object other) {
			if (other instanceof OrderBehavior) {
				if (priority < ((OrderBehavior) other).priority) return -1;
				else if (priority == ((OrderBehavior) other).priority) return 0;
				else return 1;
			}
			return 0;
		}	
	}
	
	private PriorityQueue<OrderBehavior> behaviors;
	
	public PriorityBehavior() {
		behaviors = new PriorityQueue<OrderBehavior>();
	}

	public PriorityBehavior(PriorityQueue<OrderBehavior> behaviors) {
		this.behaviors = behaviors;
	}

	@Override
	public void addBehaviorMod(BehaviorModule behaviorMod) {
		//no specified priority, give neutral priority (zero)
		behaviors.offer(new OrderBehavior(behaviorMod, 0));
	}

	@Override
	public void addBehaviorMod(BehaviorModule behaviorMod, int priority) {
		behaviors.offer(new OrderBehavior(behaviorMod, priority));
	}

	@Override
	public void removeBehaviorMod(BehaviorModule behaviorMod) {
		for (OrderBehavior ob : behaviors) {
			if (ob.behavior.equals(behaviorMod)) {
				behaviors.remove(ob);
				break;
			}
		}
	}

	@Override
	public void behave(DemographicItem individual, SimpleWorld environment) {
		//invoke behave() method for each BehaviorModule in their priority order
		if (!behaviors.isEmpty()) {
			PriorityQueue<OrderBehavior> aux = new PriorityQueue<OrderBehavior>(behaviors);
			OrderBehavior ob;
			while(!aux.isEmpty()) {
				ob = aux.poll();
				ob.behavior.behave(individual, environment);
			}
		}
	}

	@Override
	public boolean isEmptyBehavior() {
		return behaviors.isEmpty();
	}

	@Override
	public Iterable<BehaviorModule> getBehaviors() {
		//returns behaviors in no particular order
		ArrayList<BehaviorModule> retVal = new ArrayList<BehaviorModule>();
		for(OrderBehavior b : behaviors) {
			retVal.add(b.behavior);
		}
		return retVal;
	}

}
