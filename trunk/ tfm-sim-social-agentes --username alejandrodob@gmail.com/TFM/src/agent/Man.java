package agent;

import agent.behavior.ListBehavior;
import sim.util.Int2D;

public class Man extends Person {

	public Man() {
		super();
	}

	public Man(Int2D location, int age, boolean coupled,
			SocioeconomicLevel socLevel, Education education) {
		super(location, new ListBehavior(), age, coupled, socLevel, education);
	}

}
