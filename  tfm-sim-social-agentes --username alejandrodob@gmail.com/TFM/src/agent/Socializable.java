package agent;

public interface Socializable {
	
	public void addFriend(DemographicItem friend);
	public void removeFriend(DemographicItem friend);
	public void addFamilyMember(DemographicItem relative);
	public void removeFamilyMember(DemographicItem relative);

}
