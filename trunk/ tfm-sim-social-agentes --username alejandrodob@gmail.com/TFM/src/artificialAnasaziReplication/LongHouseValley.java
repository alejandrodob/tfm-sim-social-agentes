package artificialAnasaziReplication;

import model.SimpleWorld;

public class LongHouseValley extends SimpleWorld {

	public LongHouseValley(long seed) {
		super(seed);
		field = new ValleyFloor();
	}

}
