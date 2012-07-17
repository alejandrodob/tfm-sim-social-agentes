package artificialAnasaziReplication;

import java.awt.Color;

import artificialAnasaziReplication.ValleyFloor.Plot;
import sim.portrayal.grid.FastObjectGridPortrayal2D;
import sim.util.gui.ColorMap;
import sim.util.gui.SimpleColorMap;

public class ValleyLandGridPortrayal2D extends FastObjectGridPortrayal2D {

	static Color[] colorTable = {Color.black, Color.blue, Color.gray, Color.pink, Color.red, Color.white, Color.yellow};
	
	public ValleyLandGridPortrayal2D() {
		super();
		setMap(new SimpleColorMap(colorTable));
	}

	public ValleyLandGridPortrayal2D(boolean immutableField) {
		super(immutableField);
		setMap(new SimpleColorMap(colorTable));
	}

	@Override
	public double doubleValue(Object obj) {
		if (obj instanceof Plot) {
			Plot plot = (Plot) obj;
			Color color = plot.getValue();
			if (color == Color.black) return 0;
			else if (color == Color.blue) return 1;
			else if (color == Color.gray) return 2;
			else if (color == Color.pink) return 3;
			else if (color == Color.red) return 4;
			else if (color == Color.white) return 5;
			else if (color == Color.yellow) return 6;
		}
		return super.doubleValue(obj);
	}
	
}
