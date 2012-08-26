package agent;

import java.util.ArrayList;

public abstract class Household extends DemographicItem {
		
	protected int size;

	public abstract void addMember(Person member);
	
	public abstract void removeMember(Person member);
	
	public abstract Household fission();
	
}
