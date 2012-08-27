package pruebaMADAM;

import java.util.ArrayList;

import agent.DemographicItem;
import agent.Person;
import agent.behavior.BasicSocialBehavior;
import agent.social.FriendsNetwork;
import ec.util.MersenneTwisterFast;

public class SocialBehaviorAdulto extends BasicSocialBehavior {
		
	//SINGLETON

	private static MersenneTwisterFast random = new MersenneTwisterFast();
	private static SocialBehaviorAdulto INSTANCE = new SocialBehaviorAdulto();
	
	private SocialBehaviorAdulto() {}
	
	public static SocialBehaviorAdulto getInstance() {
		return INSTANCE;
	}
	
	@Override
	protected void meetPeople(Person me) {
		//se conocen (activamente) a 2 personas al año
		if (me.getSteps()%25 == 0) {
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
		if (me.getAge() <= 60) {
			FriendsNetwork amigos = (FriendsNetwork)me.getFriends();
			if (me instanceof HombrePrueba) {
				ArrayList<Person> candidatos = amigos.femaleFriends();
				if (candidatos.size() > 0) {
					MujerPrueba candidata = (MujerPrueba) candidatos.get(random.nextInt(candidatos.size()));
					if (cumpleRequisitos(candidata,me)) {//si me gusta como posible pareja, "pido su mano"
						if (candidata.acceptMarriageProposal(me)) {
							if (me.isCoupled()) me.divorce();
							marry(candidata,me);
						}
					}
				}
			} else if (me instanceof MujerPrueba) {
				ArrayList<Person> candidatos = amigos.maleFriends();
				if (candidatos.size() > 0) {
					HombrePrueba candidato = (HombrePrueba) candidatos.get(random.nextInt(candidatos.size()));
					if (cumpleRequisitos(candidato,me)) {//si me gusta como posible pareja, "pido su mano"
						if (candidato.acceptMarriageProposal(me)) {
							if (me.isCoupled()) me.divorce();
							marry(candidato,me);
						}
					}
				}
			}
		}
		//relajamos la exigencia de b�squeda de pareja
		relajarExigencia(me);
	}

	@Override
	protected void marry(Person partner, Person me) {
		me.marry(partner);
	}

	@Override
	public boolean acceptFriend(Person friend, Person me) {
		return true;
	}

	@Override
	public boolean acceptMarriage(Person candidate, Person me) {
		return cumpleRequisitos(candidate,me);
	}
	
	protected boolean cumpleRequisitos(Person posiblePareja,Person me) {
		if (me instanceof HombrePrueba) {
			return (posiblePareja instanceof MujerPrueba)
				//&& (!getCasado()) //esto es temporal, los casados podran divorciarse si encuantran una pareja mejor, pero de momento para evitar bodas triples
				&& (posiblePareja.getAge() >= 16)
				&& (Math.abs(me.getAge() - posiblePareja.getAge()) <= 8)
				&& (caracteristicasComunes(posiblePareja,me) > ((HombrePrueba) me).getNivelExigencia());
		} else {
			return (posiblePareja instanceof HombrePrueba)
			//&& (!getCasado()) //esto es temporal, los casados podran divorciarse si encuantran una pareja mejor, pero de momento para evitar bodas triples
			&& (posiblePareja.getAge() >= 16)
			&& (Math.abs(me.getAge() - posiblePareja.getAge()) <= 8)
			&& (caracteristicasComunes(posiblePareja,me) > ((MujerPrueba) me).getNivelExigencia());
		}
	}
	
	protected void relajarExigencia(Person me) {
		if (!me.isCoupled()) {
			if (me instanceof MujerPrueba) {
				((MujerPrueba)me).setNivelExigencia((int) Math.round(((MujerPrueba) me).getCaracteristicas().numCaract * Math.exp(-0.02*me.getAge())));
			}
			else if (me instanceof HombrePrueba) {
				((HombrePrueba)me).setNivelExigencia((int) Math.round(((HombrePrueba) me).getCaracteristicas().numCaract * Math.exp(-0.02*me.getAge())));
			}
		}
	}
	
	protected int caracteristicasComunes(Person otro,Person me) {
		int numCar = 0;
		if (me instanceof HombrePrueba) {
			for (int i = 0; i < ((HombrePrueba)me).getCaracteristicas().totalCaract; i++) {
				if (((MujerPrueba)otro).getCaracteristicas().getCaracteristicas()[i] == ((HombrePrueba)me).getCaracteristicas().getCaracteristicas()[i])
					numCar++;
			}
		}
		else if (me instanceof MujerPrueba) {
			for (int i = 0; i < ((MujerPrueba)me).getCaracteristicas().totalCaract; i++) {
				if (((HombrePrueba)otro).getCaracteristicas().getCaracteristicas()[i] == ((MujerPrueba)me).getCaracteristicas().getCaracteristicas()[i])
					numCar++;
			}
		}
		return numCar;
	}
	
}
