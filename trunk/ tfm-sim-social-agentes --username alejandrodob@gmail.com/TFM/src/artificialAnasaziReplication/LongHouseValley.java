package artificialAnasaziReplication;

import sim.util.Int2D;
import agent.DemographicItem;
import model.SimpleWorld;

public class LongHouseValley extends SimpleWorld {
	
	private int year;

	public LongHouseValley(long seed) {
		super(seed);
		field = new ValleyFloor();
	}

	@Override
	public void addIndividual(DemographicItem person, Int2D location) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeIndividual(DemographicItem person) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerDeath(DemographicItem person) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerBirth(DemographicItem newborn, DemographicItem mother) {
		// TODO Auto-generated method stub
		
	}
	
	public int getYear() {
		return year;
	}

}
