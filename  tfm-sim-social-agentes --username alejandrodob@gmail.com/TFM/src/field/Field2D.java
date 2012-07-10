package field;

import java.util.Iterator;

import sim.field.SparseField;
import sim.field.grid.SparseGrid2D;
import sim.util.Bag;
import sim.util.Double2D;
import sim.util.Int2D;
import sim.util.IntBag;

public class Field2D extends AbstractField2D {
	
	public Field2D(int width,int height) {
		field = new SparseGrid2D(width, height);
	}

	@Override
	public int numObjectsAtLocation(int x, int y) {
		return ((SparseGrid2D) field).numObjectsAtLocation(x,y);
	}

	@Override
	public int getObjectIndex(Object obj) {
		return ((SparseField) field).getObjectIndex(obj);
	}

	@Override
	public Bag getObjectsAtLocation(int x, int y) {
		return ((SparseGrid2D) field).getObjectsAtLocation(x, y);
	}

	@Override
	public int size() {
		return ((SparseField) field).size();
	}

	@Override
	public Double2D getObjectLocationAsDouble2D(Object obj) {
		return ((SparseGrid2D) field).getObjectLocationAsDouble2D(obj);
	}

	@Override
	public Bag getObjectsAtLocation(Object location) {
		return ((SparseField) field).getObjectsAtLocation(location);
	}

	@Override
	public Int2D getObjectLocation(Object obj) {
		return ((SparseGrid2D) field).getObjectLocation(obj);
	}

	@Override
	public Bag removeObjectsAtLocation(int x, int y) {
		return ((SparseGrid2D) field).removeObjectsAtLocation(x, y);
	}

	@Override
	public boolean setObjectLocation(Object obj, int x, int y) {
		return ((SparseGrid2D) field).setObjectLocation(obj, x, y);
	}

	@Override
	public boolean setObjectLocation(Object obj, Int2D location) {
		return setObjectLocation(obj,location.x,location.y);
	}

	@Override
	public Bag getObjectsAtLocationOfObject(Object obj) {
		return ((SparseField) field).getObjectsAtLocationOfObject(obj);
	}

	@Override
	public int numObjectsAtLocationOfObject(Object obj) {
		return ((SparseField) field).numObjectsAtLocationOfObject(obj);
	}

	@Override
	public Bag removeObjectsAtLocation(Object location) {
		return ((SparseField) field).removeObjectsAtLocation(location);
	}

	@Override
	public Bag clear() {
		return ((SparseField) field).clear();
	}

	@Override
	public Object remove(Object obj) {
		return ((SparseField) field).remove(obj);
	}

	@Override
	public Bag getObjectsAtLocations(Bag locations, Bag result) {
		return ((SparseField) field).getObjectsAtLocations(locations, result);
	}

	@Override
	public Iterator iterator() {
		return ((SparseField) field).iterator();
	}

	@Override
	public Iterator locationBagIterator() {
		return ((SparseField) field).locationBagIterator();
	}
	
	@Override
	public Bag getNeighborsMaxDistance(int x, int y, int dist,
			boolean toroidal, Bag result, IntBag xPos, IntBag yPos) {
		return ((SparseGrid2D) field).getNeighborsMaxDistance(x, y, dist, toroidal, result, xPos, yPos);
	}

	@Override
	public Bag getNeighborsAndCorrespondingPositionsMaxDistance(int x, int y,
			int dist, boolean toroidal, Bag result, IntBag xPos, IntBag yPos) {
		return ((SparseGrid2D) field).getNeighborsAndCorrespondingPositionsMaxDistance(x, y, dist, toroidal, result, xPos, yPos);
	}

	@Override
	public Bag getNeighborsHamiltonianDistance(int x, int y, int dist,
			boolean toroidal, Bag result, IntBag xPos, IntBag yPos) {
		return ((SparseGrid2D) field).getNeighborsHamiltonianDistance(x, y, dist, toroidal, result, xPos, yPos);
	}

	@Override
	public Bag getNeighborsAndCorrespondingPositionsHamiltonianDistance(int x,
			int y, int dist, boolean toroidal, Bag result, IntBag xPos,
			IntBag yPos) {
		return ((SparseGrid2D) field).getNeighborsAndCorrespondingPositionsHamiltonianDistance(x, y, dist, toroidal, result, xPos, yPos);
	}

	@Override
	public Bag getNeighborsHexagonalDistance(int x, int y, int dist,
			boolean toroidal, Bag result, IntBag xPos, IntBag yPos) {
		return ((SparseGrid2D) field).getNeighborsHexagonalDistance(x, y, dist, toroidal, result, xPos, yPos);
	}

	@Override
	public Bag getNeighborsAndCorrespondingPositionsHexagonalDistance(int x,
			int y, int dist, boolean toroidal, Bag result, IntBag xPos,
			IntBag yPos) {
		return ((SparseGrid2D) field).getNeighborsAndCorrespondingPositionsHexagonalDistance(x, y, dist, toroidal, result, xPos, yPos);
	}

	@Override
	public Bag getObjectsAtLocations(IntBag xPos, IntBag yPos, Bag result) {
		return ((SparseGrid2D) field).getObjectsAtLocations(xPos, yPos, result);
	}

}
