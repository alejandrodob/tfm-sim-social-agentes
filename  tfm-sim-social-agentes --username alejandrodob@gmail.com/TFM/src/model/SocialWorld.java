package model;

import sim.util.Int2D;
import socialNetwork.SocialNetwork;
import agent.Person;

public interface SocialWorld { // este nombre no me gusta nada, espero encontrar
								// uno mas apropiado


	public void registerWedding(Person p1, Person p2);

	public void registerDivorce(Person p1, Person p2);

	public void addFamilyLink(Person p1, Person p2, SocialNetwork.relation rel);

	public void addFriendshipLink(Person p1, Person p2);
}
