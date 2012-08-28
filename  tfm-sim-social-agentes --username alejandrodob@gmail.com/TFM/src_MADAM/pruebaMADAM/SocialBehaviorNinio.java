package pruebaMADAM;

import java.util.ArrayList;

import agent.DemographicItem;
import agent.Person;
import agent.Socializable;
import agent.behavior.BasicSocialBehavior;
import ec.util.MersenneTwisterFast;

public class SocialBehaviorNinio extends BasicSocialBehavior {
	
	//SINGLETON

	private static MersenneTwisterFast random = new MersenneTwisterFast();
	private static SocialBehaviorNinio INSTANCE = new SocialBehaviorNinio();
	
	private SocialBehaviorNinio() {}
	
	public static SocialBehaviorNinio getInstance() {
		return INSTANCE;
	}

	@Override
	protected void meetPeople(Socializable me) {
		if (((Person) me).getAge() > 7 && ((Person) me).getSteps()%25 == 0) { //la unica diferencia con adulto es que ha de ser mayor de 7 años
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
				if (personaB.acceptFriendshipProposal((Person) me)) {
					makeNewFriend(personaB,me);
				}
			}
			//segunda manera
			else {
				//mis amigos
				ArrayList<Person> amigos = ((Person) me).getFriends().friends();
				if (amigos.size() > 0) {
					//se escoge uno al azar, y se obtienen sus amigos
					Person amigo = amigos.get(random.nextInt(amigos.size()));
					ArrayList<Person> amigosDeAmigo = amigo.getFriends().friends();
					if (amigosDeAmigo.size() > 0) {
						//se escoge uno al azar, y le propongo amistad
						Person amigoDeAmigo = amigosDeAmigo.get(random.nextInt(amigosDeAmigo.size()));
						if (amigoDeAmigo.acceptFriendshipProposal((Person) me)) {
							makeNewFriend(amigoDeAmigo,me);
						}
					}
				}
			}
		}
	}

	@Override
	protected void makeNewFriend(Socializable friend, Socializable me) {
		me.addFriend((DemographicItem) friend, null);
	}

	@Override
	protected void searchForMate(Socializable me) {
		//no busca pareja, es un ninio
		//eso sí, comprobara en cada ciclo si alcanza los 16 años, momento en el cual
		//se cambiara de comportamiento a uno de adulto, en que comenzara a buscar pareja
		if (((Person) me).getAge() >= 10) {
			((DemographicItem) me).removeBehaviorModule(this);
			((DemographicItem) me).addBehaviorModule(SocialBehaviorAdulto.getInstance());
		}
	}

	@Override
	protected void marry(Socializable partner, Socializable me) {
		//no se casa, es un ninio
	}

	@Override
	public boolean acceptFriend(Socializable friend, Socializable me) {
		return true;
	}

	@Override
	public boolean acceptMarriage(Socializable candidate, Socializable me) {
		return false;
	}

}
