package agent;

public interface Behavior {
		
	public void addBehavior(BehaviorModule behavior);
	public void removeBehavior(BehaviorModule behavior);
	public void behave(DemographicItem individual);

}
