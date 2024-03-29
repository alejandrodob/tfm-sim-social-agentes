package pruebasAjuste;

import artificialAnasaziReplication.LongHouseValley;

public class AjusteParametro {

	
	public static void main(String[] args) {
		int bestDeathAge1 = 0;
		int bestFertAge1 = 0;
		double bestFert1 = 0;
		double bestHarvVar1 = 0;
		double bestSpatialHarv1 = 0;
		double bestHarvAdjus1 = 0;
		double bestNormPop1 = Double.MAX_VALUE;
		
		int bestDeathAge2 = 0;
		int bestFertAge2 = 0;
		double bestFert2 = 0;
		double bestHarvVar2 = 0;
		double bestSpatialHarv2 = 0;
		double bestHarvAdjus2 = 0;
		double bestNormPop2 = Double.MAX_VALUE;
		
		double bestSpatialHarv1car = 0;
		double bestHarvAdjus1car = 0;
		double bestNormCar1 = Double.MAX_VALUE;

		double bestSpatialHarv2car = 0;
		double bestHarvAdjus2car = 0;
		double bestNormCar2 = Double.MAX_VALUE;
		
		for (int death = 36; death <= 38; death += 2) {
			for (int fertAge = 32; fertAge <= 34; fertAge += 2) {
				for(double fert = 0.125; fert <= 0.155; fert += 0.015) {
					for (double harvVar = 0; harvVar <= 0.2; harvVar += 0.05) {
						for (double harvAdjus = 0.54; harvAdjus <= 0.6; harvAdjus += 0.02) {
							for (double spatialHarvVar = 0.3; spatialHarvVar <= 0.5; spatialHarvVar += 0.1) {
								int jobs = 30;
								double l1pop = 0;
								double l2pop = 0;
								//double l1car = 0;
								//double l2car = 0;
								LongHouseValley state = new LongHouseValley(System.currentTimeMillis());
								state.setDeathAge(death);
								state.setFertilityEndsAge(fertAge);
								state.setFertility(fert);
								state.setHarvestAdjustment(harvAdjus);
								state.setHarvestVariance(harvVar);
								state.setSpatialHarvestVariance(spatialHarvVar);
								state.nameThread();
								for(int job = 0; job < jobs; job++) {
									state.setJob(job);
									state.start();
									do
										if (!state.schedule.step(state)) break;
									while(state.schedule.getSteps() < 551);
//									l1pop += state.l1Pop();
//									l2pop += state.l2Pop();
//									l1car += state.l1Car();
//									l2car += state.l2Car();
									state.finish();
								}
								if (l1pop/jobs < bestNormPop1) {
									bestNormPop1 = l1pop/jobs;
									bestDeathAge1 = death;
									bestFertAge1 = fertAge;
									bestFert1 = fert;
									bestHarvVar1 = harvVar;
									bestSpatialHarv1 = spatialHarvVar;
									bestHarvAdjus1 = harvAdjus;
								}
								if (l2pop/jobs < bestNormPop2) {
									bestNormPop2 = l2pop/jobs;
									bestDeathAge2 = death;
									bestFertAge2 = fertAge;
									bestFert2 = fert;
									bestHarvVar2 = harvVar;
									bestSpatialHarv2 = spatialHarvVar;
									bestHarvAdjus2 = harvAdjus;
								}
								/*if (l1car/jobs < bestNormCar1) {
									bestNormCar1 = l1car/jobs;
									bestSpatialHarv1car = spatialHarvVar;
									bestHarvAdjus1car = harvAdjus;
								}
								if (l2car/jobs < bestNormCar2) {
									bestNormCar2 = l2car/jobs;
									bestSpatialHarv2car = spatialHarvVar;
									bestHarvAdjus2car = harvAdjus;
								}*/
							}
						}
					}
				}
			}
		}
		System.out.println("Resultados: \nNorma 1: "+bestNormPop1+"\ndeathAge: "+bestDeathAge1+"\nfertilityEnsAge: "+bestFertAge1+"\nfertility: "+bestFert1+"\nharvestVariance: "+bestHarvVar1+"\nspatialHarvestVariance: "+bestSpatialHarv1+"\nharvestAdjustment: "+bestHarvAdjus1);
		//System.out.println("Capacidad Carga norma 1: "+bestNormCar1+"  harvestAdjustment: "+bestHarvAdjus1car+"   spatialHarvestVariance: "+bestSpatialHarv1car);
		System.out.println("Resultados: \nNorma 2: "+bestNormPop2+"\ndeathAge: "+bestDeathAge2+"\nfertilityEnsAge: "+bestFertAge2+"\nfertility: "+bestFert2+"\nharvestVariance: "+bestHarvVar2+"\nspatialHarvestVariance: "+bestSpatialHarv2+"\nharvestAdjustment: "+bestHarvAdjus2);
		//System.out.println("Capacidad Carga norma 2: "+bestNormCar2+"  harvestAdjustment: "+bestHarvAdjus2car+"   spatialHarvestVariance: "+bestSpatialHarv2car);
		System.exit(0);
	}
}


/*///////////////////A continuaci�n el c�digo de la versi�n de LongHouseValley que necesita esta clase

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

	public double harvestAdjustment = 0.6;
	public double harvestVariance = 0.4;
	private int deathAge = 36;
	private double fertility = 0.125;
	private int fertilityEndsAge = 32;
	public double spatialHarvestVariance = 0.4;
	
	public static final double farmToResidenceDistance = 16;
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
	public int[] historicalPopRegistry = new int[551];
	public int[] populationRegistry = new int[551];
	public int[] carryingRegistry = new int[551];

	//population
	//in this case we don't place the agents in the field directly, so we need to keep them somewhere else
	public SparseGrid2D population;
	
	public LongHouseValley(long seed) {
		super(seed);
	}
	
	@Override
	public void start() {
		super.start();
		historicalPopRegistry = new int[551];
		populationRegistry = new int[551];
		carryingRegistry = new int[551];
		year = 800;
		farmSitesAvailable = 0;
		field = new ValleyFloor(spatialHarvestVariance);
		population = new SparseGrid2D(ValleyFloor.WIDTH, ValleyFloor.HEIGHT);
		schedule.scheduleRepeating(schedule.getTime() + 1, 0, (Steppable) field);
		((ValleyFloor) field).water(year);
		((ValleyFloor) field).calculateYield(year);
		((ValleyFloor) field).calculateBaseYield(harvestAdjustment);
		//create the initial households and place them randomly in the valley
		for (int i = 0;i<initialNumberHouseholds;i++) {
			Vector<Int2D> potFarms = determinePotentialFarms();
			Household hh = new Household();
			//random location for farming and settling
			boolean rand = false;
			Int2D randomLoc = null;
			while (!rand) {
				randomLoc = new Int2D(random.nextInt(ValleyFloor.WIDTH),random.nextInt(ValleyFloor.HEIGHT));
				rand = !((ValleyFloor) field).plotAt(randomLoc.x, randomLoc.y).isOcfarm();
			}
			hh.setFarmlocation(randomLoc);
			hh.setLocation(randomLoc);
			((ValleyFloor) field).plotAt(randomLoc.x, randomLoc.y).ssetOcfarm(true);
			((ValleyFloor) field).plotAt(randomLoc.x, randomLoc.y).incHouseholdNum();
			//now a decent one
			potFarms = determinePotentialFarms();
			if (potFarms.size() > 0) {
				Int2D bestFarm = FarmingBehavior.getInstance().determineBestFarm(potFarms,hh);
				changeFarmLocation(hh,bestFarm);
				//find a settlement nearby
				boolean settled = false;
				while (!settled) {
					hh.setLocation(FarmingBehavior.getInstance().findInitialSettlementNearFarm(hh,this));
					((ValleyFloor) field).plotAt(hh.getLocation().x, hh.getLocation().y).incHouseholdNum();
					settled = (hh.getLocation()!= null);
					//add the household to the simulation
					if (settled) addIndividual(hh,hh.getLocation());
				}
			} else {
				//no farmsites available, so agent cannot enter the system
				((ValleyFloor) field).plotAt(randomLoc.x, randomLoc.y).ssetOcfarm(false);
			}
			((ValleyFloor) field).plotAt(randomLoc.x, randomLoc.y).decHouseholdNum();
		}
		//add a steppable that increments the year after the agents have stepped in the current year
		Steppable yearIncrem = new Steppable() {
			@Override
			public void step(SimState state) {
				((LongHouseValley) state).setYear(year + 1);
				historicalPopRegistry[year-801]=historicalHouseholds();
				populationRegistry[year-801]=population.size();
				carryingRegistry[year-801] = ((ValleyFloor) field).calculateCarryingCapacity();
				if (year > 1350) {
//					System.out.println("L1 population :"+l1norm(populationRegistry,historicalPopRegistry));
//					System.out.println("L1 carrying :"+l1norm(carryingRegistry,historicalPopRegistry));
//					System.out.println("L2 population :"+l2norm(populationRegistry,historicalPopRegistry));
//					System.out.println("L2 carrying :"+l2norm(carryingRegistry,historicalPopRegistry));
//					state.finish(); //end the simulation at year 1350		
				}
			}
		};
		schedule.scheduleRepeating(schedule.getTime() + 1,0,yearIncrem);
	}

	public double l1norm(int[] uno, int[] historical) {
		double norm = 0;
		for (int i = 0;i<uno.length;i++) {
			norm += Math.abs(uno[i] - historical[i]); 
		}
		return norm;
	}
	
	public double l2norm(int[] uno, int[] historical) {
		double norm = 0;
		for (int i = 0;i<uno.length;i++) {
			norm += (uno[i] - historical[i])*(uno[i] - historical[i]); 
		}
		return Math.sqrt(norm);
	}
	
	public double l1Pop(){
		return l1norm(populationRegistry,historicalPopRegistry);
	}
	public double l2Pop(){
		return l2norm(populationRegistry,historicalPopRegistry);
	}
	public double l1Car(){
		return l1norm(carryingRegistry,historicalPopRegistry);
	}
	public double l2Car(){
		return l2norm(carryingRegistry,historicalPopRegistry);
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
				((ValleyFloor) field).plotAt(newhh.getLocation().x, newhh.getLocation().y).incHouseholdNum();
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
		((ValleyFloor) field).plotAt(to.x, to.y).incHouseholdNum();
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
	
		public double getSpatialHarvestVariance() {
		return spatialHarvestVariance;
	}

	public void setSpatialHarvestVariance(double spatialHarvestVariance) {
		this.spatialHarvestVariance = spatialHarvestVariance;
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


*/