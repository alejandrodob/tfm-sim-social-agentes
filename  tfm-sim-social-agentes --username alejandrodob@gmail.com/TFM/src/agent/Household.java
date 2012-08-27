package agent;

import java.util.ArrayList;

public abstract class Household extends DemographicItem {
	//this class is intended to be used when a household is not the minimmum unit int the simulation.
	//otherwise, one should use DemographicItem and make a subclass of it
		
	protected int size;

	public abstract void addMember(Person member);
	
	public abstract void removeMember(Person member);
	
	public abstract Household fission();
	
	public abstract void disolve();
	
}
