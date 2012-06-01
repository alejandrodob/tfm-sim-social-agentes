package agent.behavior;

import agent.DemographicItem;
import agent.Person;

public abstract class BasicSocialBehavior implements BehaviorModule{

	@Override
	public void behave(DemographicItem person) {
		meetPeople((Person) person);
		searchForMate((Person) person);
	}

	protected abstract void meetPeople(Person person);
	protected abstract void makeNewFriend(Person person);
	protected abstract void searchForMate(Person person);
	protected abstract void mate(Person person);
	
}
