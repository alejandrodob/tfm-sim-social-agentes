package agent.social;

import java.util.ArrayList;

import agent.Person;

public interface FriendsNetwork {

	public ArrayList<Person> femaleFriends();

	public ArrayList<Person> maleFriends();
	
	public ArrayList<Person> friends();
	
	public boolean isFriend(Person friend);
	// similarmente a como ocurre con FamilyNetwork, la clase que implemente las
	// funcionalidades de esta
	// debera extender a su vez ListNetwork (o implementar tambien
	// AgentSocialNetwork)
}
