package pruebaMADAM;

import ec.util.MersenneTwisterFast;
import model.SimpleWorld;
import agent.DemographicItem;
import agent.Person;
import agent.behavior.BasicDemographicBehavior;

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
		//this behavior is intended for those who cannot have children: men and children themselves
		//but when a child girl reaches 16, she becomes a fertile woman
		if (person.getAge() == 16 && person instanceof Woman){
			person.removeBehaviorModule(this);
			person.addBehaviorModule(FemaleDemographicBehavior.getInstance(), 1);
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
