package model;

import agent.DemographicItem;
import agent.Person;
import environment.Mobility;
import environment.Mortality;
import environment.Natality;
import environment.Nuptiality;
import field.AbstractField2D;
import sim.engine.SimState;
import sim.field.grid.SparseGrid2D;
import sim.util.Int2D;

public abstract class SimpleWorld extends SimState {

	
	protected AbstractField2D field;
	
	public Natality natality;
	public Mortality mortality;
	public Mobility mobility;
	public Nuptiality nuptiality;
	
	
	public SimpleWorld(long seed) {
		super(seed);
	}

	public AbstractField2D getField() {
		return field;
	}

	public void setField(AbstractField2D field) {
		this.field = field;
	}
	

	public abstract void addIndividual(DemographicItem person, Int2D location);

	public abstract void removeIndividual(DemographicItem person);

	public abstract void registerDeath(DemographicItem person);

	public abstract void registerBirth(DemographicItem newborn, DemographicItem mother);
	
	public abstract void registerMigration(DemographicItem person, Int2D from, Int2D to);

}
