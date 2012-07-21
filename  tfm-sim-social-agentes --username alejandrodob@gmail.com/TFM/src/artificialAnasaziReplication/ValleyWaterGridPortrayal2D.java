package artificialAnasaziReplication;

import java.awt.Color;

import sim.portrayal.grid.FastObjectGridPortrayal2D;
import sim.util.gui.SimpleColorMap;
import artificialAnasaziReplication.ValleyFloor.Plot;

public class ValleyWaterGridPortrayal2D extends FastObjectGridPortrayal2D {
	
	static Color[] colorTable = {Color.blue, Color.white,};
	
	public ValleyWaterGridPortrayal2D() {
		super();
		setMap(new SimpleColorMap(colorTable));
	}

	public ValleyWaterGridPortrayal2D(boolean immutableField) {
		super(immutableField);
		setMap(new SimpleColorMap(colorTable));
	}

	@Override
	public double doubleValue(Object obj) {
		if (obj instanceof Plot) {
			Plot plot = (Plot) obj;
			boolean waterSource = plot.isWatersource();
			if (waterSource) return 0;
			else return 1;
		}
		return super.doubleValue(obj);
	}
}
