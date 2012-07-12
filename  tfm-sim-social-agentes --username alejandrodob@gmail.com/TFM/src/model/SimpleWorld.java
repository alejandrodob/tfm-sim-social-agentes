package model;

import environment.Mobility;
import environment.Mortality;
import environment.Natality;
import environment.Nuptiality;
import field.AbstractField2D;
import sim.engine.SimState;
import sim.field.grid.SparseGrid2D;

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

}
