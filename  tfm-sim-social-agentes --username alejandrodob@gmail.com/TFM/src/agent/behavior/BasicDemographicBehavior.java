package agent.behavior;

import model.World;
import agent.DemographicItem;
import agent.Person;

public abstract class BasicDemographicBehavior implements BehaviorModule {

	@Override
	public void behave(DemographicItem person, World environment) {
		age((Person) person);
		haveChild((Person) person,environment);
		migrate((Person) person,environment);
		die((Person) person,environment);
	}

	protected void age(Person person) {
		person.setAge(person.getAge() + 1);
	}

	protected abstract void haveChild(Person person, World environment);

	protected abstract void die(Person person, World environment);

	protected abstract void migrate(Person person, World environment);

}
