package agent;

public interface Socializable {

	public void addFriend(DemographicItem friend, Object atribute);

	public void removeFriend(DemographicItem friend);

	public void addFamilyMember(DemographicItem relative, Object kinship);

	public void removeFamilyMember(DemographicItem relative);

}
