package model;

import sim.util.Int2D;
import socialNetwork.SocialNetwork;
import agent.Person;

public interface SocialWorld { // este nombre no me gusta nada, espero encontrar
								// uno mas apropiado

	public void addIndividual(Person person, Int2D location);// este metodo y
																// los 3 de
																// abajo
																// deberian ir
																// en otra
																// interfaz

	public void removeIndividual(Person person);

	public void registerDeath(Person person);

	public void registerBirth(Person newborn, Person mother);

	public void registerWedding(Person p1, Person p2);

	public void registerDivorce(Person p1, Person p2);

	public void registerMigration(Person person, Int2D from, Int2D to);

	public void addFamilyLink(Person p1, Person p2, SocialNetwork.relation rel);

	public void addFriendshipLink(Person p1, Person p2);
}