package pruebaMADAM;

import model.demography.Mortality;
import agent.DemographicItem;
import agent.Person;
import ec.util.MersenneTwisterFast;

public class MortalityExample implements Mortality {
	
	//SINGLETON

	private static MersenneTwisterFast random = new MersenneTwisterFast();
	private static MortalityExample INSTANCE = new MortalityExample();
	
	private MortalityExample() {}
	
	public static MortalityExample getInstance() {
		return INSTANCE;
	}

	@Override
	public boolean timeToDie(DemographicItem persona) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int ageOfDeath(DemographicItem persona) {
		int age = ((Person) persona).getAge();
		if (age == 1){
			if (random.nextBoolean(probabilidadMuerte(age))){
				return (age + random.nextInt(4));
			}
		}
		else 
			if (random.nextBoolean(probabilidadMuerte(age))){
				return (age + random.nextInt(5));
			}
		return 100;
	}
	
	@Override
	public double crudeDeathRate() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int lifeExpectancy(DemographicItem person) {
		int age = ((Person) person).getAge();
		if (age == 1){
			if (random.nextBoolean(probabilidadMuerte(age))){
				return (random.nextInt(4));
			}
		}
		else 
			if (random.nextBoolean(probabilidadMuerte(age))){
				return (random.nextInt(5));
			}
		return 100;
	}

	@Override
	public double deathProbability(DemographicItem person) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double ageSpecificDeathRate(int age) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double sexSpecificDeathRate(boolean man) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double ageSexSpecificDeathRate(int age, boolean man) {
		// TODO Auto-generated method stub
		return 0;
	}


	// Tabla de mortalidad de la poblaci�n espa�ola, a�os 1990-1991. Fuente:
	// INE.
	public double probabilidadMuerte(int edad) {
		double pm;

		if (edad < 1)
			pm = 0.00783;
		else if ((1 <= edad) && (edad < 5))
			pm = 0.00182;
		else if ((5 <= edad) && (edad < 10))
			pm = 0.00118;
		else if ((10 <= edad) && (edad < 15))
			pm = 0.00128;
		else if ((15 <= edad) && (edad < 20))
			pm = 0.00323;
		else if ((20 <= edad) && (edad < 25))
			pm = 0.00511;
		else if ((25 <= edad) && (edad < 30))
			pm = 0.00596;
		else if ((30 <= edad) && (edad < 35))
			pm = 0.00628;
		else if ((35 <= edad) && (edad < 40))
			pm = 0.00696;
		else if ((40 <= edad) && (edad < 45))
			pm = 0.00963;
		else if ((45 <= edad) && (edad < 50))
			pm = 0.01384;
		else if ((50 <= edad) && (edad < 55))
			pm = 0.02316;
		else if ((55 <= edad) && (edad < 60))
			pm = 0.03411;
		else if ((60 <= edad) && (edad < 65))
			pm = 0.05197;
		else if ((65 <= edad) && (edad < 70))
			pm = 0.08113;
		else if ((70 <= edad) && (edad < 75))
			pm = 0.12843;
		else if ((75 <= edad) && (edad < 80))
			pm = 0.21538;
		else if ((80 <= edad) && (edad < 85))
			pm = 0.34711;
		else if ((85 <= edad) && (edad < 90))
			pm = 0.51997;
		else if ((90 <= edad) && (edad < 95))
			pm = 0.71682;
		else if ((95 <= edad) && (edad < 100))
			pm = 0.89388;
		else
			pm = 1.0;

		return pm;
	}

}
