package artificialAnasaziReplication;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.net.MalformedURLException;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import sim.display.Console;
import sim.display.Controller;
import sim.display.Display2D;
import sim.display.GUIState;
import sim.engine.SimState;
import sim.engine.Steppable;
import sim.portrayal.DrawInfo2D;
import sim.portrayal.Inspector;
import sim.portrayal.SimpleInspector;
import sim.portrayal.grid.FastObjectGridPortrayal2D;
import sim.portrayal.grid.SparseGridPortrayal2D;
import sim.portrayal.simple.CircledPortrayal2D;
import sim.portrayal.simple.ImagePortrayal2D;
import sim.portrayal.simple.LabelledPortrayal2D;
import sim.portrayal.simple.MovablePortrayal2D;
import sim.portrayal.simple.OvalPortrayal2D;

public class LongHouseValleyWithUI extends GUIState {

	//display for the simulated data
	public Display2D simValleyDisplay;
	public JFrame simValleyFrame;
	
	//display for the historical data
	public Display2D hisValleyDisplay;
	public JFrame hisValleyFrame;
	
	//portrayals
	public ValleyLandGridPortrayal2D landCoverPortrayal = new ValleyLandGridPortrayal2D(false);
	public ValleyWaterGridPortrayal2D waterSourcesPortrayal = new ValleyWaterGridPortrayal2D(false);
	public ValleyOccupGridPortrayal2D occupPortrayal = new ValleyOccupGridPortrayal2D(false);
	public ValleyYieldGridPortrayal2D yieldPortrayal = new ValleyYieldGridPortrayal2D(false);
	public SparseGridPortrayal2D simHouseholdsPortrayal = new SparseGridPortrayal2D();
	public SparseGridPortrayal2D hisHouseholdsPortrayal = new SparseGridPortrayal2D();
	
	//variables for charting population
	org.jfree.data.xy.XYSeries seriesSim;
	org.jfree.data.xy.XYSeries seriesDat;
    sim.util.media.chart.TimeSeriesChartGenerator chart;

/*	//inspector
	public Inspector statisticsInspector;
	public JFrame statInspectorFrame;*/
	
	public static void main(String[] args) {
		LongHouseValleyWithUI vid = new LongHouseValleyWithUI();
		Console c = new Console(vid);
		c.setVisible(true);
	}
	
	public LongHouseValleyWithUI() { super(new LongHouseValley(System.currentTimeMillis())); }
	
	public LongHouseValleyWithUI(SimState state) { super(state); }
	
	public static String getName() { return "Long House Valley Simulation"; }

	public Object getSimulationInspectedObject(){return state;}

	public Inspector getInspector() {
		Inspector i = super.getInspector();
		i.setVolatile(true);
		return i;
	}

	public void start() {
		super.start();
		setupPortrayals();
		
		chart.removeAllSeries();
		seriesSim = new org.jfree.data.xy.XYSeries("Simulated population",false);
		seriesDat = new org.jfree.data.xy.XYSeries("Historical data population",false);
		
		chart.addSeries(seriesSim, null);
		scheduleImmediateRepeat(true, new Steppable() {
			public void step(SimState state) {

				double time = state.schedule.getTime(); 
				double simPop = ((LongHouseValley) state).numHouseholds;
				double dataPop = ((LongHouseValley) state).historicalHouseholds();

				// now add the data
				if (time >= state.schedule.EPOCH && time < state.schedule.AFTER_SIMULATION)
					seriesSim.add(time + 800, simPop, true);
			}
		});
		chart.addSeries(seriesDat, null);
		scheduleImmediateRepeat(true, new Steppable() {
			public void step(SimState state) {

				double time = state.schedule.getTime(); 
				double simPop = ((LongHouseValley) state).numHouseholds;
				double dataPop = ((LongHouseValley) state).historicalHouseholds();

				// now add the data
				if (time >= state.schedule.EPOCH && time < state.schedule.AFTER_SIMULATION)
					seriesDat.add(time + 800, dataPop, true);
			}
		});
	}
	
	public void finish() {
		super.finish();
		state = new LongHouseValley(System.currentTimeMillis());
	}

	public void load(SimState state) {
		super.load(state);
		setupPortrayals();
	}
	
	public void setupPortrayals() {
		LongHouseValley valley = (LongHouseValley) state;
		
		//set the fields for the portrayals
		landCoverPortrayal.setField(((ValleyFloor) valley.getField()).getGrid());
		waterSourcesPortrayal.setField(((ValleyFloor) valley.getField()).getGrid());
		occupPortrayal.setField(((ValleyFloor) valley.getField()).getGrid());
		yieldPortrayal.setField(((ValleyFloor) valley.getField()).getGrid());
		
		simHouseholdsPortrayal.setField(valley.population);
		hisHouseholdsPortrayal.setField(((ValleyFloor) valley.getField()).hisPopulation);
		
		//set the simplePortrayals for agents
		
		//for the simulated households
		simHouseholdsPortrayal.setPortrayalForAll(
				new CircledPortrayal2D(
						new LabelledPortrayal2D(
								new OvalPortrayal2D(){
									public void draw(Object object, Graphics2D graphics, DrawInfo2D info){
										paint = Color.green;
										super.draw(object, graphics, info);
									}
								},
								5.0, null, Color.black, true),
								0, 5.0, Color.orange, true));
		
		//and for the historical settlements
		LabelledPortrayal2D portrayal = new LabelledPortrayal2D(
				new OvalPortrayal2D(){
					public void draw(Object object, Graphics2D graphics, DrawInfo2D info){
						paint = Color.green;
						super.draw(object, graphics, info);
					}
				},
				0.0, null, Color.lightGray, false);
		portrayal.align = LabelledPortrayal2D.ALIGN_CENTER;
		hisHouseholdsPortrayal.setPortrayalForAll(portrayal);
		//reschedule
		simValleyDisplay.reset();
		simValleyDisplay.setBackdrop(Color.white);
		hisValleyDisplay.reset();
		hisValleyDisplay.setBackdrop(Color.white);
		
		//repaint
		simValleyDisplay.repaint();
		hisValleyDisplay.repaint();
		
	}
	
	public void init(Controller c) {
		super.init(c);
		
		//simulated data display
		simValleyDisplay = new Display2D(320,480,this);
		simValleyDisplay.setClipping(false);
		simValleyFrame = simValleyDisplay.createFrame();
		simValleyFrame.setTitle("Simulated");
		c.registerFrame(simValleyFrame); // so that the frame appears in the "Display" list
		simValleyFrame.setVisible(true);
		simValleyDisplay.attach(landCoverPortrayal, "Valley land zones");
		simValleyDisplay.attach(waterSourcesPortrayal, "Water sources", false);
		simValleyDisplay.attach(occupPortrayal, "Occupation (red=settlements,yellow=farms",false);
		simValleyDisplay.attach(yieldPortrayal, "Yield", false);
		simValleyDisplay.attach(simHouseholdsPortrayal, "Households");//attach the last one because every one covers the previous ones
		
		//historical data display
		hisValleyDisplay = new Display2D(320,480,this);
		hisValleyDisplay.setClipping(false);
		hisValleyFrame = hisValleyDisplay.createFrame();
		hisValleyFrame.setTitle("Historical");
		c.registerFrame(hisValleyFrame); // so that the frame appears in the "Display" list
		hisValleyFrame.setVisible(true);
		hisValleyDisplay.attach(landCoverPortrayal, "Valley land zones");
		hisValleyDisplay.attach(hisHouseholdsPortrayal, "Historical settlements");
		
		for (Object fr : c.getAllFrames()) {
			JFrame frame = (JFrame) fr;
			if (frame.equals(hisValleyFrame)) {
				Point loc = frame.getLocationOnScreen();
				loc.setLocation(loc.x+350,loc.y);
				frame.setLocation(loc);
			}
		}
		
		//create the charts for statistics
		chart = new sim.util.media.chart.TimeSeriesChartGenerator();
		chart.setTitle("Evolution of the population");
		chart.setRangeAxisLabel("Households");
		chart.setDomainAxisLabel("Year");
		JFrame frame = chart.createFrame(this);
		// perhaps you might move the chart to where you like.
		frame.show();
		frame.pack();
		c.registerFrame(frame);
/*		
		//create an inspector for the statistics
		statisticsInspector = new SimpleInspector(((LongHouseValley) state).statistics, this, "Population evolution") {
			@Override
			public void updateInspector() {
				System.out.println("me estoy actualizando, que conste en actasDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD");
				SwingUtilities.invokeLater(new Runnable() { public void run() { repaint(); }});
			}
		};
		c.registerInspector(statisticsInspector,null);
		statisticsInspector.setVolatile(true);
		statisticsInspector.setVisible(true);
		statInspectorFrame = statisticsInspector.createFrame(null);
		statInspectorFrame.setVisible(true);
		statInspectorFrame.setTitle("Statistics");*/
	}

	public void quit() {
		super.quit();

		if (simValleyFrame != null) simValleyFrame.dispose();
		simValleyFrame = null;
		simValleyDisplay = null;
		
		if (hisValleyFrame != null) hisValleyFrame.dispose();
		hisValleyFrame = null;
		hisValleyDisplay = null;
	}
}
