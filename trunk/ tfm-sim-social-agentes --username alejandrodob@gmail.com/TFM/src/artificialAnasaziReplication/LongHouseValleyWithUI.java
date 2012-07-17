package artificialAnasaziReplication;

import java.awt.Color;

import javax.swing.JFrame;

import sim.display.Console;
import sim.display.Controller;
import sim.display.Display2D;
import sim.display.GUIState;
import sim.engine.SimState;
import sim.portrayal.Inspector;
import sim.portrayal.grid.FastObjectGridPortrayal2D;

public class LongHouseValleyWithUI extends GUIState {
	
	public Display2D valleyDisplay;
	public JFrame valleyFrame;
	public ValleyLandGridPortrayal2D landCoverPortrayal = new ValleyLandGridPortrayal2D();
	
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

	public void load(SimState state) {
		super.load(state);
		setupPortrayals();
	}

	public void setupPortrayals() {
		LongHouseValley valley = (LongHouseValley) state;
		
		landCoverPortrayal.setField(((ValleyFloor) valley.getField()).getGrid());
		
		//reschedule
		valleyDisplay.reset();
		valleyDisplay.setBackdrop(Color.white);
		
		//repaint
		valleyDisplay.repaint();
	}
	
	public void init(Controller c) {
		super.init(c);
		//inicializacion de 2D
		valleyDisplay = new Display2D(320,480,this);
		//display.setClipping(false);
		valleyFrame = valleyDisplay.createFrame();
		valleyFrame.setTitle("Simulated");
		c.registerFrame(valleyFrame); // so the frame appears in the "Display" list
		valleyFrame.setVisible(true);
		valleyDisplay.attach( landCoverPortrayal, "ValleyTerrain" );

		
	}

	public void quit() {
		super.quit();

		//2D
		if (valleyFrame != null) valleyFrame.dispose();
		valleyFrame = null;
		valleyDisplay = null;
	}
}
