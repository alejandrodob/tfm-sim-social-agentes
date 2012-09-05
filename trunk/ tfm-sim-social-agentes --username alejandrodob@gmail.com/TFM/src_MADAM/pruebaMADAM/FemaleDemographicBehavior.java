package pruebaMADAM;

import model.SimpleWorld;
import sim.util.Int2D;
import agent.DemographicItem;
import agent.Person;
import agent.behavior.BasicDemographicBehavior;
import agent.social.Kinship;
import ec.util.MersenneTwisterFast;

public class FemaleDemographicBehavior extends BasicDemographicBehavior {
	
	//SINGLETON

	private static MersenneTwisterFast random = new MersenneTwisterFast();
	private static FemaleDemographicBehavior INSTANCE = new FemaleDemographicBehavior();
	
	private FemaleDemographicBehavior() {}
	
	public static FemaleDemographicBehavior getInstance() {
		return INSTANCE;
	}
	
	@Override
	protected void haveChild(DemographicItem person, SimpleWorld environment) {
		// Una vez al a�o ( o dos, lo voy cambiando)
		if (((MadamPerson) person).steps()%100 == 0 && ((Person) person).isCoupled()
				&& ((Woman) person).numHijos() < ((Woman) person).getNumHijosMax()) {
			FertilityExample nat = (FertilityExample) environment.fertility;
			if (nat.newChild(person)) {
				Int2D motherLocation = new Int2D(person.getLocation().x,person.getLocation().y);
				int totCarac = ((MadamWorld)environment).getTotalCaract();
				int numCarac = ((MadamWorld)environment).getNumCaract();
				if (random.nextDouble() > nat.femaleBirthProbability()) {
					Man child = new Man(motherLocation,0,false,totCarac,numCarac);
					
					child.addFamilyMember(person, Kinship.MOTHER);
					((Person) person).addFamilyMember(child, Kinship.SON);
					child.addFamilyMember(((Person) person).getFamily().couple(),Kinship.FATHER);
					((Person) person).getFamily().couple().addFamilyMember(child, Kinship.SON);
					for (Person sibling : ((Person) person).getFamily().sons()) {
						if (sibling instanceof Man) {
							child.addFamilyMember(sibling, Kinship.BROTHER);
							sibling.addFamilyMember(child, Kinship.BROTHER);
						} else {
							child.addFamilyMember(sibling, Kinship.SISTER);
							sibling.addFamilyMember(child, Kinship.BROTHER);
						}
					}
					environment.registerBirth(child, person);
				}
				else {
					Woman child = new Woman(motherLocation,0,false,totCarac,numCarac);

					child.addFamilyMember(person, Kinship.MOTHER);
					((Person) person).addFamilyMember(child, Kinship.DAUGHTER);
					child.addFamilyMember(((Person) person).getFamily().couple(),Kinship.FATHER);
					((Person) person).getFamily().couple().addFamilyMember(child, Kinship.DAUGHTER);
					for (Person sibling : ((Person) person).getFamily().sons()) {
						if (sibling instanceof Man) {
							child.addFamilyMember(sibling, Kinship.BROTHER);
							sibling.addFamilyMember(child, Kinship.SISTER);
						} else {
							child.addFamilyMember(sibling, Kinship.SISTER);
							sibling.addFamilyMember(child, Kinship.SISTER);
						}
					}
					environment.registerBirth(child, person);
				}
			}
		}
	}

	@Override
	protected void die(DemographicItem person, SimpleWorld environment) {
		MortalityExample mort = MortalityExample.getInstance();

		// Y si la Muerte ha de llev�rselo, que as� sea
		if (((Person) person).getAge() >= ((Man)person).getEdadMuerte()) {
			((MadamPerson) person).die();
			environment.registerDeath(person);

		}
		
		int edadDespues = ((Person) person).getAge();
		if (cambiaEdadMuerte(edadDespues-1,edadDespues))
			((Man) person).setEdadMuerte(mort.ageOfDeath(person));
	}
	
	private boolean cambiaEdadMuerte(int edadAntes, int edadDespues){
		return (edadAntes == 0 && edadDespues == 1) || (edadAntes%5 == 4 && edadDespues%5 == 0);
	}

	@Override
	public void age(DemographicItem person) {
		((MadamPerson) person).incSteps();
	}
	
	@Override
	protected void migrate(DemographicItem person, SimpleWorld environment) {}

}
