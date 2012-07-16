package artificialAnasaziReplication;

import java.util.Vector;

import sim.util.Int2D;

import ec.util.MersenneTwisterFast;
import agent.DemographicItem;
import agent.behavior.BehaviorModule;
import model.SimpleWorld;

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
		findFarmAndSettlement(valley.createFissionedHousehold(household),valley);
		valley.registerBirth(null, null);
	}
	
	private void moveHousehold(Household household, LongHouseValley valley, Int2D dest) {
		//leave the previous settlement
		((ValleyFloor) valley.getField()).getFloor()[household.getLocation().x][household.getLocation().y].decHouseholdNum();
		//occupy the new one
		((ValleyFloor) valley.getField()).getFloor()[dest.x][dest.y].incHousholdNum();
		household.setLocation(dest);
	}
	
	private void findFarmAndSettlement(Household household, LongHouseValley valley) {
		
		//find a new spot for the settlement (might remain the same location as before)
		int searchCount = 0;
		boolean settlementFound = false;
		int xh = 0;
		int yh = 0;
		
		//if there are no potential farm spots available the agent is removed from the system
		Vector<Int2D> potFarm = valley.determinePotentialFarms();
		if (valley.getFarmSitesAvailable() > 0) {
			Int2D bestFarm = household.determineBestFarm(potFarm);
			double by = ((ValleyFloor) valley.getField()).getFloor()[bestFarm.x][bestFarm.y].getYield();
			//leave the current farm and move to the new better one
			Int2D currentFarm = household.getFarmlocation();
			((ValleyFloor) valley.getField()).getFloor()[currentFarm.x][currentFarm.y].setOcfarm(false);
			household.setFarmlocation(bestFarm);
			((ValleyFloor) valley.getField()).getFloor()[bestFarm.x][bestFarm.y].setOcfarm(true);
			
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
				household.die(valley);
			}
		
		} else {  //no farms available
			household.die(valley);
		}
	}
	
	private void age(Household household) {
		household.setAge(household.getAge()+1);
	}

	@Override
	public void behave(DemographicItem individual, SimpleWorld environment) {
		death((Household) individual, (LongHouseValley) environment);
		((Household) individual).estimateHarvest();
		//see if needs to move
		if (((Household) individual).getEstimate() < ((Household) individual).getNutritionNeed()) {
			findFarmAndSettlement((Household) individual, (LongHouseValley) environment);
		}
		//see if household fissions
		if ( ((Household) individual).getAge() > ((Household) individual).getFertilityAge() && 
				((Household) individual).getAge() <= ((LongHouseValley) environment).getFertilityEndsAge() &&
				(random.nextFloat() < ((LongHouseValley) environment).getFertility())) {
			if (((LongHouseValley) environment).determinePotentialFarms().size() > 0) {
				fissioning((Household) individual, (LongHouseValley) environment);
			}
		}
		age((Household) individual);
	}

}
