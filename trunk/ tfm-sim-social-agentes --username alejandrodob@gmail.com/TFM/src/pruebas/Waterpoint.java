package pruebas;

class Waterpoint {
	final int x;
	final int y;
	final int sarg;
	final int meterNorth;
	final int meterEast;
	final int typeWater;
	final int startDate;
	final int endDate;
	
	public Waterpoint(int x, int y, int sarg, int meterNorth, int meterEast, int typeWater, int startDate, int endDate) {
		this.x = x;
		this.y = y;
		this.sarg = sarg;
		this.meterNorth = meterNorth;
		this.meterEast = meterEast;
		this.typeWater = typeWater;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	@Override
	public String toString() {
		String res = "";
		res+= sarg+", "+meterNorth+", "+meterEast+", "+typeWater+", "+startDate+", "+endDate;
		return res;
	}
}
