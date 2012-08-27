package agent;

import java.util.List;

public interface Household {
	
	public void addMember(DemographicItem member);
	
	public void removeMember(DemographicItem member);
	
	public Household fission();
	
	public void disolve();
	
	public List<DemographicItem> members();
	
	public DemographicItem father();
	
	public DemographicItem mother();
	
	public List<DemographicItem> sons();
	
	public int size();
	
}
