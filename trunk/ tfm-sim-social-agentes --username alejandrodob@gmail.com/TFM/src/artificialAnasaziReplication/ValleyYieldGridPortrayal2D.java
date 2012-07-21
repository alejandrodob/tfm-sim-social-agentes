package artificialAnasaziReplication;

import java.awt.Color;

import sim.portrayal.grid.FastObjectGridPortrayal2D;
import sim.util.gui.SimpleColorMap;
import artificialAnasaziReplication.ValleyFloor.Plot;

public class ValleyYieldGridPortrayal2D extends FastObjectGridPortrayal2D {
	
	private final double aproxMaxBaseYield = 1400;
	private final double minBaseYield = 0;
	
	public ValleyYieldGridPortrayal2D() {
		super();
		setMap(new SimpleColorMap(minBaseYield,aproxMaxBaseYield,Color.black,Color.yellow));
	}

	public ValleyYieldGridPortrayal2D(boolean immutableField) {
		super(immutableField);
		setMap(new SimpleColorMap(minBaseYield,aproxMaxBaseYield,Color.black,Color.yellow));
	}

	@Override
	public double doubleValue(Object obj) {
		if (obj instanceof Plot) {
			Plot plot = (Plot) obj;
			return plot.getBaseYield();
			}
		return super.doubleValue(obj);
	}
}
