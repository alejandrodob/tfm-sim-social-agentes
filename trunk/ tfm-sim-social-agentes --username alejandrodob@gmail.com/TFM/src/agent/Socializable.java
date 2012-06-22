package agent;

import java.util.Collection;

public interface Socializable {

	public void addFriend(DemographicItem friend, Object atribute);

	public void removeFriend(DemographicItem friend);
	
	public boolean isFriend(DemographicItem person);

	public void addFamilyMember(DemographicItem relative, Object kinship);

	public void removeFamilyMember(DemographicItem relative);
	
	public void divorce();
	
	public void marry(Person partner);
	
	public Collection<DemographicItem> peopleAround(int radius);
	
	//los metodos que vienen a continuacion dependeran del tipo de comportamiento que tenga asignada
	//la persona en cuestion, asi pues han de ser un simple enlace al correspondiente del comportamiento que tenga
	
	public boolean acceptFriendshipProposal(Person friend);
	
	public boolean acceptMarriageProposal(Person candidate);
}
