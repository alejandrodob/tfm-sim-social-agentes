package agent.behavior;

import model.SimpleWorld;
import agent.DemographicItem;
import agent.Person;

public abstract class BasicDemographicBehavior implements BehaviorModule {

	@Override
	public void behave(DemographicItem person, SimpleWorld environment) {
		age(person);
		haveChild((Person) person,environment);
		migrate((Person) person,environment);
		die((Person) person,environment);
	}

	protected void age(DemographicItem person) {
		person.setAge(person.getAge() + 1);
	}

	protected abstract void haveChild(DemographicItem person, SimpleWorld environment);

	protected abstract void die(DemographicItem person, SimpleWorld environment);

	protected abstract void migrate(DemographicItem person, SimpleWorld environment);

}
