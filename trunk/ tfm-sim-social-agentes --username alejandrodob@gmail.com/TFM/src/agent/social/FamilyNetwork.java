package agent.social;

import java.util.ArrayList;

import agent.Man;
import agent.Person;
import agent.Woman;

public interface FamilyNetwork {

	public ArrayList<Person> siblings();

	public Man father();

	public Woman mother();

	public ArrayList<Person> sons();
	// etcetera.
	// otra cosa: la idea es que la clase con funcionalidad extienda ListNetwork
	// e implemente a esta
}
