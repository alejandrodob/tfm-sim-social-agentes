/*package pruebas;

import agent.Man;
import agent.Person;
import agent.Woman;
import agent.behavior.BasicChildBehavior;
import agent.behavior.BasicFemaleBehavior;
import agent.behavior.BasicMaleBehavior;
import agent.social.FamilyListNetwork;
import sim.util.Int2D;
import socialNetwork.SocialNetwork;
import model.World;

public class Prueba extends World {

	public Prueba(long seed) {
		super(seed);
	}

	// la idea es que bien esta clase o la clase world cuando se desarrolle más
	// y no tenga el nombre de prueba
	// tenga unos parametros que bien pueden ser distribuciones de probabilidad
	// o cosas así, para
	// que a la hora de crear los agentes se haga de acuerdo a estos datos.
	// estos datos se deberían poder
	// cargar de bases de datos tipo INE u otras maneras
	*//**
	 * @param args
	 *//*
	public static void main(String[] args) {
		doLoop(Prueba.class, args);
		System.exit(0);
	}

	public void start() {
		super.start();

		for (int i = 1; i < 30; ++i) {
			// en cada ciclo:
			//creamos un hombre, una mujer y un niño
			Man manecillo = new Man(new Int2D(i, i), 18 + i, false, null, null);
			Woman womancilla = new Woman(new Int2D(i + 5, i + 7), 18 + i,
					false, null, null);
			Person hijo;
			if (i % 2 <= 0) {
				hijo = (Man) new Man(new Int2D(i, i), random.nextInt(18),
						false, null, null);
			} else {
				hijo = (Woman) new Woman(new Int2D(i, i), random.nextInt(18),
						false, null, null);
			}
			
			//les añadimos los comportamientos pertinentes
			BasicChildBehavior cb = new BasicChildBehavior();
			BasicMaleBehavior mb = new BasicMaleBehavior();
			BasicFemaleBehavior fm = new BasicFemaleBehavior();
			manecillo.addBehaviorModule(mb);
			womancilla.addBehaviorModule(fm);
			hijo.addBehaviorModule(cb);
			
			//los añadimos al mundo
			addIndividual(manecillo, manecillo.getLocation());
			addIndividual(womancilla, womancilla.getLocation());
			addIndividual(hijo, hijo.getLocation());
			
			//los casamos y creamos lazos familiares entre ellos, tanto a nivel de la red social
			//de cada uno, como a nivel global
			registerWedding(manecillo, womancilla);
			manecillo.setCoupled(true);
			manecillo.addFamilyMember(womancilla,FamilyListNetwork.kinship.WIFE);
			womancilla.setCoupled(true);
			womancilla.addFamilyMember(manecillo,FamilyListNetwork.kinship.HUSBAND);
			addFamilyLink(manecillo, hijo, SocialNetwork.relation.FATHER_SON);
			addFamilyLink(womancilla, hijo, SocialNetwork.relation.MOTHER_SON);
			manecillo.addFamilyMember(hijo,FamilyListNetwork.kinship.SON);
			hijo.addFamilyMember(manecillo,FamilyListNetwork.kinship.FATHER);
			womancilla.addFamilyMember(hijo,FamilyListNetwork.kinship.SON);
			hijo.addFamilyMember(womancilla,FamilyListNetwork.kinship.MOTHER);

		}
	}

}
*/