package agent.social;

import java.util.ArrayList;

import agent.Man;
import agent.Person;
import agent.Woman;

public class FriendsListNetwork extends ListNetwork implements FriendsNetwork {

	@Override
	public ArrayList<Person> femaleFriends() {
		ArrayList<Person> femFriends = new ArrayList<Person>();
		for (ListElement f : network) {
			if (((Person) f.agent).female())
				femFriends.add((Person) f.agent);
		}
		return femFriends;
	}

	@Override
	public ArrayList<Person> maleFriends() {
		ArrayList<Person> masFriends = new ArrayList<Person>();
		for (ListElement f : network) {
			if (((Person) f.agent).male())
				masFriends.add((Person) f.agent);
		}
		return masFriends;
	}

	@Override
	public ArrayList<Person> friends() {
		ArrayList<Person> friends = new ArrayList<Person>();
		for (ListElement f : network) {
			friends.add((Person) f.agent);
		}
		return friends;
	}

	@Override
	public boolean isFriend(Person person) {
		return containsMember(person);
	}

}
