package model.field;

import java.util.Iterator;

import sim.field.SparseField2D;
import sim.field.grid.Grid2D;
import sim.util.Bag;
import sim.util.Double2D;
import sim.util.Int2D;
import sim.util.IntBag;

public abstract class AbstractField2D {
	
	protected Grid2D grid;

	public int getWidth() {
		return grid.getWidth();
	}

	public int getHeight() {
		return grid.getHeight();
	}

	public abstract int numObjectsAtLocation(int x, int y);

	public abstract int getObjectIndex(Object obj);

	public abstract Bag getObjectsAtLocation(int x, int y);

	public abstract int size();
	
	public abstract Double2D getObjectLocationAsDouble2D(Object obj);

	public abstract Bag getObjectsAtLocation(Object location);

	public abstract Int2D getObjectLocation(Object obj);

	public abstract Bag removeObjectsAtLocation(int x, int y);

	public abstract boolean setObjectLocation(Object obj, int x, int y);

	public abstract boolean setObjectLocation(Object obj, Int2D location);

	public abstract Bag getObjectsAtLocationOfObject(Object obj);

	public abstract int numObjectsAtLocationOfObject(Object obj);

	public abstract Bag removeObjectsAtLocation(Object location);

	public abstract Bag clear();

	public abstract Object remove(Object obj);

	public abstract Bag getObjectsAtLocations(Bag locations, Bag result);

	public abstract Iterator iterator();

	public abstract Iterator locationBagIterator();

	public abstract Bag getNeighborsMaxDistance(int x, int y, int dist,
			boolean toroidal, Bag result, IntBag xPos, IntBag yPos);
	
	public abstract Bag getNeighborsAndCorrespondingPositionsMaxDistance(int x, int y,
			int dist, boolean toroidal, Bag result, IntBag xPos, IntBag yPos);

	public abstract Bag getNeighborsHamiltonianDistance(int x, int y, int dist,
			boolean toroidal, Bag result, IntBag xPos, IntBag yPos);

	public abstract Bag getNeighborsAndCorrespondingPositionsHamiltonianDistance(int x,
			int y, int dist, boolean toroidal, Bag result, IntBag xPos,
			IntBag yPos);

	public abstract Bag getNeighborsHexagonalDistance(int x, int y, int dist,
			boolean toroidal, Bag result, IntBag xPos, IntBag yPos);

	public abstract Bag getNeighborsAndCorrespondingPositionsHexagonalDistance(int x,
			int y, int dist, boolean toroidal, Bag result, IntBag xPos,
			IntBag yPos);

	public abstract Bag getObjectsAtLocations(IntBag xPos, IntBag yPos, Bag result);

	public abstract Grid2D getGrid(); //to return something drawable by fieldPortrayals


}
