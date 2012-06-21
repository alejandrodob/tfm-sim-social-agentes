package agent;

import agent.behavior.ListBehavior;
import sim.util.Int2D;

public class Woman extends Person {

	public Woman() {
		super();
	}

	public Woman(Int2D location, int age, boolean coupled,
			SocioeconomicLevel socLevel, Education education) {
		super(location, new ListBehavior(), age, coupled, socLevel, education);
	}
	
	@Override
	public void divorce() { //this method goes here because Person is a more general purpose class, maybe one should want a simulation with no gender distinction, and so there would be no divorces
		if (isCoupled()) {
			family.removeMember(family.couple());
		}
	}

}
