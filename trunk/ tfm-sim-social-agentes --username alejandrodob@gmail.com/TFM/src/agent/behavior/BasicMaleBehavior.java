package agent.behavior;

import model.SimpleWorld;
import agent.DemographicItem;

public class BasicMaleBehavior extends BasicDemographicBehavior {

	public BasicMaleBehavior() {
	}

	@Override
	protected void haveChild(DemographicItem person, SimpleWorld environment) {
		// TODO Auto-generated method stub
		// do nothing, you're a man.
		// Or maybe you don't want your wife to have a child and you can avoid
		// it (in a machism social study or whatever)
	}

	@Override
	protected void die(DemographicItem person, SimpleWorld environment) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void migrate(DemographicItem person, SimpleWorld environment) {
		// TODO Auto-generated method stub

	}

}
