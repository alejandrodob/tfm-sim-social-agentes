package agent.social;

import java.util.ArrayList;

import agent.Person;

public interface FamilyNetwork {

	public ArrayList<Person> siblings();

	public Person father();

	public Person mother();
	
	public Person couple();

	public ArrayList<Person> sons();
	
	public boolean isFamilyMember(Person person);
	
	
	// etcetera.
	// otra cosa: la idea es que la clase con funcionalidad extienda ListNetwork
	// e implemente a esta
}
