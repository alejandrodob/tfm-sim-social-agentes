package artificialAnasaziReplication;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

import sim.engine.SimState;
import sim.util.Int2D;

import ec.util.MersenneTwisterFast;
import field.MutableField2D;

public class ValleyFloor extends MutableField2D {
	
	private static final int WIDTH = 80;
	private static final int HEIGHT = 120;
	private static MersenneTwisterFast random = new MersenneTwisterFast();
	private Plot[][] floor = new Plot[WIDTH][HEIGHT];
	private boolean streamsexist;
	private boolean alluviumexist;
	private Vector<Waterpoint> waterpoints;
	private Vector<Integer> mapdata;
	private Vector<Integer> waterdata;
	private Vector<Double> apdsidata;
	private Vector<Double> environmentdata;
	//el settlements aun no se lo meto
	
	private enum Zone {
		General, North, NorthDunes, Mid, MidDunes, Natural, Upland, Kinbiko, Empty
	}
	
	private enum MaizeZone {
		Yield1, Yield2, Yield3, SandDune, NoYield, Empty
	}
	
	public ValleyFloor() {
		super(WIDTH,HEIGHT);
		initMap();
	}

	@Override
	public void step(SimState state) {
		calculateYield(((LongHouseValley) state).getYear());
		calculateBaseYield(((LongHouseValley) state).harvestAdjustment,((LongHouseValley) state).getYear());
		water(((LongHouseValley) state).getYear());
	}
	
	public void calculateBaseYield(double harvestAdjustment,int year) {
		for (int x = 0;x< WIDTH;x++) {
			for (int y = 0;y<HEIGHT;y++) {
				floor[x][y].calculateBaseYield(harvestAdjustment,year);
			}
		} 
	}
	
	private void initMap() {
		
		//load data and create the valley map
		for (int x = 0;x< WIDTH;x++) {
			for (int y = 0;y<HEIGHT;y++) {
				floor[x][y] = new Plot();
				floor[x][y].setWatersource(false);
				double quality = random.nextGaussian() * 0.4 + 1;//0.4 is the initial harvestVariance
				if (quality >= 0) floor[x][y].setQuality(quality);
				else floor[x][y].setQuality(0);
			}
		}
		try {
			loadDataFiles();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int xx = 0;
		int yy = 119;
		for (int val:mapdata) {
			switch (val) {
			case 0 : {
				floor[xx][yy].setColor(Color.black);
				floor[xx][yy].setZone(Zone.General);
				floor[xx][yy].setMaizeZone(MaizeZone.Yield2);
				break;
			}
			case 10 : {
				floor[xx][yy].setColor(Color.red);
				floor[xx][yy].setZone(Zone.North);
				floor[xx][yy].setMaizeZone(MaizeZone.Yield1);
				break;
			}
			case 15 : {
				floor[xx][yy].setColor(Color.white);
				floor[xx][yy].setZone(Zone.NorthDunes);
				floor[xx][yy].setMaizeZone(MaizeZone.SandDune);
				break;
			}
			case 20 : {
				floor[xx][yy].setColor(Color.gray);
				floor[xx][yy].setZone(Zone.Mid);
				if (xx <= 74) floor[xx][yy].setMaizeZone(MaizeZone.Yield1);
				else floor[xx][yy].setMaizeZone(MaizeZone.Yield2);
				break;
			}
			case 25 : {
				floor[xx][yy].setColor(Color.white);
				floor[xx][yy].setZone(Zone.MidDunes);
				floor[xx][yy].setMaizeZone(MaizeZone.SandDune);
				break;
			}
			case 30 : {
				floor[xx][yy].setColor(Color.yellow);
				floor[xx][yy].setZone(Zone.Natural);
				floor[xx][yy].setMaizeZone(MaizeZone.NoYield);
				break;
			}
			case 40 : {
				floor[xx][yy].setColor(Color.blue);
				floor[xx][yy].setZone(Zone.Upland);
				floor[xx][yy].setMaizeZone(MaizeZone.Yield3);
				break;
			}
			case 50 : {
				floor[xx][yy].setColor(Color.pink);
				floor[xx][yy].setZone(Zone.Kinbiko);
				floor[xx][yy].setMaizeZone(MaizeZone.Yield1);
				break;
			}
			case 60 : {
				floor[xx][yy].setColor(Color.white);
				floor[xx][yy].setZone(Zone.Empty);
				floor[xx][yy].setMaizeZone(MaizeZone.Empty);
				break;
			}
			}
			if (yy > 0) yy--;
			else {xx++; yy = 119;}
		}

		//create the waterpoints
		waterpoints = new Vector<Waterpoint>();
		int i = 0;
		while (i+6 <= waterdata.size()) {
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
			waterpoints.add(new Waterpoint(x,y,sarg,meterNorth,meterEast,typeWater,startDate,endDate));
			}
		}
	}

	private void loadDataFiles() throws IOException {
		Scanner s = null;

        try {
            s = new Scanner(new BufferedReader(new FileReader("/home/alejandro/workspace-mason/TFM/src/artificialAnasaziReplication/mapfiles/Map.txt")));
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
        	s = new Scanner(new BufferedReader(new FileReader("/home/alejandro/workspace-mason/TFM/src/artificialAnasaziReplication/mapfiles/adjustedPDSI.txt")));
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
        	s = new Scanner(new BufferedReader(new FileReader("/home/alejandro/workspace-mason/TFM/src/artificialAnasaziReplication/mapfiles/water.txt")));
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
        	s = new Scanner(new BufferedReader(new FileReader("/home/alejandro/workspace-mason/TFM/src/artificialAnasaziReplication/mapfiles/environment.txt")));
        	environmentdata = new Vector<Double>();
        	while (s.hasNext()) {
        		environmentdata.add(Double.parseDouble(s.next()));
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
				switch (floor[x][y].getZone()) {
				case General : {
					floor[x][y].setApdsi(generalapdsi);
					floor[x][y].setHydro(generalhydro);
					break;
				}
				case North : {
					floor[x][y].setApdsi(northapdsi);
					floor[x][y].setHydro(northhydro);
					break;
				}
				case Mid : {
					floor[x][y].setApdsi(midapdsi);
					floor[x][y].setHydro(midhydro);
					break;
				}
				case Natural : {
					floor[x][y].setApdsi(naturalapdsi);
					floor[x][y].setHydro(naturalhydro);
					break;
				}
				case Upland : {
					floor[x][y].setApdsi(uplandapdsi);
					floor[x][y].setHydro(uplandhydro);
					break;
				}
				case Kinbiko : {
					floor[x][y].setApdsi(kinbikoapdsi);
					floor[x][y].setHydro(kinbikohydro);
					break;
				}
				}
				switch (floor[x][y].getMaizeZone()) {
				case NoYield : {
					floor[x][y].setYield(0);
					break;
				}
				case Empty : {
					floor[x][y].setYield(0);
					break;
				}
				case Yield2 : {
					if (floor[x][y].getApdsi() >= 3.0) floor[x][y].setYield(961);
					else if ((floor[x][y].getApdsi() >= 1.0 && floor[x][y].getApdsi() < 3.0)) floor[x][y].setYield(824);
					else if ((floor[x][y].getApdsi() > -1.0) && (floor[x][y].getApdsi() < 1.0)) floor[x][y].setYield(684);
					else if ((floor[x][y].getApdsi() > -3.0) && (floor[x][y].getApdsi() <= -1.0)) floor[x][y].setYield(599);
					else if (floor[x][y].getApdsi() <= -3.0) floor[x][y].setYield(514);
					break;
				}
				case Yield3 : {
					if (floor[x][y].getApdsi() >= 3.0) floor[x][y].setYield(769);
					else if ((floor[x][y].getApdsi() >= 1.0 && floor[x][y].getApdsi() < 3.0)) floor[x][y].setYield(659);
					else if ((floor[x][y].getApdsi() > -1.0) && (floor[x][y].getApdsi() < 1.0)) floor[x][y].setYield(547);
					else if ((floor[x][y].getApdsi() > -3.0) && (floor[x][y].getApdsi() <= -1.0)) floor[x][y].setYield(479);
					else if (floor[x][y].getApdsi() <= -3.0) floor[x][y].setYield(411);
					break;
				}
				case SandDune : {
					if (floor[x][y].getApdsi() >= 3.0) floor[x][y].setYield(1201);
					else if ((floor[x][y].getApdsi() >= 1.0 && floor[x][y].getApdsi() < 3.0)) floor[x][y].setYield(1030);
					else if ((floor[x][y].getApdsi() > -1.0) && (floor[x][y].getApdsi() < 1.0)) floor[x][y].setYield(855);
					else if ((floor[x][y].getApdsi() > -3.0) && (floor[x][y].getApdsi() <= -1.0)) floor[x][y].setYield(749);
					else if (floor[x][y].getApdsi() <= -3.0) floor[x][y].setYield(642);
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
				boolean ws = ((alluviumexist && (floor[x][y].getZone() == Zone.General || floor[x][y].getZone() == Zone.North || floor[x][y].getZone() == Zone.Mid ||floor[x][y].getZone() == Zone.Kinbiko))
				|| (streamsexist && floor[x][y].getZone() == Zone.Kinbiko));
					floor[x][y].setWatersource(ws);
			}
		}
		
		floor[72][114].setWatersource(true);
		floor[70][113].setWatersource(true);
		floor[69][112].setWatersource(true);
		floor[68][111].setWatersource(true);
		floor[67][110].setWatersource(true);
		floor[66][109].setWatersource(true);
		floor[65][108].setWatersource(true);
		floor[65][107].setWatersource(true);

		for (Waterpoint wp : waterpoints) {
			boolean ws = (wp.typeWater == 2) || (wp.typeWater == 3 && (year >= wp.startDate && year <= wp.endDate));
				floor[wp.x][wp.y].setWatersource(ws);
		}
	}
	
	//the next inner class represents a plot (100m x 100m size) in the valley, the equivalent to 
	//a patch in NetLogo
	class Plot {
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
		private int nrh;
		public Color getValue() {
			return color;
		}
		public void setColor(Color color) {
			this.color = color;
		}
		public boolean isWatersource() {
			return watersource;
		}
		public void setWatersource(boolean watersource) {
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
		public void setApdsi(double apdsi) {
			this.apdsi = apdsi;
		}
		public double getHydro() {
			return hydro;
		}
		public void setHydro(double generalhydro) {
			this.hydro = generalhydro;
		}
		public double getQuality() {
			return quality;
		}
		public void setQuality(double quality) {
			this.quality = quality;
		}
		public MaizeZone getMaizeZone() {
			return maizeZone;
		}
		public void setMaizeZone(MaizeZone maizeZone) {
			this.maizeZone = maizeZone;
		}
		public double getYield() {
			return yield;
		}
		public void setYield(double yield) {
			this.yield = yield;
		}
		public double getBaseYield() {
			return BaseYield;
		}
		public void setBaseYield(double d) {
			BaseYield = d;
		}
		public boolean isOcfarm() {
			return ocfarm;
		}
		public void setOcfarm(boolean ocfarm) {
			/*if (ocfarm) System.out.println("se pone granja en parcela ");
			else System.out.println("se quita granja de parcela");*/
			this.ocfarm = ocfarm;
		}
		public int getOchousehold() {
			return ochousehold;
		}
		public void setOchousehold(int ochousehold) {
			this.ochousehold = ochousehold;
		}
		public int getNrh() {
			return nrh;
		}
		public void setNrh(int nrh) {
			this.nrh = nrh;
		}
		
		public void calculateBaseYield(double harvestAdjustment,int year) {
			if (year == 800) setBaseYield(getYield()*getQuality());
			else setBaseYield(getYield() * getQuality() * harvestAdjustment);
		}
		
		public void incHousholdNum() {
			setOchousehold(getOchousehold() + 1);
		}
		
		public void decHouseholdNum() {
			setOchousehold(getOchousehold() - 1);
		}
		
		public Plot() {}
	}
	
	class Waterpoint {
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
	
	public static double distance(Int2D orig, Int2D dest) {
		return Math.sqrt(Math.abs(orig.x - dest.x) + Math.abs(orig.y - dest.y));
	}

	public Plot[][] getFloor() {
		return floor;
	}

	public void setFloor(Plot[][] floor) {
		this.floor = floor;
	}
	
	public Vector<Int2D> determinePotFarms(int householdMinNutritionNeed) {
		//determine the list of potential locations for a farm to move to. A potential location to farm is a place where nobody is farming and where the baseyield is higher than the minimum amount of food needed and where nobody has build a settlement

		Vector<Int2D> potfarms = new Vector<Int2D>();
		for (int x = 0;x< WIDTH;x++) {
			for (int y = 0;y<HEIGHT;y++) {
				if ((floor[x][y].zone != Zone.Empty) && !floor[x][y].isOcfarm() && (floor[x][y].getOchousehold() == 0) && (floor[x][y].getBaseYield() >= householdMinNutritionNeed)) {
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
				if (floor[x][y].isOcfarm()) {
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
				if (!floor[x][y].isOcfarm() && floor[x][y].isWatersource() && (floor[x][y].getYield() < by)) {
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
				if (!floor[x][y].isOcfarm() && (floor[x][y].getHydro() <= 0)) {
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
				if (!floor[x][y].isOcfarm()) {
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
}
