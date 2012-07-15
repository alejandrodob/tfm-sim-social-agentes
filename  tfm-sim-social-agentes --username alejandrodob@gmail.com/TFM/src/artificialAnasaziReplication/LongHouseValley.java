package artificialAnasaziReplication;

import java.util.Vector;

import sim.util.Int2D;
import agent.DemographicItem;
import model.SimpleWorld;

public class LongHouseValley extends SimpleWorld {
	
	private int year;
	public double harvestAdjustment;
	public double harvestVariance;
	private int farmSitesAvailable = 0;
	public static final double waterSourceDistance = 16.0;
	public static final double maizeGiveToChild = 0.33;
	public static final int householdMinInitialCorn = 2000;
	public static final int householdMaxInitialCorn = 2400;
	public static final int householdMinInitialAge = 0;
	public static final int householdMaxInitialAge = 29;
	public static final int householdMinNutritionNeed = 800; //160kg/person * 5 persons in a household
	public static final int householdMaxNutritionNeed = 800;
	public static final int minFertilityAge = 16;
	public static final int maxFertilityAge = 16;
	private int deathAge;
	private double fertility;
	private int fertilityEndsAge;

	
	public LongHouseValley(long seed) {
		super(seed);
		field = new ValleyFloor();
	}

	@Override
	public void addIndividual(DemographicItem person, Int2D location) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeIndividual(DemographicItem person) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerDeath(DemographicItem person) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerBirth(DemographicItem newborn, DemographicItem mother) {
		// TODO Auto-generated method stub
		
	}
	
	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getDeathAge() {
		return deathAge;
	}

	public void setDeathAge(int deathAge) {
		this.deathAge = deathAge;
	}

	public double getFertility() {
		return fertility;
	}

	public void setFertility(double fertility) {
		this.fertility = fertility;
	}

	public int getFertilityEndsAge() {
		return fertilityEndsAge;
	}

	public void setFertilityEndsAge(int fertilityEndsAge) {
		this.fertilityEndsAge = fertilityEndsAge;
	}

	public int getFarmSitesAvailable() {
		return farmSitesAvailable;
	}

	public void setFarmSitesAvailable(int farmSitesAvailable) {
		this.farmSitesAvailable = farmSitesAvailable;
	}
	
	public Vector<Int2D> determinePotentialFarms() {
		//determine the list of potential locations for a farm to move to. A potential location to farm is a place where nobody is farming and where the baseyield is higher than the minimum amount of food needed and where nobody has build a settlement
		Vector<Int2D> potFarm = ((ValleyFloor) field).determinePotFarms(householdMinNutritionNeed);
		farmSitesAvailable = potFarm.size();
		return potFarm;
	}
	
	public void createFissionedHousehold(Household parent) {
		Household fisHousehold = new Household();
		fisHousehold.setLocation(new Int2D(parent.getLocation().x,parent.getLocation().y));
		fisHousehold.setAge(0);
		//set cornStocks received from parent
		double[] childCornStocks = parent.getAgedCornStocks();
		int ys = Household.yearsOfStock;
		while (ys > -1) {
			childCornStocks[ys] = (maizeGiveToChild / (1 - maizeGiveToChild)) *  childCornStocks[ys];
			ys--;
		}
		fisHousehold.setAgedCornStocks(childCornStocks);
		addIndividual(fisHousehold, fisHousehold.getLocation());
	}
}
