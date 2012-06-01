package agent.social;

import sim.util.Bag;

public interface FriendsNetwork {
	
	public Bag femaleFriends();
	public Bag maleFriends();
	//similarmente a como ocurre con FamilyNetwork, la clase que implemente las funcionalidades de esta
	//debera extender a su vez ListNetwork (o implementar tambien AgentSocialNetwork)
}
