package model;

import agent.Person;

public interface SocialWorld { // este nombre no me gusta nada, espero encontrar
								// uno mas apropiado


	public void registerWedding(Person p1, Person p2);

	public void registerDivorce(Person p1, Person p2);

	public void addFamilyLink(Person p1, Person p2, socialNetwork.Relation rel);

	public void addFriendshipLink(Person p1, Person p2);
}
