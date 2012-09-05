package pruebaMADAM;

import java.util.ArrayList;

import agent.DemographicItem;
import agent.behavior.Behavior;
import agent.behavior.PriorityBehavior;
import sim.field.grid.SparseGrid2D;
import sim.field.network.Network;
import sim.util.Bag;
import sim.util.Int2D;
import socialNetwork.SocialNetwork;
import model.World;
import model.field.Field2D;

public class MadamWorld extends World {
	
	public int numHabitantes = 4000;
	private int totalCaract = 100;
	private int numCaract = 50;
	public int getTotalCaract() {
		return totalCaract;
	}

	public void setTotalCaract(int totalCaract) {
		this.totalCaract = totalCaract;
	}

	public int getNumCaract() {
		return numCaract;
	}

	public void setNumCaract(int numCaract) {
		this.numCaract = numCaract;
	}

	private int width = 1000;
	private int height = 1000;
	
//	public Estadisticas estad = new Estadisticas();
//	public SparseGrid2D estadGrid = new SparseGrid2D(1,1);
//	public Network estadNet = new Network(false);


	public MadamWorld(long seed) {
		super(seed);
		field = new Field2D(width,height);
		fertility = new FertilityExample();
		mortality = MortalityExample.getInstance();
	}

	@Override
	public void start() {
		super.start();
		
		field.clear();
		population = new SocialNetwork();
		
		//add the initial population
		for (int i = 0; i < numHabitantes; i++){
			MadamPerson ind = createRandomPerson();
			addIndividual(ind, ind.getLocation());
		}
		//estad.setPoblacion(amistades.getAllNodes().size());
		
		//creamos unas primeras amistades aleatorias para que se puedan relacionar un poquico
		Bag gente = population.getAllNodes();
		for (int i=0; i<gente.size(); i++){
			
			MadamPerson persona = (MadamPerson) gente.get(i);
			
			//le buscamos un amigo que esté más o menos cerca de él
			MadamPerson personaB = null;
			int radioMedioAmistad = 40;
			int factorAleatorio = (int) (random.nextGaussian()*9); //algunos buscarán más cerca, otros más lejos
			int radioEfectivo = radioMedioAmistad + factorAleatorio;
			if (radioEfectivo <= 0) radioEfectivo = radioMedioAmistad;
			ArrayList<DemographicItem> genteCerca = persona.peopleAround(radioEfectivo);
			int cont = 0;
			do {
				//si con este radio no le alcanza para conocer a nadie, busca un poco más lejos
				int aux = radioEfectivo;
				while (genteCerca.size() == 0){
					aux++;
					genteCerca = persona.peopleAround(aux);
				}
				
				personaB = (MadamPerson) genteCerca.get(random.nextInt(genteCerca.size()));
				cont++;
			}
			while ((persona == personaB) && (cont <= genteCerca.size()));
			addFriendshipLink(persona, personaB);
		}
	}
	
	private MadamPerson createRandomPerson() {
		Int2D location = new Int2D(random.nextInt(width),random.nextInt(height));
		int age = random.nextInt(80);
		if (random.nextDouble() < fertility.femaleBirthProbability()) {
			return new Woman(location,age,false,totalCaract,numCaract);
		}
		else {
			return new Man(location,age,false,totalCaract,numCaract);
		}
	}

	public static void main(String[] args){
		doLoop(MadamWorld.class,args);
		System.exit(0);
	}
}
