package agent.social;

import java.util.ArrayList;

import agent.Man;
import agent.Person;
import agent.Woman;

public interface FamilyNetwork {
	
	public ArrayList<Person> Siblings();
	public Man Father();
	public Woman Mother();
	public ArrayList<Person> sons();
	//etcetera. problema que se me ocurre a priori, a la hora de añadir un miembro, hay q override el metodo 
	//de AgentSocialNetwork, el problema es como meter el parentesco con la persona, ya se me ocurrira algo
	//otra cosa: la idea es que la clase con funcionalidad extienda ListNetwork e implemente a esta
}
