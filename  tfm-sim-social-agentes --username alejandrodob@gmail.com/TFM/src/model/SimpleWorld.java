package model;

import field.AbstractField2D;
import sim.engine.SimState;
import sim.field.grid.SparseGrid2D;

public class SimpleWorld extends SimState {

	protected int height = 40; // estos valores, ver si se pueden definir
								  // mediante un constructor mejor
	protected int width = 40; // ojo que estaba el problema del constructor
								 // sin argumentos que usa el metodo doLoop
								 // de SimState, asi que igual hay que hacer
								 // el bucle a mano
	protected AbstractField2D field;
	
	public SimpleWorld(long seed) {
		super(seed);
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public AbstractField2D getField() {
		return field;
	}

	public void setField(AbstractField2D field) {
		this.field = field;
	}

}
