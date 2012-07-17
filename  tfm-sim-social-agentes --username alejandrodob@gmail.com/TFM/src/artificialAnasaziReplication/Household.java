package artificialAnasaziReplication;

import java.util.Vector;

import ec.util.MersenneTwisterFast;

import sim.util.Int2D;
import agent.DemographicItem;
import agent.behavior.ListBehavior;

public class Household extends DemographicItem {
	
	private static MersenneTwisterFast random = new MersenneTwisterFast();

	private Int2D farmlocation;
	private double lastHarvest;
	private double estimate;
	private int age;
	private int fertilityAge;
	private double[] agedCornStocks = new double[3];
	private int nutritionNeed;
	private double nutritionNeedRemaining;
	public final static int yearsOfStock = 2; //number of years the corn harvest can be stored
	///////////hay que inicializar el field
	
	public Household() {
		behavior = new ListBehavior();
		
		//add the behaviors. it's important that farmingBehavior is added first as it should be executed first
		addBehaviorModule(FarmingBehavior.getInstance());
		addBehaviorModule(DemographicBehaviorHousehold.getInstance());
		
		//the next corresponds to the inithousehold method in NetLogo
		agedCornStocks[0] = LongHouseValley.householdMinInitialCorn + random.nextDouble() * (LongHouseValley.householdMaxInitialCorn - LongHouseValley.householdMinInitialCorn);
		agedCornStocks[1] = LongHouseValley.householdMinInitialCorn + random.nextDouble() * (LongHouseValley.householdMaxInitialCorn - LongHouseValley.householdMinInitialCorn);
		agedCornStocks[2] = LongHouseValley.householdMinInitialCorn + random.nextDouble() * (LongHouseValley.householdMaxInitialCorn - LongHouseValley.householdMinInitialCorn);
		setAge(LongHouseValley.householdMinInitialAge + random.nextInt(LongHouseValley.householdMaxInitialAge));
		setNutritionNeed(LongHouseValley.householdMinNutritionNeed + random.nextInt(LongHouseValley.householdMaxNutritionNeed - LongHouseValley.householdMinNutritionNeed + 1));
		setFertilityAge(LongHouseValley.minFertilityAge + random.nextInt(LongHouseValley.maxFertilityAge - LongHouseValley.minFertilityAge + 1));
		setLastHarvest(0);
	}

	public Int2D getFarmlocation() {
		return farmlocation;
	}
	public void setFarmlocation(Int2D farmlocation) {
		this.farmlocation = farmlocation;
	}
	public double getLastHarvest() {
		return lastHarvest;
	}
	public void setLastHarvest(double lastHarvest2) {
		this.lastHarvest = lastHarvest2;
	}
	public double getEstimate() {
		return estimate;
	}
	public void setEstimate(double estimate) {
		this.estimate = estimate;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public int getFertilityAge() {
		return fertilityAge;
	}
	public void setFertilityAge(int fertilityAge) {
		this.fertilityAge = fertilityAge;
	}
	public double[] getAgedCornStocks() {
		return agedCornStocks;
	}
	public void setAgedCornStocks(double[] agedCornStocks) {
		this.agedCornStocks = agedCornStocks;
	}
	public int getNutritionNeed() {
		return nutritionNeed;
	}
	public void setNutritionNeed(int nutritionNeed) {
		this.nutritionNeed = nutritionNeed;
	}
	public double getNutritionNeedRemaining() {
		return nutritionNeedRemaining;
	}
	public void setNutritionNeedRemaining(double nutritionNeedRemaining) {
		this.nutritionNeedRemaining = nutritionNeedRemaining;
	}
	
	public void estimateHarvest() {
		double total = 0;
		int ys = yearsOfStock - 1;
	        while (ys > -1) {
	        	total += getAgedCornStocks()[ys];
	        	ys --;
	        }
	        setEstimate(total + getLastHarvest());
	}

	public Int2D determineBestFarm(Vector<Int2D> potfarms) {
		Int2D existingFarm = new Int2D(getFarmlocation().x,getFarmlocation().y);
		Int2D bestFarm = null;
		double distancetns = 1000;
		for (Int2D farm : potfarms) {
			double dist = ValleyFloor.distance(existingFarm, farm);
			if (dist < distancetns) {
				bestFarm = farm;
				distancetns = dist;
			}
		}
		return bestFarm;
	}
	
	public boolean findInitialSettlementNearFarm(ValleyFloor valley) {

		boolean settlementFound = false;
		int xh = 0;
		int yh = 0;

		double by = valley.plotAt(farmlocation.x,farmlocation.y).getYield();
		Vector<Int2D> potSettle = valley.potentialSettlements(by);

		//if there are cells with water which are not farmed and in a zone that is less productive than the zone where the favorite farm plot is located
		if (potSettle.size() > 0) {
			double minDist = Float.POSITIVE_INFINITY;
			Int2D minSettle = null;
			for (Int2D ps : potSettle) {
				if (ValleyFloor.distance(farmlocation, ps) < minDist) {
					minDist = ValleyFloor.distance(farmlocation, ps);
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
				potSettle = valley.potentialSettlementsRelaxed();
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
			}
		}

		//if no settlement is found yet
		if (!settlementFound) {
			potSettle = valley.potentialSettlementsReRelaxed();
			double minDist3 = Float.POSITIVE_INFINITY;
			Int2D minSettle3 = null;
			for (Int2D ps : potSettle) {
				if (ValleyFloor.distance(farmlocation, ps) < minDist3) {
					minDist3 = ValleyFloor.distance(farmlocation, ps);
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
				potSettle = valley.potentialSettlementsRelaxed();
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
			}
		}

		// if not settlement found, don't worry about nearby watersources...
		if (!settlementFound) {
			potSettle = valley.potentialSettlementsReRelaxed();
			double minDist3 = Float.POSITIVE_INFINITY;
			Int2D minSettle3 = null;
			for (Int2D ps : potSettle) {
				if (ValleyFloor.distance(farmlocation, ps) < minDist3) {
					minDist3 = ValleyFloor.distance(farmlocation, ps);
					minSettle3 = ps;
				}
			}
			xh = minSettle3.x;
			yh = minSettle3.y;
			settlementFound = true;
			if (settlementFound) {
				potSettle = valley.potentialSettlementsRelaxed();
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
			}
		}
		if (settlementFound) {
			setLocation(new Int2D(xh,yh));
		}
		return settlementFound;
	}
	
	/*public void die(ValleyFloor floor) {
		//remove this household from its settlement
		//((ValleyFloor) field)
		floor.getFloor()[location.x][location.y].decHouseholdNum();
		//remove the farmplot this household was farming
		//((ValleyFloor) field)
		floor.getFloor()[farmlocation.x][farmlocation.y].setOcfarm(false);
		//remove the agent from the simulation
		stop.stop();
	}*/
	public void die(LongHouseValley valley) {
		//remove this household from its settlement
		//((ValleyFloor) field)
		((ValleyFloor)valley.getField()).plotAt(location.x,location.y).decHouseholdNum();
		//remove the farmplot this household was farming
		//((ValleyFloor) field)
		((ValleyFloor)valley.getField()).plotAt(farmlocation.x,farmlocation.y).setOcfarm(false);
		//remove the agent from the simulation
		valley.registerDeath(this);
		stop.stop();
	}
	
	@Override
	public String toString() {
		return new String("");
		//implement if want to show a label with this string in the GUI
	}
}
