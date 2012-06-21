package agent.behavior;

import agent.DemographicItem;
import agent.Person;

public abstract class BasicSocialBehavior implements BehaviorModule {

	@Override
	public void behave(DemographicItem me) {
		meetPeople((Person) me);
		searchForMate((Person) me);
	}

	protected abstract void meetPeople(Person me);

	protected abstract void makeNewFriend(Person friend, Person me);

	protected abstract void searchForMate(Person me);

	protected abstract void marry(Person partner, Person me);
	
	public abstract boolean acceptFriend(Person friend, Person me);
	
	public abstract boolean acceptMarriage(Person candidate, Person me);

}
