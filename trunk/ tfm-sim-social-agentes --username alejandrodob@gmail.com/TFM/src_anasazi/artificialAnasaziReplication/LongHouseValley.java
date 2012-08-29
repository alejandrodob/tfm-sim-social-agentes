package artificialAnasaziReplication;

import java.util.Vector;

import model.SimpleWorld;
import sim.engine.SimState;
import sim.engine.Steppable;
import sim.field.grid.SparseGrid2D;
import sim.util.Bag;
import sim.util.Int2D;
import agent.DemographicItem;
import artificialAnasaziReplication.ValleyFloor.HistoricalSettlement;

public class LongHouseValley extends SimpleWorld {
	
	private int year = 800;
	private int farmSitesAvailable = 0;

	public double harvestAdjustment = 0.54;
	public double harvestVariance = 0.1;
	private int deathAge = 38;
	private double fertility = 0.155;
	private int fertilityEndsAge = 34;
	
	public static final double spatialHarvestVariance = 0.4;
	public static final double farmToResidenceDistance = 1.6;
	public static final double maizeGiveToChild = 0.33;
	public static final int householdMinInitialCorn = 2000;
	public static final int householdMaxInitialCorn = 2400;
	public static final int householdMinInitialAge = 0;
	public static final int householdMaxInitialAge = 29;
	public static final int householdMinNutritionNeed = 800; //160kg/person * 5 persons a household
	public static final int householdMaxNutritionNeed = 800;
	public static final int minFertilityAge = 16;
	public static final int maxFertilityAge = 16;
	private static final int initialNumberHouseholds = 14;
	
	//statistics
	public int numHouseholds() {
		return population.size();
	}
	public int historicalHouseholds() {
		ValleyFloor f = (ValleyFloor) field;
		if (f != null) {
			Bag aux = new Bag(f.hisPopulation.getAllObjects());
			int total = 0;
			for (Object o : aux) {
				HistoricalSettlement hs = (HistoricalSettlement) o;
				total += hs.getNrhouseholds();
			}
			return total;
		}
		else return 0;
	}

	//population
	//in this case we don't place the agents in the field directly, so we need to keep them somewhere else
	public SparseGrid2D population;
	
	public LongHouseValley(long seed) {
		super(seed);
	}
	
	@Override
	public void start() {
		super.start();
		year = 800;
		farmSitesAvailable = 0;
		field = new ValleyFloor();
		population = new SparseGrid2D(ValleyFloor.WIDTH, ValleyFloor.HEIGHT);
		schedule.scheduleRepeating(schedule.getTime() + 1, 0, (Steppable) field);
		((ValleyFloor) field).water(year);
		((ValleyFloor) field).calculateYield(year);
		((ValleyFloor) field).calculateBaseYield(harvestAdjustment);
		//create the initial households and place them randomly in the valley
		for (int i = 0;i<initialNumberHouseholds;i++) {
			Vector<Int2D> potFarms = determinePotentialFarms();
			Household hh = new Household();
			//random location for farming
			boolean rand = false;
			Int2D randomFarm = null;
			while (!rand) {
				randomFarm = new Int2D(random.nextInt(ValleyFloor.WIDTH),random.nextInt(ValleyFloor.HEIGHT));
				rand = !((ValleyFloor) field).plotAt(randomFarm.x, randomFarm.y).isOcfarm();
			}
			hh.setFarmlocation(randomFarm);
			((ValleyFloor) field).plotAt(randomFarm.x, randomFarm.y).ssetOcfarm(true);
			//now a decent one
			potFarms = determinePotentialFarms();
			if (potFarms.size() > 0) {
				Int2D bestFarm = FarmingBehavior.getInstance().determineBestFarm(potFarms,hh);
				changeFarmLocation(hh,bestFarm);
				//find a settlement nearby
				boolean settled = false;
				while (!settled) {
					hh.setLocation(FarmingBehavior.getInstance().findInitialSettlementNearFarm(hh,this));
					((ValleyFloor) field).plotAt(hh.getLocation().x, hh.getLocation().y).incHousholdNum();
					settled = (hh.getLocation()!= null);
					//add the household to the simulation
					if (settled) addIndividual(hh,hh.getLocation());
				}
			} else {
				//no farmsites available, so agent cannot enter the system
				((ValleyFloor) field).plotAt(randomFarm.x, randomFarm.y).ssetOcfarm(false);
			}
		}
		//add a steppable that increments the year after the agents have stepped in the current year
		Steppable yearIncrem = new Steppable() {
			@Override
			public void step(SimState state) {
				((LongHouseValley) state).setYear(year + 1);
				if (year > 1350) {
					state.finish(); //end the simulation at year 1350		
				}
			}
		};
		schedule.scheduleRepeating(schedule.getTime() + 1,0,yearIncrem);
	}

	@Override
	public void addIndividual(DemographicItem person, Int2D location) {
		population.setObjectLocation(person, location);
		person.setStop(schedule.scheduleRepeating(schedule.getTime() + 1, 1, person));
	}

	@Override
	public void removeIndividual(DemographicItem person) {
		((ValleyFloor) field).plotAt(person.getLocation().x,person.getLocation().y).decHouseholdNum();
		((ValleyFloor) field).plotAt(((Household) person).getFarmlocation().x,((Household) person).getFarmlocation().y).ssetOcfarm(false);
		population.remove(person);
	}

	@Override
	public void registerDeath(DemographicItem person) {
		removeIndividual(person);
	}

	@Override
	public void registerBirth(DemographicItem newborn, DemographicItem parent) {
		Household newhh = createFissionedHousehold((Household) parent);
		Vector<Int2D> potFarms = determinePotentialFarms();
		//random location for farming
		boolean rand = false;
		Int2D randomFarm = null;
		while (!rand) {
			randomFarm = new Int2D(random.nextInt(ValleyFloor.WIDTH),random.nextInt(ValleyFloor.HEIGHT));
			rand = !((ValleyFloor) field).plotAt(randomFarm.x, randomFarm.y).isOcfarm();
		}
		newhh.setFarmlocation(randomFarm);
		((ValleyFloor) field).plotAt(randomFarm.x, randomFarm.y).ssetOcfarm(true);
		//now a decent one
		potFarms = determinePotentialFarms();
		if (potFarms.size() > 0) {
			Int2D bestFarm = FarmingBehavior.getInstance().determineBestFarm(potFarms,newhh);
			changeFarmLocation(newhh,bestFarm);
			//find a settlement nearby
			boolean settled = false;
			while (!settled) {
				newhh.setLocation(FarmingBehavior.getInstance().findInitialSettlementNearFarm(newhh, this));
				((ValleyFloor) field).plotAt(newhh.getLocation().x, newhh.getLocation().y).incHousholdNum();
				settled = (newhh.getLocation()!= null);
				//add the household to the simulation
				if (settled) addIndividual(newhh,newhh.getLocation());
			}
		} else {
			//no farmsite available for the new household, so cannot enter the system
			((ValleyFloor) field).plotAt(randomFarm.x, randomFarm.y).ssetOcfarm(false);
		}
	}
	
	@Override
	public void registerMigration(DemographicItem person, Int2D from, Int2D to) {
		Household hh = (Household) person;
		Int2D formerLocation = (from == null)?hh.getLocation():from;
		hh.setLocation(to);
		population.setObjectLocation(hh,to);
		((ValleyFloor) field).plotAt(formerLocation.x, formerLocation.y).decHouseholdNum();
		((ValleyFloor) field).plotAt(to.x, to.y).incHousholdNum();
	}
	
	public int year() {
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

	public int farmSitesAvailable() {
		return farmSitesAvailable;
	}

	public void setFarmSitesAvailable(int farmSitesAvailable) {
		this.farmSitesAvailable = farmSitesAvailable;
	}
	
	public double getHarvestAdjustment() {
		return harvestAdjustment;
	}

	public void setHarvestAdjustment(double harvestAdjustment) {
		this.harvestAdjustment = harvestAdjustment;
	}

	public double getHarvestVariance() {
		return harvestVariance;
	}

	public void setHarvestVariance(double harvestVariance) {
		this.harvestVariance = harvestVariance;
	}

	public Vector<Int2D> determinePotentialFarms() {
		//determine the list of potential locations for a farm to move to. A potential location to farm is a place where nobody is farming and where the baseyield is higher than the minimum amount of food needed and where nobody has build a settlement
		Vector<Int2D> potFarm = ((ValleyFloor) field).determinePotFarms(householdMinNutritionNeed);
		setFarmSitesAvailable(potFarm.size());
		return potFarm;
	}
	
	private Household createFissionedHousehold(Household parent) {
		//just creates the daughter's household from the parental's data. registerBirth will handle then the result
		Household fisHousehold = new Household();
		fisHousehold.setLocation(parent.getLocation());
		fisHousehold.setAge(0);
		//set cornStocks received from parent
		double[] childCornStocks = parent.getAgedCornStocks();
		int ys = Household.yearsOfStock;
		while (ys > -1) {
			childCornStocks[ys] = (maizeGiveToChild / (1 - maizeGiveToChild)) *  childCornStocks[ys];
			ys--;
		}
		fisHousehold.setAgedCornStocks(childCornStocks);
		return fisHousehold;
	}
	
	public void changeFarmLocation(Household hh, Int2D farmDest) {
		Int2D formerLocation = hh.getFarmlocation();
		((ValleyFloor) field).plotAt(farmDest.x, farmDest.y).ssetOcfarm(true);
		((ValleyFloor) field).plotAt(formerLocation.x, formerLocation.y).ssetOcfarm(false);
		hh.setFarmlocation(farmDest);
	}
	
	public static void main(String[] args){
		doLoop(LongHouseValley.class, args);
		System.exit(0);
	}

}
