package artificialAnasaziReplication;

import java.util.Vector;

import sim.util.Int2D;
import agent.DemographicItem;
import agent.behavior.ListBehavior;
import agent.behavior.PriorityBehavior;
import ec.util.MersenneTwisterFast;

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
	
	public Household() {
		behavior = new PriorityBehavior();
		
		//add the behaviors. give priority to farmingBehavior as it should be executed first
		addBehaviorModule(DemographicBehaviorHousehold.getInstance(),0);
		addBehaviorModule(FarmingBehavior.getInstance(),-1);
		
		//the next corresponds to the inithousehold method in NetLogo
		agedCornStocks[0] = LongHouseValley.householdMinInitialCorn + random.nextDouble() * (LongHouseValley.householdMaxInitialCorn - LongHouseValley.householdMinInitialCorn);
		agedCornStocks[1] = LongHouseValley.householdMinInitialCorn + random.nextDouble() * (LongHouseValley.householdMaxInitialCorn - LongHouseValley.householdMinInitialCorn);
		agedCornStocks[2] = LongHouseValley.householdMinInitialCorn + random.nextDouble() * (LongHouseValley.householdMaxInitialCorn - LongHouseValley.householdMinInitialCorn);
		setAge(LongHouseValley.householdMinInitialAge + random.nextInt(LongHouseValley.householdMaxInitialAge));
		setNutritionNeed(LongHouseValley.householdMinNutritionNeed + random.nextInt(LongHouseValley.householdMaxNutritionNeed - LongHouseValley.householdMinNutritionNeed + 1));
		setFertilityAge(LongHouseValley.minFertilityAge + random.nextInt(LongHouseValley.maxFertilityAge - LongHouseValley.minFertilityAge + 1));
		setNutritionNeedRemaining(0);
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
	
	public void die(LongHouseValley valley) {
		valley.registerDeath(this);
		stop.stop();
	}
	
}
