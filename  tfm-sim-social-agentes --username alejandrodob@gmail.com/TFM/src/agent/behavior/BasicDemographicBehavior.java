package agent.behavior;

import agent.DemographicItem;
import agent.Person;

public abstract class BasicDemographicBehavior implements BehaviorModule{
	
	@Override
	public void behave(DemographicItem person) {
		age((Person) person);
		haveChild((Person) person);
		migrate((Person) person);
		die((Person) person);
		System.out.println("me estoy comportando!!!");
	}
	protected void age(Person person) {
		person.setAge(person.getAge()+1);
	}
	
	protected abstract void haveChild(Person person);
	
	protected abstract void die(Person person);
	
	protected abstract void migrate(Person person);
	
	
}
