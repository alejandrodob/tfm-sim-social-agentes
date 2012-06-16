package model;

import agent.Person;
import environment.*;
import sim.engine.SimState;
import sim.field.grid.SparseGrid2D;
import sim.field.network.Network;
import sim.util.Int2D;
import socialNetwork.SocialNetwork;

public class World extends SimState implements SocialWorld{
	
	public SocialNetwork population = new SocialNetwork();;
	public final int height = 40; //estos valores, ver si se pueden definir mediante un constructor mejor
	public final int width = 40;  //ojo que estaba el problema del constructor sin argumentos que usa el metodo doLoop de SimState, asi que igual hay que hacer el bucle a mano
	public SparseGrid2D field = new SparseGrid2D(height, width);
	
	public Natality natality;
	public Mortality mortality;
	public Mobility mobility;
	public Nuptiality nuptiality;
	
	
	public World(long seed) {
		super(seed);
	}
	
	@Override
	public void addIndividual(Person person, Int2D location) {
		population.addPerson(person);
		field.setObjectLocation(person, location);
		schedule.scheduleRepeating(person);
	}

	@Override
	public void removeIndividual(Person person) {
		population.removePerson(person);
		field.remove(person);
	}

	@Override
	public void registerDeath(Person person) {
		removeIndividual(person);
		population.removePerson(person); //this method removes automatically all the relations (edges in the network) 
										 //between this person and his friends/family
	}

	@Override
	public void registerBirth(Person newborn, Person mother) {
		addIndividual(newborn,newborn.getLocation());
		population.addRelation(mother,newborn,SocialNetwork.relation.MOTHER_SON);
	}

	@Override
	public void registerWedding(Person p1,
			Person p2) {
		population.addRelation(p1,p2,SocialNetwork.relation.COUPLE);
	}

	@Override
	public void registerDivorce(Person p1,
			Person p2) {
		population.removeRelation(p1,p2,SocialNetwork.relation.COUPLE);
	}

	@Override
	public void registerMigration(Person person, Int2D from,
			Int2D to) {
		// hasta que no lo use en algo concreto, integre el GIS y defina un poco mejor lo de las
		//ubicaciones, esto no lo toco
		
	}
	
	@Override
	public void addFamilyLink(Person p1,
			Person p2, SocialNetwork.relation rel) {
		population.addRelation(p1, p2, rel);
	}
	
	@Override
	public void addFriendshipLink(Person p1,
			Person p2) {
		population.addRelation(p1, p2, SocialNetwork.relation.FRIENDS);
	}

}
