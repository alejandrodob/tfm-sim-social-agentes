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
	private int householdMinNutritionNeed;
	public final static double waterSourceDistance = 16.0;

	
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
}
