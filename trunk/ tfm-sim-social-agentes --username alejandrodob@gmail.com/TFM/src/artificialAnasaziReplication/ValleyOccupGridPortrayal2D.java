package artificialAnasaziReplication;

import java.awt.Color;

import sim.portrayal.grid.FastObjectGridPortrayal2D;
import sim.util.gui.SimpleColorMap;
import artificialAnasaziReplication.ValleyFloor.Plot;

public class ValleyOccupGridPortrayal2D extends FastObjectGridPortrayal2D {
	static Color[] colorTable = {Color.yellow, Color.red, Color.black};
	
	public ValleyOccupGridPortrayal2D() {
		super();
		setMap(new SimpleColorMap(colorTable));
	}

	public ValleyOccupGridPortrayal2D(boolean immutableField) {
		super(immutableField);
		setMap(new SimpleColorMap(colorTable));
	}

	@Override
	public double doubleValue(Object obj) {
		if (obj instanceof Plot) {
			Plot plot = (Plot) obj;
			if (plot.isOcfarm()) return 0;
			else if (plot.getOchousehold()>0) return 1;
			else return 2;
		}
		return super.doubleValue(obj);
	}

}
