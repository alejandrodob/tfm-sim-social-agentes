package pruebaMADAM;

import model.SimpleWorld;
import sim.util.Int2D;
import ec.util.MersenneTwisterFast;
import environment.MortalityExample;
import environment.NatalityExample;
import agent.Person;
import agent.DemographicItem;
import agent.behavior.BasicDemographicBehavior;
import agent.social.Kinship;

public class DemographicBehavior extends BasicDemographicBehavior {
	
	//SINGLETON

	private static MersenneTwisterFast random = new MersenneTwisterFast();
	private static DemographicBehavior INSTANCE = new DemographicBehavior();
	
	private DemographicBehavior() {}
	
	public static DemographicBehavior getInstance() {
		return INSTANCE;
	}
	
	@Override
	protected void haveChild(DemographicItem person, SimpleWorld environment) {
		// Una vez al a�o ( o dos, lo voy cambiando)
		if (((Person) person).getSteps()%100 == 0) {
			NatalityExample nat = NatalityExample.getInstance();
			if (nat.newChild(person)) {
				if (random.nextBoolean()) {
					HombrePrueba child = new HombrePrueba(new Int2D(person.getLocation().getX(),person.getLocation().getY()),0,false);
					child.addBehaviorModule(SocialBehaviorNinio.getInstance());
					child.addBehaviorModule(DemographicBehavior.getInstance());
					child.addFamilyMember(person, Kinship.MOTHER);
					((Person) person).addFamilyMember(child, Kinship.SON);
					child.addFamilyMember(((Person) person).getFamily().couple(),Kinship.FATHER);
					((Person) person).getFamily().couple().addFamilyMember(child, Kinship.SON);
					for (Person sibling : ((Person) person).getFamily().sons()) {
						if (sibling instanceof HombrePrueba) {
							child.addFamilyMember(sibling, Kinship.BROTHER);
							sibling.addFamilyMember(child, Kinship.BROTHER);
						} else {
							child.addFamilyMember(sibling, Kinship.SISTER);
							sibling.addFamilyMember(child, Kinship.BROTHER);
						}
					}
					environment.addIndividual(child,child.getLocation());
				}
				else {
					MujerPrueba child = new MujerPrueba(new Int2D(person.getLocation().getX(),person.getLocation().getY()),0,false);
					child.addBehaviorModule(SocialBehaviorNinio.getInstance());
					child.addBehaviorModule(DemographicBehavior.getInstance());
					child.addFamilyMember(person, Kinship.MOTHER);
					((Person) person).addFamilyMember(child, Kinship.DAUGHTER);
					child.addFamilyMember(((Person) person).getFamily().couple(),Kinship.FATHER);
					((Person) person).getFamily().couple().addFamilyMember(child, Kinship.DAUGHTER);
					for (Person sibling : ((Person) person).getFamily().sons()) {
						if (sibling instanceof HombrePrueba) {
							child.addFamilyMember(sibling, Kinship.BROTHER);
							sibling.addFamilyMember(child, Kinship.SISTER);
						} else {
							child.addFamilyMember(sibling, Kinship.SISTER);
							sibling.addFamilyMember(child, Kinship.SISTER);
						}
					}
					environment.addIndividual(child,child.getLocation());
				}
			}
		}
	}

	@Override
	protected void die(DemographicItem person, SimpleWorld environment) {
		MortalityExample mort = MortalityExample.getInstance();
		if (person instanceof HombrePrueba) {
			// Y si la Muerte ha de llev�rselo, que as� sea
			if (((Person) person).getAge() > ((HombrePrueba)person).getEdadMuerte()) {
				person.getStop().stop();
			
			//////////aki me kedo, me falta registrar la muerte, borrarle de las listas de sus coleguis
			//////////borrarle del field y del socialnetwork de world
			//idea: haciendo un metodo en Person que le borre de sus coleguis y familiares, que sea general pa
			//que se pueda reutilizar en otras simulaciones. luego, a parte, aquí, y aprovechando que le paso 
			//como parametro en el behave el World, llamar a los metodos de world correspondientes para que
			//desaparezca de la fas de la tierra
			}
			
			// Cada 5 a�os se actualiza su probabilidad de morir en los pr�ximos 5
			int edadDespues = ((Person) person).getAge();
			if (cambiaEdadMuerte(edadDespues-1,edadDespues))
				((HombrePrueba) person).setEdadMuerte(mort.ageOfDeath(person));		
		}	
	}
	
	protected boolean cambiaEdadMuerte(int edadAntes, int edadDespues){
		return (edadAntes == 0 && edadDespues == 1) || (edadAntes%5 == 4 && edadDespues%5 == 0);
	}

	@Override
	protected void migrate(DemographicItem person, SimpleWorld environment) {}

}
