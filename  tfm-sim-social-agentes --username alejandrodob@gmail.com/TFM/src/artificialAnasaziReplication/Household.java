package artificialAnasaziReplication;

import java.util.Vector;

import ec.util.MersenneTwisterFast;

import sim.util.Int2D;
import agent.DemographicItem;
import agent.behavior.ListBehavior;

public class Household extends DemographicItem {
	
	private static MersenneTwisterFast random = new MersenneTwisterFast();

	private Int2D farmlocation;
	private Int2D farmplot; //creo que es una ubicacion, pero ni warriuls-->es lo mismo que farmlocation
	private double lastHarvest;
	private double estimate;
	private int age;
	private int fertilityAge;
	private double[] agedCornStocks = new double[3];
	private int nutritionNeed;
	private double nutritionNeedRemaining;
	public final static int yearsOfStock = 2; //number of years the corn harvest can be stored
	
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
		setNutritionNeed(LongHouseValley.householdMinNutritionNeed + random.nextInt(LongHouseValley.householdMaxNutritionNeed - LongHouseValley.householdMinNutritionNeed));
		setFertilityAge(LongHouseValley.minFertilityAge + random.nextInt(LongHouseValley.maxFertilityAge - LongHouseValley.minFertilityAge));
		setLastHarvest(0);
	}

	public Int2D getFarmlocation() {
		return farmlocation;
	}
	public void setFarmlocation(Int2D farmlocation) {
		this.farmlocation = farmlocation;
	}
	public Int2D getFarmplot() {
		return farmplot;
	}
	public void setFarmplot(Int2D farmplot) {
		this.farmplot = farmplot;
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
	
	public void die() {
		//remove this household from its settlement
		((ValleyFloor) field).getFloor()[location.x][location.y].decHouseholdNum();
		//remove the farmplot this household was farming
		((ValleyFloor) field).getFloor()[farmlocation.x][farmlocation.y].setOcfarm(false);
		//remove the agent from the simulation
		stop.stop();
	}
}
