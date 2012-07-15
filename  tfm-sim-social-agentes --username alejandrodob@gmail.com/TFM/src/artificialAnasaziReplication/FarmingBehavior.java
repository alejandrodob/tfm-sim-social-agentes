package artificialAnasaziReplication;

import pruebaMADAM.DemographicBehavior;
import ec.util.MersenneTwisterFast;
import model.SimpleWorld;
import agent.DemographicItem;
import agent.behavior.BehaviorModule;

public class FarmingBehavior implements BehaviorModule{
	
	//SINGLETON

	private static MersenneTwisterFast random = new MersenneTwisterFast();
	private static FarmingBehavior INSTANCE = new FarmingBehavior();
	
	private FarmingBehavior() {}
	
	public static FarmingBehavior getInstance() {
		return INSTANCE;
	}

	
	private void harvestConsumption(Household household, LongHouseValley valley) {
		
		//calculate the harvest of the household. Update the stocks of corn available in storage
		double baseYield = ((ValleyFloor) valley.getField()).getFloor()[household.getFarmlocation().x][household.getFarmlocation().y].getBaseYield();
		double lastHarvest = baseYield * (1 + random.nextGaussian() * valley.harvestVariance);
		household.setLastHarvest(lastHarvest);
		double[] agedCornStocks = household.getAgedCornStocks();
		agedCornStocks[2] = agedCornStocks[1];
		agedCornStocks[1] = agedCornStocks[0];
		agedCornStocks[0] = lastHarvest;
		household.setAgedCornStocks(agedCornStocks);
		household.setNutritionNeedRemaining(household.getNutritionNeed());
		                
		//calculate how much nutrients the household can derive from harvest and stored corn
		int ys = Household.yearsOfStock;
		while (ys > -1) {
			if (agedCornStocks[ys] >= household.getNutritionNeedRemaining()) {
				agedCornStocks[ys] -= household.getNutritionNeedRemaining();
				household.setNutritionNeedRemaining(0);
			} else {
				household.setNutritionNeedRemaining(household.getNutritionNeedRemaining() - agedCornStocks[ys]);
				agedCornStocks[ys] = 0;
			}
			ys--;
		}
	}
	@Override
	public void behave(DemographicItem individual, SimpleWorld environment) {
		harvestConsumption((Household) individual, (LongHouseValley) environment);
	}

}
