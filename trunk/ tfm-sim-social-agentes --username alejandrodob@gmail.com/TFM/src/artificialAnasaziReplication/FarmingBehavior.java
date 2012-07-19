package artificialAnasaziReplication;

import java.util.Vector;

import pruebaMADAM.DemographicBehavior;
import sim.util.Int2D;
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
	
	private void moveHousehold(Household household, LongHouseValley valley, Int2D dest) {
		valley.registerMigration(household, null, dest);
	}
	
	private boolean findFarmAndSettlement(Household household, LongHouseValley valley) {
		
		//find a new spot for the settlement (might remain the same location as before)
		boolean settlementFound = false;
		int xh = 0;
		int yh = 0;
		
		//if there are no potential farm spots available the agent is removed from the system
		Vector<Int2D> potFarm = valley.determinePotentialFarms();
		if (valley.getFarmSitesAvailable() > 0) {
			Int2D bestFarm = household.determineBestFarm(potFarm);
			double by = ((ValleyFloor) valley.getField()).plotAt(bestFarm.x,bestFarm.y).getYield();
			//leave the current farm and move to the new better one
			valley.changeFarmLocation(household, bestFarm);
			
			Vector<Int2D> potSettle = ((ValleyFloor) valley.getField()).potentialSettlements(by);
			
			//if there are cells with water which are not farmed and in a zone that is less productive than the zone where the favorite farm plot is located
			if (potSettle.size() > 0) {
				double minDist = Float.POSITIVE_INFINITY;
				Int2D minSettle = null;
				for (Int2D ps : potSettle) {
					if (ValleyFloor.distance(bestFarm, ps) < minDist) {
						minDist = ValleyFloor.distance(bestFarm, ps);
						minSettle = ps;
					}
				}
				if (minDist <= LongHouseValley.waterSourceDistance) {
					xh = minSettle.x;
					yh = minSettle.y;
					settlementFound = true;
				} else {
					settlementFound = false;
				}
				if (settlementFound) {
					potSettle = ((ValleyFloor) valley.getField()).potentialSettlementsRelaxed();
					double minDist2 = Float.POSITIVE_INFINITY;
					Int2D minSettle2 = null;
					Int2D bestSett = new Int2D(xh,yh);
					for (Int2D ps : potSettle) {
						if (ValleyFloor.distance(bestSett, ps) < minDist2) {
							minDist2 = ValleyFloor.distance(bestSett, ps);
							minSettle2 = ps;
						}
					}
					xh = minSettle2.x;
					yh = minSettle2.y;
					moveHousehold(household,valley,new Int2D(xh,yh));
				}
			}
			
			//if no settlement is found yet
			if (!settlementFound) {
				potSettle = ((ValleyFloor) valley.getField()).potentialSettlementsReRelaxed();
				double minDist3 = Float.POSITIVE_INFINITY;
				Int2D minSettle3 = null;
				for (Int2D ps : potSettle) {
					if (ValleyFloor.distance(bestFarm, ps) < minDist3) {
						minDist3 = ValleyFloor.distance(bestFarm, ps);
						minSettle3 = ps;
					}
				}
				if (minDist3 <= LongHouseValley.waterSourceDistance) {
					xh = minSettle3.x;
					yh = minSettle3.y;
					settlementFound = true;
				} else {
					settlementFound = false;
				}
				if (settlementFound) {
					potSettle = ((ValleyFloor) valley.getField()).potentialSettlementsRelaxed();
					double minDist4 = Float.POSITIVE_INFINITY;
					Int2D minSettle4 = null;
					Int2D bestSett = new Int2D(xh,yh);
					for (Int2D ps : potSettle) {
						if (ValleyFloor.distance(bestSett, ps) < minDist4) {
							minDist4 = ValleyFloor.distance(bestSett, ps);
							minSettle4 = ps;
						}
					}
					xh = minSettle4.x;
					yh = minSettle4.y;
					moveHousehold(household,valley,new Int2D(xh,yh));
				}
			}
			
			// if not settlement found, don't worry about nearby watersources...
			if (!settlementFound) {
				potSettle = ((ValleyFloor) valley.getField()).potentialSettlementsReRelaxed();
				double minDist3 = Float.POSITIVE_INFINITY;
				Int2D minSettle3 = null;
				for (Int2D ps : potSettle) {
					if (ValleyFloor.distance(bestFarm, ps) < minDist3) {
						minDist3 = ValleyFloor.distance(bestFarm, ps);
						minSettle3 = ps;
					}
				}
				xh = minSettle3.x;
				yh = minSettle3.y;
				settlementFound = true;
				if (settlementFound) {
					potSettle = ((ValleyFloor) valley.getField()).potentialSettlementsRelaxed();
					double minDist4 = Float.POSITIVE_INFINITY;
					Int2D minSettle4 = null;
					Int2D bestSett = new Int2D(xh,yh);
					for (Int2D ps : potSettle) {
						if (ValleyFloor.distance(bestSett, ps) < minDist4) {
							minDist4 = ValleyFloor.distance(bestSett, ps);
							minSettle4 = ps;
						}
					}
					xh = minSettle4.x;
					yh = minSettle4.y;
					moveHousehold(household,valley,new Int2D(xh,yh));
				}
			}

			//if no possible settlement is found, leave the system
			if (!settlementFound) {
				//household.die(valley);
			}
		
		} else {  //no farms available
			//household.die(valley);
		}
		return settlementFound;
	}
	
	private void harvestConsumption(Household household, LongHouseValley valley) {
		
		//calculate the harvest of the household. Update the stocks of corn available in storage
		double baseYield = ((ValleyFloor) valley.getField()).plotAt(household.getFarmlocation().x,household.getFarmlocation().y).getBaseYield();
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
		harvestConsumption((Household) individual,(LongHouseValley) environment);
		((Household) individual).estimateHarvest();
		//see if needs to move
		if (((Household) individual).getEstimate() < ((Household) individual).getNutritionNeed()) {
			if (findFarmAndSettlement((Household) individual, (LongHouseValley) environment))
				System.out.println("exito al encontrar nueva granja y tal para no morir de hambre");
		}
	}

}
