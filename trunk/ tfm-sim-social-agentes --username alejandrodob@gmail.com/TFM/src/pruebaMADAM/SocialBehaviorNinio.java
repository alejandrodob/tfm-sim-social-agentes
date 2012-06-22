package pruebaMADAM;

import java.util.ArrayList;

import ec.util.MersenneTwisterFast;

import agent.DemographicItem;
import agent.Person;
import agent.behavior.BasicSocialBehavior;

public class SocialBehaviorNinio extends BasicSocialBehavior {
	
	private static MersenneTwisterFast random = new MersenneTwisterFast();
	
	public SocialBehaviorNinio() {
		super();
	}

	@Override
	protected void meetPeople(Person me) {
		if (me.getAge() > 7 && me.getSteps()%25 == 0) { //la unica diferencia con adulto es que ha de ser mayor de 7 años
			//va a haber 2 maneras de conocer gente, conocer gente al azar de entre los que est�n a su 
			//alrededor, o conocer a conocidos de conocidos

			//primera manera
			if (random.nextBoolean(0.3)) {
				Person personaB = null;
				int radioMedioAmistad = 40;
				int factorAleatorio = (int) (random.nextGaussian()*9); //algunos buscar�n m�s cerca, otros m�s lejos
				int radioEfectivo = radioMedioAmistad + factorAleatorio;
				if (radioEfectivo <= 0) radioEfectivo = radioMedioAmistad;
				ArrayList<DemographicItem> genteCerca = (ArrayList<DemographicItem>) me.peopleAround(radioEfectivo);
				int cont = 0;
				do {
					//si con este radio no le alcanza para conocer a nadie, busca un poco m�s lejos
					int aux = radioEfectivo;
					while (genteCerca.size() == 0) {
						aux++;
						genteCerca = (ArrayList<DemographicItem>) me.peopleAround(aux);
					}

					personaB = (Person) genteCerca.get(random.nextInt(genteCerca.size()));
					cont++;
				}
				while (me.isFriend(personaB) && (cont <= genteCerca.size()));
				if (personaB.acceptFriendshipProposal(me)) {
					makeNewFriend(personaB,me);
				}
			}
			//segunda manera
			else {
				//mis amigos
				ArrayList<Person> amigos = me.getFriends().friends();
				if (amigos.size() > 0) {
					//se escoge uno al azar, y se obtienen sus amigos
					Person amigo = amigos.get(random.nextInt(amigos.size()));
					ArrayList<Person> amigosDeAmigo = amigo.getFriends().friends();
					if (amigosDeAmigo.size() > 0) {
						//se escoge uno al azar, y le propongo amistad
						Person amigoDeAmigo = amigosDeAmigo.get(random.nextInt(amigosDeAmigo.size()));
						if (amigoDeAmigo.acceptFriendshipProposal(me)) {
							makeNewFriend(amigoDeAmigo,me);
						}
					}
				}
			}
		}
	}

	@Override
	protected void makeNewFriend(Person friend, Person me) {
		me.addFriend(friend, null);
	}

	@Override
	protected void searchForMate(Person me) {
		//no busca pareja, es un ninio
		//eso sí, comprobara en cada ciclo si alcanza los 16 años, momento en el cual
		//se cambiara de comportamiento a uno de adulto, en que comenzara a buscar pareja
		if (me.getAge() >= 10) {
			me.removeBehaviorModule(this);
			me.addBehaviorModule(new SocialBehaviorAdulto());
		}
	}

	@Override
	protected void marry(Person partner, Person me) {
		//no se casa, es un ninio
	}

	@Override
	public boolean acceptFriend(Person friend, Person me) {
		return true;
	}

	@Override
	public boolean acceptMarriage(Person candidate, Person me) {
		return false;
	}

}
