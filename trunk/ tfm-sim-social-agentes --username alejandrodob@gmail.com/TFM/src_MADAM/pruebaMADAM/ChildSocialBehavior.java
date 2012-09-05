package pruebaMADAM;

import java.util.ArrayList;

import model.SimpleWorld;

import agent.DemographicItem;
import agent.Person;
import agent.Socializable;
import agent.behavior.BasicSocialBehavior;
import ec.util.MersenneTwisterFast;

public class ChildSocialBehavior extends BasicSocialBehavior {
	
	//SINGLETON

	private static MersenneTwisterFast random = new MersenneTwisterFast();
	private static ChildSocialBehavior INSTANCE = new ChildSocialBehavior();
	
	private ChildSocialBehavior() {}
	
	public static ChildSocialBehavior getInstance() {
		return INSTANCE;
	}

	@Override
	protected void meetPeople(Socializable me,SimpleWorld environment) {
		if (((MadamPerson) me).getAge() > 7 && ((MadamPerson) me).steps()%25 == 0) { //la unica diferencia con adulto es que ha de ser mayor de 7 años
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
					makeNewFriend(personaB,me,environment);
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
							makeNewFriend(amigoDeAmigo,me,environment);
						}
					}
				}
			}
		}
	}

	@Override
	protected void makeNewFriend(Socializable friend, Socializable me, SimpleWorld environment) {
		me.addFriend((DemographicItem) friend, null);
		if (environment != null)((MadamWorld) environment).addFriendshipLink((MadamPerson)me,(MadamPerson)friend);
	}

	@Override
	protected void searchForMate(Socializable me,SimpleWorld environment) {
		//no busca pareja, es un ninio
		//eso sí, comprobara en cada ciclo si alcanza los 16 años, momento en el cual
		//se cambiara de comportamiento a uno de adulto, en que comenzara a buscar pareja
		if (((Person) me).getAge() >= 10) {
			((DemographicItem) me).removeBehaviorModule(this);
			((DemographicItem) me).addBehaviorModule(SingleSocialBehavior.getInstance());
		}
	}

	@Override
	protected void marry(Socializable partner, Socializable me, SimpleWorld environment) {
		//no se casa, es un ninio
	}

	@Override
	public boolean acceptFriend(Socializable friend, Socializable me) {
		//response to a friendship proposal. always accept, so add new friend
		makeNewFriend(friend, me, null);
		return true;
	}

	@Override
	public boolean acceptMarriage(Socializable candidate, Socializable me) {
		return false;
	}
	
	protected int caracteristicasComunes(Person otro,Person me) {
		int numCar = 0;
			for (int i = 0; i < ((Man)me).getCaracteristicas().totalCaract; i++) {
				if (((MadamPerson) otro).getCaracteristicas().getCaracteristicas()[i] == ((MadamPerson) me).getCaracteristicas().getCaracteristicas()[i])
					numCar++;
			}
		return numCar;
	}

}
