package pruebas;

import model.World;

public class Prueba {
    // world la idea es que bien esta clase cuando se desarrolle más y no tenga el nombre de prueba o la clase
	// tenga unos parametros que bien pueden ser distribuciones de probabilidad o cosas así, para 
	// que a la hora de crear los agentes se haga de acuerdo a estos datos. estos datos se deberían poder 
	// cargar de bases de datos tipo INE u otras maneras
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		World world = new World(System.currentTimeMillis(),400,400);
		
	}

}
