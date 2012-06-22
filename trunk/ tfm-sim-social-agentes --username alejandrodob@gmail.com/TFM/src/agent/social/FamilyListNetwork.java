package agent.social;

import java.util.ArrayList;
import java.util.Iterator;

import agent.Man;
import agent.Person;
import agent.Woman;
import agent.social.ListNetwork.ListElement;

public class FamilyListNetwork extends ListNetwork implements FamilyNetwork {

	public enum kinship {
		FATHER, MOTHER, SON, DAUGHTER, BROTHER, SISTER, HUSBAND, WIFE
		//OBS. quiza este enum deberia ir mas arriba, en algun lugar mas abstracto
	}

	@Override
	public ArrayList<Person> siblings() {
		ArrayList<Person> siblings = new ArrayList<Person>();
		for (ListElement sibling : network) {
			if (sibling.atribute.equals(kinship.BROTHER)
					|| sibling.atribute.equals(kinship.SISTER))
				siblings.add((Person) sibling.agent);
		}
		return siblings;
	}

	@Override
	public Man father() {
		Iterator<ListElement> it = network.iterator();
		boolean found = false;
		Man father = null;
		while (it.hasNext() && !found) {
			ListElement next = it.next();
			if (next.atribute.equals(kinship.FATHER)) {
				found = true;
				father = (Man) next.agent;
			}
		}
		return father;
	}

	@Override
	public Woman mother() {
		Iterator<ListElement> it = network.iterator();
		boolean found = false;
		Woman mother = null;
		while (it.hasNext() && !found) {
			ListElement next = it.next();
			if (next.atribute.equals(kinship.MOTHER)) {
				found = true;
				mother = (Woman) next.agent;
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
			if (next.atribute.equals(kinship.WIFE) || next.atribute.equals(kinship.HUSBAND)) {
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
			if (son.atribute.equals(kinship.SON))
				sons.add((Person) son.agent);
		}
		return sons;
	}

	@Override
	public boolean isFamilyMember(Person person) {
		return containsMember(person);
	}

}
