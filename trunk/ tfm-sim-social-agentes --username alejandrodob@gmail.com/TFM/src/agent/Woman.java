package agent;

import agent.behavior.ListBehavior;
import agent.social.Kinship;
import sim.util.Int2D;

public class Woman extends Person {

	public Woman() {
		super();
	}

	public Woman(Int2D location, int age, boolean coupled,
			SocioeconomicLevel socLevel, Education education) {
		super(location, new ListBehavior(), age, coupled, socLevel, education);
	}
	
	//these methods are here because Person is a more general purpose class, maybe one should
	//want a simulation with no gender distinction, and so there would be no divorces/marriages
	@Override
	public void divorce() { 
		if (isCoupled()) {
			family.removeMember(family.couple());
			setCoupled(false);
		}
	}

	@Override
	public void marry(Person partner) {
		family.addMember(partner,Kinship.HUSBAND);
		setCoupled(true);
	}
	
}
