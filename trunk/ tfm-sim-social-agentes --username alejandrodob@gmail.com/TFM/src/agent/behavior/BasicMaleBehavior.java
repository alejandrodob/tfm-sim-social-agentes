package agent.behavior;

import model.World;
import agent.Person;

public class BasicMaleBehavior extends BasicDemographicBehavior {

	public BasicMaleBehavior() {
	}

	@Override
	protected void haveChild(Person person, World environment) {
		// TODO Auto-generated method stub
		// do nothing, you're a man.
		// Or maybe you don't want your wife to have a child and you can avoid
		// it (in a machism social study or whatever)
	}

	@Override
	protected void die(Person person, World environment) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void migrate(Person person, World environment) {
		// TODO Auto-generated method stub

	}

}
