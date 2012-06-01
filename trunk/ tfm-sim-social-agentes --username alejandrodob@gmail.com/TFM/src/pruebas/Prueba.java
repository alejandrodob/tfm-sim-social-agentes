package pruebas;

import agent.Man;
import agent.Woman;
import agent.behavior.BasicFemaleBehavior;
import agent.behavior.BasicMaleBehavior;
import sim.util.MutableInt2D;
import model.World;

public class Prueba extends World{
	
	public Prueba(long seed) {
		super(seed);
	}

	// la idea es que bien esta clase o la clase world cuando se desarrolle más y no tenga el nombre de prueba 
	// tenga unos parametros que bien pueden ser distribuciones de probabilidad o cosas así, para 
	// que a la hora de crear los agentes se haga de acuerdo a estos datos. estos datos se deberían poder 
	// cargar de bases de datos tipo INE u otras maneras
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		doLoop(Prueba.class, args);
		System.exit(0);
	}
	
	public void start() {
		super.start();
		
		for (int i=1;i<30;++i) {
			Man manecillo = new Man(new MutableInt2D(i,i),15+i,false,null,null);
			Woman womancilla = new Woman(new MutableInt2D(i+5,i+7),15+i,false,null,null);
			BasicMaleBehavior mb = new BasicMaleBehavior();
			BasicFemaleBehavior fm = new BasicFemaleBehavior();
			manecillo.addBehaviorModule(mb);
			womancilla.addBehaviorModule(fm);
			addIndividual(manecillo);
			addIndividual(womancilla);
			schedule.scheduleRepeating(manecillo);
			schedule.scheduleRepeating(womancilla);
			
		}
	}

}
