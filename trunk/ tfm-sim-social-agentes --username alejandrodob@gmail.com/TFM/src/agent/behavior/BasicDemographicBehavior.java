package agent.behavior;

import agent.DemographicItem;
import agent.Person;

public abstract class BasicDemographicBehavior implements BehaviorModule{
	
	@Override
	public void behave(DemographicItem person) {
		Person p = (Person) person;
		age(p);
		haveChild(p);
		migrate(p);
		die(p);
		System.out.println(p.toString()+"tengo "+p.getAge());
	}
	protected void age(Person person) {
		person.setAge(person.getAge()+1);
	}
	
	protected abstract void haveChild(Person person);
	
	protected abstract void die(Person person);
	
	protected abstract void migrate(Person person);
	
	
}