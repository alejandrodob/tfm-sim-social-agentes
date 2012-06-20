package pruebaMADAM;

import java.util.ArrayList;

import sim.field.network.Edge;
import sim.util.Bag;
import agent.Man;
import agent.Person;
import agent.Woman;
import agent.behavior.BasicSocialBehavior;
import agent.social.FriendsNetwork;

public class SocialBehaviorPrueba extends BasicSocialBehavior {

	@Override
	protected void meetPeople(Person person) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void makeNewFriend(Person person) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void searchForMate(Person person) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void mate(Person person) {
		// TODO Auto-generated method stub

	}
	
	public boolean cumpleRequisitos(Person posiblePareja,Person me) {
		if (me instanceof Man) {
			return (posiblePareja instanceof Woman)
				//&& (!getCasado()) //esto es temporal, los casados podran divorciarse si encuantran una pareja mejor, pero de momento para evitar bodas triples
				&& (posiblePareja.getAge() >= 16)
				&& (Math.abs(me.getAge() - posiblePareja.getAge()) <= 8)
				&& (caracteristicasComunes(posiblePareja) > nivelExigencia);
		} else {
			return (posiblePareja instanceof Man)
			//&& (!getCasado()) //esto es temporal, los casados podran divorciarse si encuantran una pareja mejor, pero de momento para evitar bodas triples
			&& (posiblePareja.getAge() >= 16)
			&& (Math.abs(me.getAge() - posiblePareja.getAge()) <= 8)
			&& (caracteristicasComunes(posiblePareja) > nivelExigencia);
		}
	}
	
	public boolean aceptaPretendiente(Person pretendiente,Person me) {
		return cumpleRequisitos(pretendiente,me);
	}
	
	public void buscaPareja(Person me) { //habr� que refinarlo probablemente
		FriendsNetwork amigos = (FriendsNetwork)me.getFriends();
		if (me instanceof Man) {
			ArrayList<Woman> candidatos = amigos.femaleFriends();
			if (candidatos.size() > 0){
				Woman candidata = candidatos.get(.random.nextInt(candidatos.size()));
				Individuo pareja = (Individuo) rel.getOtherNode(this);
				if (cumpleRequisitos(pareja)) //si me gusta como posible pareja, "pido su mano"
					if (pareja.aceptaPretendiente(this)){
						if (getCasado()) meDivorcio(mundo);
						if (pareja.getCasado()) pareja.meDivorcio(mundo);
						nosCasamos(mundo,pareja);
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
	
	public void conocerGenteNueva(Mundo mundo){
		//va a haber 2 maneras de conocer gente, conocer gente al azar de entre los que est�n a su 
		//alrededor, o conocer a conocidos de conocidos

		//primera manera
		if (mundo.random.nextBoolean(0.3)){
			Individuo personaB = null;
			int radioMedioAmistad = 40;
			int factorAleatorio = (int) (mundo.random.nextGaussian()*9); //algunos buscar�n m�s cerca, otros m�s lejos
			int radioEfectivo = radioMedioAmistad + factorAleatorio;
			if (radioEfectivo <= 0) radioEfectivo = radioMedioAmistad;
			Bag genteCerca = mundo.personasEnDerredor(this,radioEfectivo);
			int cont = 0;
			do {
				//si con este radio no le alcanza para conocer a nadie, busca un poco m�s lejos
				int aux = radioEfectivo;
				while (genteCerca.numObjs == 0){
					aux++;
					genteCerca = mundo.personasEnDerredor(this,aux);
				}

				personaB = (Individuo) genteCerca.get(mundo.random.nextInt(genteCerca.numObjs));
				cont++;
			}
			while ((this == personaB) && (cont <= genteCerca.numObjs));
			mundo.amistades.addEdge(this, personaB, null);
		}
		//segunda manera
		else {
			Bag conocidos = mundo.amistades.getEdgesIn(this);
			if (conocidos.numObjs > 0){
				Edge amistad = (Edge) conocidos.get(mundo.random.nextInt(conocidos.numObjs));
				Individuo conocidoAlAzar = (Individuo) amistad.getOtherNode(this);
				Bag conocidosDeMiConocido = mundo.amistades.getEdgesIn(conocidoAlAzar);
				if (conocidosDeMiConocido.numObjs > 0){
					Edge amistad2 = (Edge) conocidosDeMiConocido.get(mundo.random.nextInt(conocidosDeMiConocido.numObjs));
					Individuo nuevoAmigo = (Individuo) amistad2.getOtherNode(conocidoAlAzar);
					mundo.amistades.addEdge(this,nuevoAmigo,null);
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
