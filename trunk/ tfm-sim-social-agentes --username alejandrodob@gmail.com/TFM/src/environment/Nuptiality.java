package environment;

import agent.Man;
import agent.Woman;

public interface Nuptiality {

	public double probabilidadBoda(Man hombre, Woman mujer);

	public double probabilidadDivorcio(Man hombre, Woman mujer);

}
