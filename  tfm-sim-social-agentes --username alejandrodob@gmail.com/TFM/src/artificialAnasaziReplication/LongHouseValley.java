package artificialAnasaziReplication;

import java.util.Vector;

import sim.engine.Schedule;
import sim.engine.SimState;
import sim.engine.Steppable;
import sim.field.grid.SparseGrid2D;
import sim.util.Int2D;
import agent.DemographicItem;
import model.SimpleWorld;

public class LongHouseValley extends SimpleWorld {
	
	private int year = 800;
	public double harvestAdjustment = 0.54;
	public double harvestVariance = 0.4;
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
	private int deathAge = 38;
	private double fertility = 0.155;
	private int fertilityEndsAge = 34;
	private static final int initialNumberHouseholds = 14;
	
	//statistics
	public int numHouseholds = initialNumberHouseholds;

	//population
	//in this case we don't place the agents in the field directly, so we need to keep them somewhere else
	public SparseGrid2D population;
	
	public LongHouseValley(long seed) {
		super(seed);
	}
	
	@Override
	public void start() {
		super.start();
		field = new ValleyFloor();
		population = new SparseGrid2D(ValleyFloor.WIDTH, ValleyFloor.HEIGHT);
		System.out.println("inicialmente hay farmas ocupadas: "+((ValleyFloor) field).ocFarms());
		schedule.scheduleRepeating(schedule.getTime() + 1, 0, (Steppable) field);
		((ValleyFloor) field).water(year);
		((ValleyFloor) field).calculateYield(year);
		((ValleyFloor) field).calculateBaseYield(harvestAdjustment,year);
		//create the initial households and place them randomly in the valley
		for (int i = 0;i<initialNumberHouseholds;i++) {
			Household hh = new Household();
			//random location for farming
			Int2D farmLoc = new Int2D(random.nextInt(((ValleyFloor) field).getWidth()),random.nextInt(((ValleyFloor) field).getHeight()));
			hh.setFarmlocation(farmLoc);
			((ValleyFloor) field).plotAt(hh.getFarmlocation().x,hh.getFarmlocation().y).setOcfarm(true);
			//find a settlement nearby
			if (hh.findInitialSettlementNearFarm((ValleyFloor) field)) {
				//occupy the plot
				((ValleyFloor) field).plotAt(hh.getLocation().x,hh.getLocation().y).incHousholdNum();
				addIndividual(hh,hh.getLocation());
			}
		}
		//add a steppable that increments the year after the agents have stepped in the current year
		Steppable yearIncrem = new Steppable() {
			@Override
			public void step(SimState state) {
				if (year%5==0) { //cada 5 años para no ser un cansino 
					System.out.println("poblacion en el año "+year+": "+ numHouseholds);
					System.out.println("poblacion segun el sparsegrid :"+population.size());
					determinePotentialFarms();
					System.out.println("potential farmsites en el año "+year+": "+farmSitesAvailable);
					System.out.println("parcelas ocupadas con farm en el año "+year+": "+ ((ValleyFloor) field).ocFarms());
					}
				((LongHouseValley) state).setYear(year + 1);
				if (year > 1350) {
					System.out.println("finalmente hay farmas ocupadas: "+((ValleyFloor) field).ocFarms());

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
		population.remove(person);		
	}

	@Override
	public void registerDeath(DemographicItem person) {
		numHouseholds--;
		removeIndividual(person);
		System.out.println(person.toString()+" se muere");
	}

	@Override
	public void registerBirth(DemographicItem newborn, DemographicItem mother) {
		numHouseholds++;
		System.out.println(" acaba de nacer");
	}
	
	@Override
	public void registerMigration(DemographicItem person, Int2D from, Int2D to) {
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
		setFarmSitesAvailable(potFarm.size());
		return potFarm;
	}
	
	public Household createFissionedHousehold(Household parent) {
		Household fisHousehold = new Household();
		fisHousehold.setLocation(new Int2D(parent.getLocation().x,parent.getLocation().y));
		((ValleyFloor) field).plotAt(parent.getLocation().x,parent.getLocation().y).incHousholdNum();
		fisHousehold.setAge(0);
		fisHousehold.setFarmlocation(fisHousehold.getLocation()); //absurd farmlocation so that it is not null
		((ValleyFloor) field).plotAt(fisHousehold.getFarmlocation().x,fisHousehold.getFarmlocation().y).setOcfarm(true);
		//set cornStocks received from parent
		double[] childCornStocks = parent.getAgedCornStocks();
		int ys = Household.yearsOfStock;
		while (ys > -1) {
			childCornStocks[ys] = (maizeGiveToChild / (1 - maizeGiveToChild)) *  childCornStocks[ys];
			ys--;
		}
		fisHousehold.setAgedCornStocks(childCornStocks);
		addIndividual(fisHousehold, fisHousehold.getLocation());
		return fisHousehold; // return so that it can be assigned a proper farm via DemographicBehavior (which is who makes this method call)
	}
	
	public static void main(String[] args){
		doLoop(LongHouseValley.class, args);
		System.exit(0);
	}

}
