package agent.social;

import java.util.ArrayList;

import agent.DemographicItem;
import agent.Person;
import agent.Woman;
import agent.Man;

public interface FriendsNetwork {

	public ArrayList<Woman> femaleFriends();

	public ArrayList<Man> maleFriends();
	
	public ArrayList<Person> friends();
	
	public boolean isFriend(Person friend);
	// similarmente a como ocurre con FamilyNetwork, la clase que implemente las
	// funcionalidades de esta
	// debera extender a su vez ListNetwork (o implementar tambien
	// AgentSocialNetwork)
}
