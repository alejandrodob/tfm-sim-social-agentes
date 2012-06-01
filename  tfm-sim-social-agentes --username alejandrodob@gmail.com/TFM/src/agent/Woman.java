package agent;

import agent.behavior.ListBehavior;
import sim.util.MutableInt2D;

public class Woman extends Person {
	
	public Woman() {
		super();
	}

	public Woman(MutableInt2D location, int age, boolean coupled, SocioeconomicLevel socLevel,
			Education education) {
		super(location, new ListBehavior(), age, coupled, socLevel, education);
	}



	
}
