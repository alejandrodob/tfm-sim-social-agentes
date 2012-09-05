package agent.social;

import java.util.ArrayList;
import java.util.Iterator;

import agent.Person;

public class FamilyListNetwork extends ListNetwork implements FamilyNetwork {

	@Override
	public ArrayList<Person> siblings() {
		ArrayList<Person> siblings = new ArrayList<Person>();
		for (ListElement sibling : network) {
			if (sibling.atribute.equals(Kinship.BROTHER)
					|| sibling.atribute.equals(Kinship.SISTER))
				siblings.add((Person) sibling.agent);
		}
		return siblings;
	}

	@Override
	public Person father() {
		Iterator<ListElement> it = network.iterator();
		boolean found = false;
		Person father = null;
		while (it.hasNext() && !found) {
			ListElement next = it.next();
			if (next.atribute.equals(Kinship.FATHER)) {
				found = true;
				father = (Person) next.agent;
			}
		}
		return father;
	}

	@Override
	public Person mother() {
		Iterator<ListElement> it = network.iterator();
		boolean found = false;
		Person mother = null;
		while (it.hasNext() && !found) {
			ListElement next = it.next();
			if (next.atribute.equals(Kinship.MOTHER)) {
				found = true;
				mother = (Person) next.agent;
			}
		}
		return mother;
	}
	
	@Override
	public Person couple() {
		Iterator<ListElement> it = network.iterator();
		boolean found = false;
		Person couple = null;
		while (it.hasNext() && !found) {
			ListElement next = it.next();
			if (next.atribute.equals(Kinship.WIFE) || next.atribute.equals(Kinship.HUSBAND)) {
				found = true;
				couple = (Person) next.agent;
			}
		}
		return couple;
	}

	@Override
	public ArrayList<Person> sons() {
		ArrayList<Person> sons = new ArrayList<Person>();
		for (ListElement son : network) {
			if (son.atribute.equals(Kinship.SON))
				sons.add((Person) son.agent);
		}
		return sons;
	}

	@Override
	public boolean isFamilyMember(Person person) {
		return containsMember(person);
	}

	@Override
	public ArrayList<Person> members() {
		ArrayList<Person> sons = new ArrayList<Person>();
		for (ListElement son : network)	sons.add((Person) son.agent);
		return sons;
	}

}
