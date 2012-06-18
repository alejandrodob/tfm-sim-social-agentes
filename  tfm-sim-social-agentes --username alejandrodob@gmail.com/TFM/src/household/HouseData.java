package household;

public class HouseData {

	private double superficie;
	private int periodoConstruccion;
	private int numPlantas;
	private int numHabitaciones;
	private int numServicios;

	public HouseData(double superficie, int periodoConstruccion,
			int numPlantas, int numHabitaciones, int numServicios) {
		super();
		this.superficie = superficie;
		this.periodoConstruccion = periodoConstruccion;
		this.numPlantas = numPlantas;
		this.numHabitaciones = numHabitaciones;
		this.numServicios = numServicios;
	}

	public double getSuperficie() {
		return superficie;
	}

	public void setSuperficie(double superficie) {
		this.superficie = superficie;
	}

	public int getPeriodoConstruccion() {
		return periodoConstruccion;
	}

	public void setPeriodoConstruccion(int periodoConstruccion) {
		this.periodoConstruccion = periodoConstruccion;
	}

	public int getNumPlantas() {
		return numPlantas;
	}

	public void setNumPlantas(int numPlantas) {
		this.numPlantas = numPlantas;
	}

	public int getNumHabitaciones() {
		return numHabitaciones;
	}

	public void setNumHabitaciones(int numHabitaciones) {
		this.numHabitaciones = numHabitaciones;
	}

	public int getNumServicios() {
		return numServicios;
	}

	public void setNumServicios(int numServicios) {
		this.numServicios = numServicios;
	}

}
