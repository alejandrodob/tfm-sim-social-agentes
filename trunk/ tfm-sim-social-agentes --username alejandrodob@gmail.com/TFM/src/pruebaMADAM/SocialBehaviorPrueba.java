package pruebaMADAM;

import java.util.ArrayList;

import ec.util.MersenneTwisterFast;

import sim.field.network.Edge;
import sim.util.Bag;
import agent.DemographicItem;
import agent.Man;
import agent.Person;
import agent.Woman;
import agent.behavior.BasicSocialBehavior;
import agent.social.FriendsNetwork;

public class SocialBehaviorPrueba extends BasicSocialBehavior {
	
	private static MersenneTwisterFast random = new MersenneTwisterFast();
	
	public SocialBehaviorPrueba() {
		super();
	}

	@Override
	protected void meetPeople(Person me) {
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
				while (genteCerca.size() == 0){
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

	@Override
	protected void makeNewFriend(Person friend, Person me) {
		me.addFriend(friend, null);
	}

	@Override
	protected void searchForMate(Person person) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void marry(Person partner, Person me) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean acceptFriend(Person friend, Person me) {
		return true;
	}

	@Override
	public boolean acceptMarriage(Person candidate, Person me) {
		// TODO Auto-generated method stub
		return cumpleRequisitos(candidate,me);
	}
	
	public boolean cumpleRequisitos(Person posiblePareja,Person me) {
		if (me instanceof HombrePrueba) {
			return (posiblePareja instanceof MujerPrueba)
				//&& (!getCasado()) //esto es temporal, los casados podran divorciarse si encuantran una pareja mejor, pero de momento para evitar bodas triples
				&& (posiblePareja.getAge() >= 16)
				&& (Math.abs(me.getAge() - posiblePareja.getAge()) <= 8)
				&& (caracteristicasComunes(posiblePareja) > ((HombrePrueba) me).getNivelExigencia());
		} else {
			return (posiblePareja instanceof HombrePrueba)
			//&& (!getCasado()) //esto es temporal, los casados podran divorciarse si encuantran una pareja mejor, pero de momento para evitar bodas triples
			&& (posiblePareja.getAge() >= 16)
			&& (Math.abs(me.getAge() - posiblePareja.getAge()) <= 8)
			&& (caracteristicasComunes(posiblePareja) > ((MujerPrueba) me).getNivelExigencia());
		}
	}
	
	public boolean aceptaPretendiente(Person pretendiente,Person me) {
		return cumpleRequisitos(pretendiente,me);
	}
	
	public void buscaPareja(Person me) { //habr� que refinarlo probablemente
		FriendsNetwork amigos = (FriendsNetwork)me.getFriends();
		if (me instanceof HombrePrueba) {
			ArrayList<Woman> candidatos = amigos.femaleFriends();
			if (candidatos.size() > 0) {
				MujerPrueba candidata = (MujerPrueba) candidatos.get(random.nextInt(candidatos.size()));
				if (cumpleRequisitos(candidata)) //si me gusta como posible pareja, "pido su mano"
					if (aceptaPretendiente(this)) {
						if (me.isCoupled()) me.divorce();
						if (candidata.isCoupled()) candidata.divorce();//de momento esto que se quede asi, pero habria q ver bien lo de interaccion con comportamientos, porque q pasa si la candidata tiene otro comportamiento distinto
						nosCasamos(mundo,candidata);
					}
			}
		} else if (me instanceof MujerPrueba) {
			ArrayList<Man> candidatos = amigos.maleFriends();
			if (candidatos.size() > 0) {
				HombrePrueba candidato = (HombrePrueba) candidatos.get(random.nextInt(candidatos.size()));
				if (cumpleRequisitos(candidato)) //si me gusta como posible pareja, "pido su mano"
					if (aceptaPretendiente(this)) {
						if (me.isCoupled()) me.divorce();
						if (candidato.isCoupled()) candidato.divorce();//de momento esto que se quede asi, pero habria q ver bien lo de interaccion con comportamientos, porque q pasa si la candidata tiene otro comportamiento distinto
						nosCasamos(mundo,candidato);
					}
			}
		}
		
	}
	
	public void nosCasamos(Mundo mundo,Individuo pareja){
		mundo.registraBoda(pareja,this);
	}
	
	public void meDivorcio(Mundo mundo){
		mundo.registraDivorcio(this);
	}
	
	public void conocerGenteNueva(Person me){
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
				while (genteCerca.size() == 0){
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

	public void relajarExigencia() {
		if (!casado){
			nivelExigencia = (int) Math.round(caracteristicas.numCaract * Math.exp(-0.02*getEdad_Real()));
		}
	}
	
	public boolean tocaTenerHijo(Mundo mundo){
		if ((sexo == Sexo.F) && (getEdad_Real() >= 16)
				&& (getEdad_Real() <= 50) && casado
				&& (numHijos < numHijosMax))
			return (mundo.random.nextBoolean(Math.exp(-0.0875*getEdad_Real())));//no muy realista, pero bueno
		return false;
		/*return ((sexo == Sexo.F) && (getEdad_Real() >= 16)
				&& (getEdad_Real() <= 50) && casado
				&& (numHijos < numHijosMax));*/
	}

	public int caracteristicasComunes(Person otro,Person me) {
		int numCar = 0;
		
		for (int i = 0; i < caracteristicas.totalCaract; i++) {
			if (otro.getCaracteristicas()[i] == getCaracteristicas()[i])
				numCar++;
		}
		return numCar;
	}
	
	public void naceBebe(Mundo mundo){
		Sexo sexo;
		if (mundo.random.nextBoolean()){
			sexo = Sexo.F;
		}
		else sexo = Sexo.M;
		Individuo bebe = new Individuo(0,sexo,new Caracteristicas(caracteristicas.totalCaract,caracteristicas.numCaract,mundo.random));
		mundo.registraNacimiento(bebe,this);
		numHijos++;
	}
	
	public boolean cambiaEdadMuerte(int edadAntes, int edadDespues){
		return (edadAntes == 0 && edadDespues == 1) || (edadAntes%5 == 4 && edadDespues%5 == 0);
	}


}
