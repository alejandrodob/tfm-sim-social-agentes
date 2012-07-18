package model;

import agent.Person;
import agent.DemographicItem;
import environment.*;
import sim.engine.SimState;
import sim.field.grid.SparseGrid2D;
import sim.field.network.Network;
import sim.util.Int2D;
import socialNetwork.SocialNetwork;

public class World extends SimpleWorld implements SocialWorld {

	
	public SocialNetwork population = new SocialNetwork();
	

	public World(long seed) {
		super(seed);
	}

	@Override
	public void addIndividual(DemographicItem person, Int2D location) {
		population.addPerson((Person) person);
		field.setObjectLocation(person, location);
		person.setField(field);
		person.setStop(schedule.scheduleRepeating(person));
	}

	@Override
	public void removeIndividual(DemographicItem person) {
		if (field.remove(person) != null)  //no need of invoking population.removePerson(person) if no such person exists
			population.removePerson((Person) person);
	}

	@Override
	public void registerDeath(DemographicItem person) {
		removeIndividual(person);
		//registrar en las estadisticas esta muerte
	}

	@Override
	public void registerBirth(DemographicItem newborn, DemographicItem mother) {
		addIndividual(newborn, newborn.getLocation());
		population.addRelation((Person) mother, (Person) newborn,
				SocialNetwork.relation.MOTHER_SON);
	}
	
	@Override
	public void registerMigration(DemographicItem person, Int2D from, Int2D to) {
		// hasta que no lo use en algo concreto, integre el GIS y defina un poco
		// mejor lo de las
		// ubicaciones, esto no lo toco

	}

	@Override
	public void registerWedding(Person p1, Person p2) {
		population.addRelation(p1, p2, SocialNetwork.relation.COUPLE);
	}

	@Override
	public void registerDivorce(Person p1, Person p2) {
		population.removeRelation(p1, p2, SocialNetwork.relation.COUPLE);
	}

	@Override
	public void addFamilyLink(Person p1, Person p2, SocialNetwork.relation rel) {
		population.addRelation(p1, p2, rel);
	}

	@Override
	public void addFriendshipLink(Person p1, Person p2) {
		population.addRelation(p1, p2, SocialNetwork.relation.FRIENDS);
	}

}
