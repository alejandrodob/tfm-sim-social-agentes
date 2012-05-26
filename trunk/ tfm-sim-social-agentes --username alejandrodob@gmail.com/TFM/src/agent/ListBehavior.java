package agent;

import java.util.ArrayList;

public class ListBehavior implements Behavior {
	
	private ArrayList<BehaviorModule> behaviors;
	

	public ListBehavior() {
		behaviors = new ArrayList<BehaviorModule>();
	}

	@Override
	public void addBehavior(BehaviorModule behavior) {
		// TODO Auto-generated method stub
		behaviors.add(behavior);
	}

	@Override
	public void removeBehavior(BehaviorModule behavior) {
		// TODO Auto-generated method stub
		behaviors.remove(behavior);
	}

	@Override
	public void behave(DemographicItem individual) {
		// TODO Auto-generated method stub
		for (BehaviorModule b : behaviors)
			b.behave(individual);
		if (behaviors.isEmpty())
			System.out.println("Agent "+individual.toString()+" doesn't have any BehaviorModules so he does nothing!!");
	}

}
