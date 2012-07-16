package artificialAnasaziReplication;

import sim.display.Console;
import sim.display.GUIState;
import sim.engine.SimState;

public class LongHouseValleyWithUI extends GUIState {
	
	public static void main(String[] args)
	{
		LongHouseValleyWithUI vid = new LongHouseValleyWithUI();
	Console c = new Console(vid);
	c.setVisible(true);
	}
	public LongHouseValleyWithUI() { super(new LongHouseValley(System.currentTimeMillis())); }
	public LongHouseValleyWithUI(SimState state) { super(state); }
	public static String getName() { return "Long House Valley Simulation"; }

}
