package artificialAnasaziReplication;

import java.awt.Color;
import java.awt.Graphics2D;
import java.net.MalformedURLException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import sim.display.Console;
import sim.display.Controller;
import sim.display.Display2D;
import sim.display.GUIState;
import sim.engine.SimState;
import sim.portrayal.DrawInfo2D;
import sim.portrayal.Inspector;
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
	public SparseGridPortrayal2D simHouseholdsPortrayal = new SparseGridPortrayal2D();
	public SparseGridPortrayal2D hisHouseholdsPortrayal = new SparseGridPortrayal2D();
	
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
				0.0, null, Color.black, false);
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
		c.registerFrame(simValleyFrame); // so the frame appears in the "Display" list
		simValleyFrame.setVisible(true);
		simValleyDisplay.attach(landCoverPortrayal, "Valley land zones");
		simValleyDisplay.attach(simHouseholdsPortrayal, "Households");
		
		//historical data display
		hisValleyDisplay = new Display2D(320,480,this);
		hisValleyDisplay.setClipping(false);
		hisValleyFrame = hisValleyDisplay.createFrame();
		hisValleyFrame.setTitle("Historical");
		c.registerFrame(hisValleyFrame); // so the frame appears in the "Display" list
		hisValleyFrame.setVisible(true);
		hisValleyDisplay.attach(landCoverPortrayal, "Valley land zones");
		hisValleyDisplay.attach(hisHouseholdsPortrayal, "Households");
		
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
