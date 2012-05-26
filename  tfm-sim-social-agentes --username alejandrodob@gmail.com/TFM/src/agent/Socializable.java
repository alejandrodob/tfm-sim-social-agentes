package agent;

public interface Socializable {
	
	public void addFriend(DemographicItem friend);
	public void removeFriend(DemographicItem friend);
	public void addFamilyMember(DemographicItem famMem);
	public void removeFamilyMember(DemographicItem famMem);

}
