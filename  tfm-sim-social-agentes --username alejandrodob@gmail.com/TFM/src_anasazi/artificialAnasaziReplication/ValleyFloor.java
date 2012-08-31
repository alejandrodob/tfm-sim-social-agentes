package artificialAnasaziReplication;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Scanner;
import java.util.Vector;

import sim.engine.SimState;
import sim.field.grid.ObjectGrid2D;
import sim.field.grid.SparseGrid2D;
import sim.util.Int2D;
import ec.util.MersenneTwisterFast;
import field.MutableField2D;

public class ValleyFloor extends MutableField2D {
	
	public static final int WIDTH = 80;
	public static final int HEIGHT = 120;
	private static MersenneTwisterFast random = new MersenneTwisterFast();
	private ObjectGrid2D grid = new ObjectGrid2D(WIDTH,HEIGHT);
	private boolean streamsexist;
	private boolean alluviumexist;
	private Vector<Waterpoint> waterpoints;
	private Vector<Integer> mapdata;
	private Vector<Integer> waterdata;
	private Vector<Double> apdsidata;
	private Vector<Double> environmentdata;
	private Vector<Double> historicaldata;
	private Vector<HistoricalSettlement> historicalSettlements;
	
	//where the historical settlements will be represented
	public SparseGrid2D hisPopulation = new SparseGrid2D(WIDTH,HEIGHT);
	
	public enum Zone {
		General, North, NorthDunes, Mid, MidDunes, Natural, Upland, Kinbiko, Empty
	}
	
	private enum MaizeZone {
		Yield1, Yield2, Yield3, SandDune, NoYield, Empty
	}
	
	//the next inner class represents a plot (100m x 100m size) in the valley, the equivalent to 
	//a patch in NetLogo
	public class Plot {
		private Color color;
		private boolean watersource;
		private Zone zone;
		private double apdsi;
		private double hydro;
		private double quality;
		private MaizeZone maizeZone;
		private double yield;
		private double BaseYield;
		private boolean ocfarm;
		private int ochousehold;
		
		public Color whatColor() {
			return color;
		}
		public void setColor(Color color) {
			this.color = color;
		}
		public boolean isWatersource() {
			return watersource;
		}
		public void ssetWatersource(boolean watersource) {
			this.watersource = watersource;
		}
		public Zone getZone() {
			return zone;
		}
		public void setZone(Zone zone) {
			this.zone = zone;
		}
		public double getApdsi() {
			return apdsi;
		}
		public void ssetApdsi(double apdsi) {
			this.apdsi = apdsi;
		}
		public double getHydro() {
			return hydro;
		}
		public void ssetHydro(double generalhydro) {
			this.hydro = generalhydro;
		}
		public double getQuality() {
			return quality;
		}
		public void ssetQuality(double quality) {
			this.quality = quality;
		}
		public MaizeZone getMaizeZone() {
			return maizeZone;
		}
		public void ssetMaizeZone(MaizeZone maizeZone) {
			this.maizeZone = maizeZone;
		}
		public double getYield() {
			return yield;
		}
		public void ssetYield(double yield) {
			this.yield = yield;
		}
		public double getBaseYield() {
			return BaseYield;
		}
		public void ssetBaseYield(double d) {
			BaseYield = d;
		}
		public boolean isOcfarm() {
			return ocfarm;
		}
		public void ssetOcfarm(boolean ocfarm) {
			this.ocfarm = ocfarm;
		}
		public int getOchousehold() {
			return ochousehold;
		}
		public void ssetOchousehold(int ochousehold) {
			this.ochousehold = ochousehold;
		}
			
		public void calculateBaseYield(double harvestAdjustment) {
			ssetBaseYield(getYield() * getQuality() * harvestAdjustment);
		}
		
		public void incHouseholdNum() {
			ssetOchousehold(getOchousehold() + 1);
		}
		
		public void decHouseholdNum() {
			ssetOchousehold(getOchousehold() - 1);
		}
		
		public Plot() {
			ocfarm = false;
			ochousehold = 0;
		}
	}
	
	public class Waterpoint {
		final int x;
		final int y;
		final int sarg;
		final int meterNorth;
		final int meterEast;
		final int typeWater;
		final int startDate;
		final int endDate;
		
		public Waterpoint(int x, int y, int sarg, int meterNorth, int meterEast, int typeWater, int startDate, int endDate) {
			this.x = x;
			this.y = y;
			this.sarg = sarg;
			this.meterNorth = meterNorth;
			this.meterEast = meterEast;
			this.typeWater = typeWater;
			this.startDate = startDate;
			this.endDate = endDate;
		}
	}
	
	public class HistoricalSettlement {
		Int2D location;
		double SARG;
		double meterNorth;
		double meterEast;
		double startdate;
		double enddate;
		double mediandate;
		double typeset;
		double sizeset;
		double description;
		double roomcount;
		double elevation;
		double baselinehouseholds;
		int nrhouseholds;
		boolean visible;

		public HistoricalSettlement(Int2D location, double sARG,
				double meterNorth, double meterEast, double startdate,
				double enddate, double mediandate, double typeset, double sizeset,
				double description, double roomcount, double elevation,
				double baselinehouseholds, int nrhouseholds, boolean visible) {
			this.location = location;
			SARG = sARG;
			this.meterNorth = meterNorth;
			this.meterEast = meterEast;
			this.startdate = startdate;
			this.enddate = enddate;
			this.mediandate = mediandate;
			this.typeset = typeset;
			this.sizeset = sizeset;
			this.description = description;
			this.roomcount = roomcount;
			this.elevation = elevation;
			this.baselinehouseholds = baselinehouseholds;
			this.nrhouseholds = nrhouseholds;
			this.visible = visible;
		}
		
		public double getStartdate() {
			return startdate;
		}

		public void setStartdate(double startdate) {
			this.startdate = startdate;
		}

		public double getEnddate() {
			return enddate;
		}

		public void setEnddate(double enddate) {
			this.enddate = enddate;
		}

		public int getNrhouseholds() {
			return nrhouseholds;
		}

		public void setNrhouseholds(int nrhouseholds) {
			this.nrhouseholds = nrhouseholds;
		}

		public void checkVisibility(int year) {
			//if in the year "year" the settlement existed, visibility will be set to true, 
			//otherwise it will be set to false
			visible = ((year >= startdate) && (year < enddate) && ((int) typeset == 1));
		}
		
		public void setnrhouseholds(int year) {
			if ((year > mediandate) && (year != enddate)) {
				nrhouseholds = (int) Math.ceil((baselinehouseholds * (enddate - year) / (enddate - mediandate)));
				if (nrhouseholds < 1) nrhouseholds = 1;
			}
			if ((year <= mediandate) && (mediandate != startdate)) {
				nrhouseholds = (int) Math.ceil((baselinehouseholds * (year - startdate) / (mediandate - startdate)));
				if (nrhouseholds < 1) nrhouseholds = 1;
			}
		}
		
		@Override
		public String toString() {
			return new String(""+nrhouseholds);
		}
	}
	
	public ValleyFloor() {
		super(WIDTH,HEIGHT);
		initMap();
	}

	@Override
	public void step(SimState state) {
		int year = ((LongHouseValley) state).year();
		calculateYield(year);
		calculateBaseYield(((LongHouseValley) state).harvestAdjustment);
		water(year);
		updateHistoricalData(year);
	}
	
	/**Updates the grid of historical settlements, acording to their existence dates.
	 * Returns the population (number of historical households,
	 * i.e. sum of the households in each settlement*/
	public int updateHistoricalData(int year) {
		int population = 0;
		for (HistoricalSettlement hs : historicalSettlements) {
			hs.checkVisibility(year);
			hs.setnrhouseholds(year);
			if (hs.visible){
				hisPopulation.setObjectLocation(hs, hs.location);
				population+=hs.nrhouseholds;
			} else {
				hisPopulation.remove(hs);
			}
		}
		return population;
	}
	
	public void calculateBaseYield(double harvestAdjustment) {
		for (int x = 0;x< WIDTH;x++) {
			for (int y = 0;y<HEIGHT;y++) {
				plotAt(x,y).calculateBaseYield(harvestAdjustment);
			}
		}
	}
	
	public int calculateCarryingCapacity() {
		int cc = 0;
		for (int x = 0;x< WIDTH;x++) {
			for (int y = 0;y<HEIGHT;y++) {
				if (plotAt(x,y).getBaseYield() >= LongHouseValley.householdMinNutritionNeed) {
					cc++;
				}
			}
		}
		return cc;
	}
	
	public Plot plotAt(int x, int y) {
		return (Plot) grid.get(x,y);
	}
	
	private void setPlotAt(int x, int y, Object plot) {
		grid.set(x, y, (Plot) plot);
	}
	
	private void initMap() {
		
		//load data and create the valley map
		for (int x = 0;x< WIDTH;x++) {
			for (int y = 0;y<HEIGHT;y++) {
				Plot plot = new Plot();
				plot.ssetWatersource(false);
				double quality = random.nextGaussian() * LongHouseValley.spatialHarvestVariance + 1;
				if (quality >= 0) plot.ssetQuality(quality);
				else plot.ssetQuality(0);
				setPlotAt(x,y,plot);
			}
		}
		try {
			loadDataFiles();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int xx = 0;
		int yy = 0;
		for (int val:mapdata) {
			switch (val) {
			case 0 : {
				plotAt(xx,yy).setColor(Color.black);
				plotAt(xx,yy).setZone(Zone.General);
				plotAt(xx,yy).ssetMaizeZone(MaizeZone.Yield2);
				break;
			}
			case 10 : {
				plotAt(xx,yy).setColor(Color.red);
				plotAt(xx,yy).setZone(Zone.North);
				plotAt(xx,yy).ssetMaizeZone(MaizeZone.Yield1);
				break;
			}
			case 15 : {
				plotAt(xx,yy).setColor(Color.white);
				plotAt(xx,yy).setZone(Zone.NorthDunes);
				plotAt(xx,yy).ssetMaizeZone(MaizeZone.SandDune);
				break;
			}
			case 20 : {
				plotAt(xx,yy).setColor(Color.gray);
				plotAt(xx,yy).setZone(Zone.Mid);
				if (xx <= 74) plotAt(xx,yy).ssetMaizeZone(MaizeZone.Yield1);
				else plotAt(xx,yy).ssetMaizeZone(MaizeZone.Yield2);
				break;
			}
			case 25 : {
				plotAt(xx,yy).setColor(Color.white);
				plotAt(xx,yy).setZone(Zone.MidDunes);
				plotAt(xx,yy).ssetMaizeZone(MaizeZone.SandDune);
				break;
			}
			case 30 : {
				plotAt(xx,yy).setColor(Color.yellow);
				plotAt(xx,yy).setZone(Zone.Natural);
				plotAt(xx,yy).ssetMaizeZone(MaizeZone.NoYield);
				break;
			}
			case 40 : {
				plotAt(xx,yy).setColor(Color.blue);
				plotAt(xx,yy).setZone(Zone.Upland);
				plotAt(xx,yy).ssetMaizeZone(MaizeZone.Yield3);
				break;
			}
			case 50 : {
				plotAt(xx,yy).setColor(Color.pink);
				plotAt(xx,yy).setZone(Zone.Kinbiko);
				plotAt(xx,yy).ssetMaizeZone(MaizeZone.Yield1);
				break;
			}
			case 60 : {
				plotAt(xx,yy).setColor(Color.white);
				plotAt(xx,yy).setZone(Zone.Empty);
				plotAt(xx,yy).ssetMaizeZone(MaizeZone.Empty);
				break;
			}
			}
			if (yy <119) yy++;
			else {xx++; yy = 0;}
		}

		//create the waterpoints
		waterpoints = new Vector<Waterpoint>();
		int i = 0;
		while (i+6 < waterdata.size()) {
			int sarg = waterdata.get(i); i++;
			int meterNorth = waterdata.get(i); i++;
			int meterEast = waterdata.get(i); i++;
			int typeWater = waterdata.get(i); i++;
			int startDate = waterdata.get(i); i++;
			int endDate = waterdata.get(i); i++;
			int x = (int) (25 + ((meterEast - 2392) / 93.5)); //this is a translation from the input data in meters into location on the map.
			int y = (int) Math.floor(45 + (37.6 + ((meterNorth - 7954) / 93.5)));
			//there are 2 waterpoints which seem to be wrong, because they both have meterNorth = 0 and meterEast = 0. ignore them
			if (x>0 && y>0) {
			waterpoints.add(new Waterpoint(x,119-y,sarg,meterNorth,meterEast,typeWater,startDate,endDate));
			}
		}
		
		//create the historical settlements
		historicalSettlements = new Vector<HistoricalSettlement>();
		int k = 0;
		while (k+12 < historicaldata.size()) {
			double SARG = historicaldata.get(k);k++;
			double meterNorth = historicaldata.get(k);k++;
			double meterEast = historicaldata.get(k);k++;
			double startdate = historicaldata.get(k);k++;
			double enddate = historicaldata.get(k);k++;
			double mediandate = 1950 - historicaldata.get(k);k++;
			double typeset = historicaldata.get(k);k++;
			double sizeset = historicaldata.get(k);k++;
			double description = historicaldata.get(k);k++;
			double roomcount = historicaldata.get(k);k++;
			double elevation = historicaldata.get(k);k++;
			double baselinehouseholds = historicaldata.get(k);k++;
			
			int x = (int) (25 + ((meterEast - 2392) / 93.5)); //this is a translation from the input data in meters into location on the map.
			int y = (int) Math.floor(45 + (37.6 + ((meterNorth - 7954) / 93.5)));
			Int2D location = new Int2D(x,119-y);
			int nrhouseholds = 0;
			boolean visible = false;
			historicalSettlements.add(new HistoricalSettlement(location,
					SARG, meterNorth, meterEast, startdate, enddate,
					mediandate, typeset, sizeset, description, roomcount,
					elevation, baselinehouseholds, nrhouseholds, visible));
		}
		//the next is done in order to have the settlements shown in the GUI before the simulation starts
		int initialYear = 800;
		updateHistoricalData(initialYear);
	}

	private void loadDataFiles() throws IOException {
		Scanner s = null;
		InputStream is1 = getClass().getResourceAsStream("Map.txt");
		InputStream is2 = getClass().getResourceAsStream("adjustedPDSI.txt");
		InputStream is3 = getClass().getResourceAsStream("water.txt");
		InputStream is4 = getClass().getResourceAsStream("environment.txt");
		InputStream is5 = getClass().getResourceAsStream("settlements.txt");
		InputStreamReader isr1 = new InputStreamReader(is1);
		InputStreamReader isr2 = new InputStreamReader(is2);
		InputStreamReader isr3 = new InputStreamReader(is3);
		InputStreamReader isr4 = new InputStreamReader(is4);
		InputStreamReader isr5 = new InputStreamReader(is5);

        try {
            s = new Scanner(new BufferedReader(isr1));
            mapdata = new Vector<Integer>();
            
            while (s.hasNext()) {
                mapdata.add(s.nextInt());
            }
        } finally {
            if (s != null) {
                s.close();
            }
        }
        
        try {
        	s = new Scanner(new BufferedReader(isr2));
        	apdsidata = new Vector<Double>();
        	while (s.hasNext()) {
        		apdsidata.add(Double.parseDouble(s.next()));
        	}
        } finally {
            if (s != null) {
                s.close();
            }
        }
        
        try {
        	s = new Scanner(new BufferedReader(isr3));
        	waterdata = new Vector<Integer>();
        	while (s.hasNext()) {
        		waterdata.add((s.nextInt()));
        	}
        } finally {
            if (s != null) {
                s.close();
            }
        }
        
        try {
        	s = new Scanner(new BufferedReader(isr4));
        	environmentdata = new Vector<Double>();
        	while (s.hasNext()) {
        		environmentdata.add(Double.parseDouble(s.next()));
        	}
        } finally {
            if (s != null) {
                s.close();
            }
        }
        
        try {
        	s = new Scanner(new BufferedReader(isr5));
        	historicaldata = new Vector<Double>();
        	while (s.hasNext()) {
        		historicaldata.add(Double.parseDouble(s.next()));
        	}
        } finally {
            if (s != null) {
                s.close();
            }
        }
	}
	
	public void calculateYield(int year) {
		double generalapdsi = apdsidata.get(year - 200);
		double northapdsi = apdsidata.get(1100 + year);
		double midapdsi = apdsidata.get(2400 + year);
		double naturalapdsi = apdsidata.get(3700 + year);
		double uplandapdsi = apdsidata.get(3700 + year);
		double kinbikoapdsi = apdsidata.get(1100 + year);
		     
		double generalhydro = environmentdata.get(((year - 382)*15)+1);
		double northhydro = environmentdata.get(((year - 382)*15)+4);
		double midhydro = environmentdata.get(((year - 382)*15)+7);
		double naturalhydro = environmentdata.get(((year - 382)*15)+10);
		double uplandhydro = environmentdata.get(((year - 382)*15)+10);
		double kinbikohydro = environmentdata.get(((year - 382)*15)+13);
		      
		for (int x = 0;x< WIDTH;x++) {
			for (int y = 0;y<HEIGHT;y++) {
				switch (plotAt(x,y).getZone()) {
				case General : {
					plotAt(x,y).ssetApdsi(generalapdsi);
					plotAt(x,y).ssetHydro(generalhydro);
					break;
				}
				case North : {
					plotAt(x,y).ssetApdsi(northapdsi);
					plotAt(x,y).ssetHydro(northhydro);
					break;
				}
				case Mid : {
					plotAt(x,y).ssetApdsi(midapdsi);
					plotAt(x,y).ssetHydro(midhydro);
					break;
				}
				case Natural : {
					plotAt(x,y).ssetApdsi(naturalapdsi);
					plotAt(x,y).ssetHydro(naturalhydro);
					break;
				}
				case Upland : {
					plotAt(x,y).ssetApdsi(uplandapdsi);
					plotAt(x,y).ssetHydro(uplandhydro);
					break;
				}
				case Kinbiko : {
					plotAt(x,y).ssetApdsi(kinbikoapdsi);
					plotAt(x,y).ssetHydro(kinbikohydro);
					break;
				}
				}
				
				switch (plotAt(x,y).getMaizeZone()) {
				case NoYield : {
					plotAt(x,y).ssetYield(0);
					break;
				}
				case Empty : {
					plotAt(x,y).ssetYield(0);
					break;
				}
				case Yield1 : {
					if (plotAt(x,y).getApdsi() >= 3.0) plotAt(x,y).ssetYield(1153);
					else if ((plotAt(x,y).getApdsi() >= 1.0) && (plotAt(x,y).getApdsi() < 3.0)) plotAt(x,y).ssetYield(988);
					else if ((plotAt(x,y).getApdsi() > -1.0) && (plotAt(x,y).getApdsi() < 1.0)) plotAt(x,y).ssetYield(821);
					else if ((plotAt(x,y).getApdsi() > -3.0) && (plotAt(x,y).getApdsi() <= -1.0)) plotAt(x,y).ssetYield(719);
					else if (plotAt(x,y).getApdsi() <= -3.0) plotAt(x,y).ssetYield(617);
					break;
					}	
				case Yield2 : {
					if (plotAt(x,y).getApdsi() >= 3.0) plotAt(x,y).ssetYield(961);
					else if ((plotAt(x,y).getApdsi() >= 1.0) && (plotAt(x,y).getApdsi() < 3.0)) plotAt(x,y).ssetYield(824);
					else if ((plotAt(x,y).getApdsi() > -1.0) && (plotAt(x,y).getApdsi() < 1.0)) plotAt(x,y).ssetYield(684);
					else if ((plotAt(x,y).getApdsi() > -3.0) && (plotAt(x,y).getApdsi() <= -1.0)) plotAt(x,y).ssetYield(599);
					else if (plotAt(x,y).getApdsi() <= -3.0) plotAt(x,y).ssetYield(514);
					break;
				}
				case Yield3 : {
					if (plotAt(x,y).getApdsi() >= 3.0) plotAt(x,y).ssetYield(769);
					else if ((plotAt(x,y).getApdsi() >= 1.0) && (plotAt(x,y).getApdsi() < 3.0)) plotAt(x,y).ssetYield(659);
					else if ((plotAt(x,y).getApdsi() > -1.0) && (plotAt(x,y).getApdsi() < 1.0)) plotAt(x,y).ssetYield(547);
					else if ((plotAt(x,y).getApdsi() > -3.0) && (plotAt(x,y).getApdsi() <= -1.0)) plotAt(x,y).ssetYield(479);
					else if (plotAt(x,y).getApdsi() <= -3.0) plotAt(x,y).ssetYield(411);
					break;
				}
				case SandDune : {
					if (plotAt(x,y).getApdsi() >= 3.0) plotAt(x,y).ssetYield(1201);
					else if ((plotAt(x,y).getApdsi() >= 1.0) && (plotAt(x,y).getApdsi() < 3.0)) plotAt(x,y).ssetYield(1030);
					else if ((plotAt(x,y).getApdsi() > -1.0) && (plotAt(x,y).getApdsi() < 1.0)) plotAt(x,y).ssetYield(855);
					else if ((plotAt(x,y).getApdsi() > -3.0) && (plotAt(x,y).getApdsi() <= -1.0)) plotAt(x,y).ssetYield(749);
					else if (plotAt(x,y).getApdsi() <= -3.0) plotAt(x,y).ssetYield(642);
					break;
				}
				}
			}
		}  
	}
	
	public void water(int year) {
		streamsexist = ((year >= 280 && year < 360) || (year >= 800 && year < 930) || (year >= 1300 && year < 1450));
		alluviumexist = (((year >= 420) && (year < 560)) || ((year >= 630) && (year < 680)) || ((year >= 980) && (year < 1120)) || ((year >= 1180) && (year < 1230)));

		for (int x = 0;x< WIDTH;x++) {
			for (int y = 0;y<HEIGHT;y++) {			
				boolean ws = ((alluviumexist && (plotAt(x,y).getZone() == Zone.General || plotAt(x,y).getZone() == Zone.North || plotAt(x,y).getZone() == Zone.Mid ||plotAt(x,y).getZone() == Zone.Kinbiko))
				|| (streamsexist && plotAt(x,y).getZone() == Zone.Kinbiko));
					plotAt(x,y).ssetWatersource(ws);
			}
		}
		
		plotAt(72,119-114).ssetWatersource(true);
		plotAt(70,119-113).ssetWatersource(true);
		plotAt(69,119-112).ssetWatersource(true);
		plotAt(68,119-111).ssetWatersource(true);
		plotAt(67,119-110).ssetWatersource(true);
		plotAt(66,119-109).ssetWatersource(true);
		plotAt(65,119-108).ssetWatersource(true);
		plotAt(65,119-107).ssetWatersource(true);

		for (Waterpoint wp : waterpoints) {
			boolean ws = (wp.typeWater == 2) || (wp.typeWater == 3 && (year >= wp.startDate && year <= wp.endDate));
				plotAt(wp.x,wp.y).ssetWatersource(ws);
		}
	}
	
	
	public static double distance(Int2D orig, Int2D dest) {
		return Math.sqrt(Math.abs(orig.x - dest.x) + Math.abs(orig.y - dest.y));
	}
	
	public Vector<Int2D> determinePotFarms(int householdMinNutritionNeed) {
		//determine the list of potential locations for a farm to move to. A potential location to farm is a place where nobody is farming and where the baseyield is higher than the minimum amount of food needed and where nobody has build a settlement

		Vector<Int2D> potfarms = new Vector<Int2D>();
		for (int x = 0; x < WIDTH; x++) {
			for (int y = 0; y < HEIGHT; y++) {
				if ((plotAt(x,y).zone != Zone.Empty)
						&& !plotAt(x,y).isOcfarm()
						&& (plotAt(x,y).getOchousehold() == 0)
						&& (plotAt(x,y).getBaseYield() >= householdMinNutritionNeed)) {
					potfarms.add(new Int2D(x,y));
				}
			}
		}
		return potfarms;
	}
	
	public int ocFarms() {
		int ocf = 0;
		for (int x = 0;x< WIDTH;x++) {
			for (int y = 0;y<HEIGHT;y++) {
				if (plotAt(x,y).isOcfarm()) {
					ocf++;
				}
			}
		}
		return ocf;
	}
	
	public Vector<Int2D> potentialSettlements(double by) {
		Vector<Int2D> settlementPlots = new Vector<Int2D>();
		for (int x = 0;x< WIDTH;x++) {
			for (int y = 0;y<HEIGHT;y++) {
				if (!plotAt(x,y).isOcfarm() && plotAt(x,y).isWatersource() && (plotAt(x,y).getYield() < by)) {
					settlementPlots.add(new Int2D(x,y));
				}
			}
		}
		return settlementPlots;
	}
	
	public Vector<Int2D> potentialSettlementsRelaxed() {
		Vector<Int2D> settlementPlots = new Vector<Int2D>();
		for (int x = 0;x< WIDTH;x++) {
			for (int y = 0;y<HEIGHT;y++) {
				if (!plotAt(x,y).isOcfarm() && (plotAt(x,y).getHydro() <= 0)) {
					settlementPlots.add(new Int2D(x,y));
				}
			}
		}
		return settlementPlots;
	}
	
	public Vector<Int2D> potentialSettlementsReRelaxed() {
		Vector<Int2D> settlementPlots = new Vector<Int2D>();
		for (int x = 0;x< WIDTH;x++) {
			for (int y = 0;y<HEIGHT;y++) {
				if (!plotAt(x,y).isOcfarm()) {
					settlementPlots.add(new Int2D(x,y));
				}
			}
		}
		return settlementPlots;
	}
	
	@Override
	public int getHeight() {
		return HEIGHT;
	}
	
	@Override
	public int getWidth() {
		return WIDTH;
	}
	
	public ObjectGrid2D getGrid() {
		return grid;
	}
}
