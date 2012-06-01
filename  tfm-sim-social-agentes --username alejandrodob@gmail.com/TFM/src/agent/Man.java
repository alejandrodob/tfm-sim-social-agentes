package agent;

import agent.behavior.ListBehavior;
import sim.util.MutableInt2D;

public class Man extends Person {
	
	public Man() {
		super();
	}

	public Man(MutableInt2D location, int age, boolean coupled, SocioeconomicLevel socLevel,
			Education education) {
		super(location, new ListBehavior(), age, coupled, socLevel, education);
	}

	

}
