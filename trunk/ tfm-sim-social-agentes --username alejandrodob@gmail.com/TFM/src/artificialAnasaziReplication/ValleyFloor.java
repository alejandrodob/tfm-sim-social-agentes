package artificialAnasaziReplication;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

import field.MutableField2D;

public class ValleyFloor extends MutableField2D {
	
	private static final int WIDTH = 80;
	private static final int HEIGHT = 120;
	public Plot[][] floor = new Plot[WIDTH][HEIGHT];
	private Vector<Waterpoint> waterpoints;
	private Vector<Integer> mapdata;
	private StringBuffer waterdata;
	private StringBuffer apdsidata;
	private StringBuffer environmentdata;
	//el settlements aun no se lo meto
	
	public ValleyFloor() {
		super(WIDTH,HEIGHT);
	}
	
	private void initPlots() {
		
		//load data and create the valley map
		for (int x = 0;x< WIDTH;x++) {
			for (int y = 0;y<HEIGHT;y++) {
				floor[x][y] = new Plot();
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
				floor[xx][yy].setZone("General");
				floor[xx][yy].setMaizeZone("Yield_2");
			}
			case 10 : {
				floor[xx][yy].setColor(Color.red);
				floor[xx][yy].setZone("North");
				floor[xx][yy].setMaizeZone("Yield_1");
			}
			case 15 : {
				floor[xx][yy].setColor(Color.white);
				floor[xx][yy].setZone("North Dunes");
				floor[xx][yy].setMaizeZone("Sand_dune");
			}
			case 20 : {
				floor[xx][yy].setColor(Color.gray);
				floor[xx][yy].setZone("Mid");
				if (xx <= 74) floor[xx][yy].setMaizeZone("Yield_1");
				else floor[xx][yy].setMaizeZone("Yield_2");
			}
			case 25 : {
				floor[xx][yy].setColor(Color.white);
				floor[xx][yy].setZone("Mid Dunes");
				floor[xx][yy].setMaizeZone("Sand_dune");
			}
			case 30 : {
				floor[xx][yy].setColor(Color.yellow);
				floor[xx][yy].setZone("Natural");
				floor[xx][yy].setMaizeZone("No_Yield");
			}
			case 40 : {
				floor[xx][yy].setColor(Color.blue);
				floor[xx][yy].setZone("Uplands");
				floor[xx][yy].setMaizeZone("Yield_3");
			}
			case 50 : {
				floor[xx][yy].setColor(Color.pink);
				floor[xx][yy].setZone("Kinbiko");
				floor[xx][yy].setMaizeZone("Yield_1");
			}
			case 60 : {
				floor[xx][yy].setColor(Color.white);
				floor[xx][yy].setZone("Empty");
				floor[xx][yy].setMaizeZone("Empty");
			}
			}
			if (yy > 0) yy--;
			else {xx++; yy--;}
			
			//create waterpoints
			
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
        	apdsidata = new StringBuffer();
        	while (s.hasNext()) {
        		apdsidata.append(s.next());
        		if (s.hasNext()) {
        			apdsidata.append(" ");
                }
        	}
        } finally {
            if (s != null) {
                s.close();
            }
        }
        
        try {
        	s = new Scanner(new BufferedReader(new FileReader("/home/alejandro/workspace-mason/TFM/src/artificialAnasaziReplication/mapfiles/water.txt")));
        	waterdata = new StringBuffer();
        	int cont = 1;
        	while (s.hasNext()) {
        		waterdata.append(s.next());
        		if (cont%6 == 0 && s.hasNext()) {
        			waterdata.append("/");
        		}
        		else if (s.hasNext()) {
                	waterdata.append(" ");
                }
        		cont++;
        	}
        } finally {
            if (s != null) {
                s.close();
            }
        }
        
        try {
        	s = new Scanner(new BufferedReader(new FileReader("/home/alejandro/workspace-mason/TFM/src/artificialAnasaziReplication/mapfiles/environment.txt")));
        	environmentdata = new StringBuffer();
        	int cont = 1;
        	while (s.hasNext()) {
        		environmentdata.append(s.next());
        		if (cont%15 == 0 && s.hasNext()) {
        			environmentdata.append("/");
        		}
        		else if (s.hasNext()) {
        			environmentdata.append(" ");
                }
        		cont++;
        	}
        } finally {
            if (s != null) {
                s.close();
            }
        }
	}
	
	//the next inner class represents a plot (100m x 100m size) in the valley, the equivalent to 
	//a patch in NetLogo
	class Plot {
		Color color;
		boolean watersource;
		String zone;
		float apdsi;
		int hydro;
		float quality;
		String maizeZone;
		int yield;
		int BaseYield;
		boolean ocfarm;
		int ochousehold;
		int nrh;
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
		public String getZone() {
			return zone;
		}
		public void setZone(String zone) {
			this.zone = zone;
		}
		public float getApdsi() {
			return apdsi;
		}
		public void setApdsi(float apdsi) {
			this.apdsi = apdsi;
		}
		public int getHydro() {
			return hydro;
		}
		public void setHydro(int hydro) {
			this.hydro = hydro;
		}
		public float getQuality() {
			return quality;
		}
		public void setQuality(float quality) {
			this.quality = quality;
		}
		public String getMaizeZone() {
			return maizeZone;
		}
		public void setMaizeZone(String maizeZone) {
			this.maizeZone = maizeZone;
		}
		public int getYield() {
			return yield;
		}
		public void setYield(int yield) {
			this.yield = yield;
		}
		public int getBaseYield() {
			return BaseYield;
		}
		public void setBaseYield(int baseYield) {
			BaseYield = baseYield;
		}
		public boolean isOcfarm() {
			return ocfarm;
		}
		public void setOcfarm(boolean ocfarm) {
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
}
