package artificialAnasaziReplication;

import model.SimpleWorld;
import agent.DemographicItem;
import agent.behavior.BehaviorModule;
import ec.util.MersenneTwisterFast;

public class DemographicBehaviorHousehold implements BehaviorModule{
	
	//SINGLETON

	private static MersenneTwisterFast random = new MersenneTwisterFast();
	private static DemographicBehaviorHousehold INSTANCE = new DemographicBehaviorHousehold();
	
	private DemographicBehaviorHousehold() {}
	
	public static DemographicBehaviorHousehold getInstance() {
		return INSTANCE;
	}
	

	private void death(Household household, LongHouseValley valley) {
		//agents who have not sufficient food derived or are older than deathAge are removed from the system
		    if ((household.getNutritionNeedRemaining() > 0) || household.getAge() > valley.getDeathAge()) {
		    	household.die(valley);
		    }
	}
	
	private void fissioning(Household household, LongHouseValley valley) {
		
		int ys = household.yearsOfStock;
		double[] agedCornStocks = household.getAgedCornStocks();
		//update cornStocks of parent
		while (ys > -1) {
			agedCornStocks[ys] = (1 - LongHouseValley.maizeGiveToChild) * agedCornStocks[ys];
			ys--;
		}
		valley.registerBirth(null,household);
	}
	
	private void age(Household household) {
		household.setAge(household.getAge()+1);
	}

	@Override
	public void behave(DemographicItem individual, SimpleWorld environment) {
		
		//see if household fissions
		if ( ((Household) individual).getAge() > ((Household) individual).getFertilityAge() && 
				((Household) individual).getAge() <= ((LongHouseValley) environment).getFertilityEndsAge() &&
				(random.nextFloat() < ((LongHouseValley) environment).getFertility())) {
			if (((LongHouseValley) environment).determinePotentialFarms().size() > 0) {
				fissioning((Household) individual, (LongHouseValley) environment);
			}
		}
		death((Household) individual, (LongHouseValley) environment);
		age((Household) individual);
	}

}
