package pruebaMADAM;

import java.util.ArrayList;

import model.SimpleWorld;
import agent.DemographicItem;
import agent.Person;
import agent.Socializable;
import agent.behavior.BasicSocialBehavior;
import agent.social.FriendsNetwork;
import ec.util.MersenneTwisterFast;

public class MarriedSocialBehavior extends BasicSocialBehavior{
	
	//SINGLETON

	private static MersenneTwisterFast random = new MersenneTwisterFast();
	private static MarriedSocialBehavior INSTANCE = new MarriedSocialBehavior();
	
	private MarriedSocialBehavior() {}
	
	public static MarriedSocialBehavior getInstance() {
		return INSTANCE;
	}

	@Override
	protected void meetPeople(Socializable me,SimpleWorld environment) {
		//se conocen (activamente) a 2 personas al año
		if (((MadamPerson) me).steps()%25 == 0) {
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
		if (((Person) me).getAge() <= 60) {
			FriendsNetwork amigos = (FriendsNetwork)((Person) me).getFriends();
			if (me instanceof Man) {
				ArrayList<Person> candidatos = amigos.femaleFriends();
				if (candidatos.size() > 0) {
					Woman candidata = (Woman) candidatos.get(random.nextInt(candidatos.size()));
					if (cumpleRequisitos(candidata,(Person) me)) {//si me gusta como posible pareja, "pido su mano"
						if (candidata.acceptMarriageProposal((Person) me)) {
							marry(candidata,me,environment);
						}
					}
				}
			} else if (me instanceof Woman) {
				ArrayList<Person> candidatos = amigos.maleFriends();
				if (candidatos.size() > 0) {
					Man candidato = (Man) candidatos.get(random.nextInt(candidatos.size()));
					if (cumpleRequisitos(candidato,(Person) me)) {//si me gusta como posible pareja, "pido su mano"
						if (candidato.acceptMarriageProposal((Person) me)) {
							marry(candidato,me,environment);
						}
					}
				}
			}
		}
	}

	@Override
	protected void marry(Socializable partner, Socializable me,SimpleWorld environment) {
		((MadamPerson) me).askForDivorce();
		me.divorce();
		me.marry((Person) partner);
		if (environment != null) ((MadamWorld) environment).registerWedding((MadamPerson)me,(Person) partner);
	}

	@Override
	public boolean acceptFriend(Socializable friend, Socializable me) {
		//response to a friendship proposal. always accept, so add new friend
		makeNewFriend(friend, me, null);
		return true;
	}

	public boolean acceptMarriage(Socializable candidate, Socializable me) {
		boolean accept = cumpleRequisitos((Person) candidate,(Person) me);
		if (accept) marry(candidate,me,null);
		return accept;
	}
	
	protected boolean cumpleRequisitos(Person posiblePareja,Person me) {
		if (me instanceof Man) {
			return (posiblePareja instanceof Woman)
				&& (posiblePareja.getAge() >= 16)
				&& (Math.abs(me.getAge() - posiblePareja.getAge()) <= 8)
				&& (caracteristicasComunes(posiblePareja,me) > ((Man) me).getNivelExigencia());
		} else {
			return (posiblePareja instanceof Man)
			&& (posiblePareja.getAge() >= 16)
			&& (Math.abs(me.getAge() - posiblePareja.getAge()) <= 8)
			&& (caracteristicasComunes(posiblePareja,me) > ((Woman) me).getNivelExigencia());
		}
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
