package agent.behavior;

import model.World;
import agent.DemographicItem;
import agent.Person;

@Deprecated
public class BasicChildBehavior implements BehaviorModule {

	@Override
	public void behave(DemographicItem person, World environment) {
		age((Person) person);
		System.out.println(person.toString()
				+ "soy un niño me estoy comportando muy bien");
	}

	public void age(Person person) {
		person.setAge(person.getAge() + 1);
	}

	public void die(Person person) {

	}

}