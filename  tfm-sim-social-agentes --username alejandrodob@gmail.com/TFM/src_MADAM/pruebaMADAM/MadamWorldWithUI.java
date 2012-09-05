package pruebaMADAM;


import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.JFrame;



import sim.display.Console;
import sim.display.Controller;
import sim.display.Display2D;
import sim.display.GUIState;
import sim.engine.SimState;
import sim.field.SparseField2D;
import sim.field.grid.SparseGrid2D;
import sim.field.network.Edge;
import sim.portrayal.DrawInfo2D;
import sim.portrayal.Inspector;
import sim.portrayal.grid.ObjectGridPortrayal2D;
import sim.portrayal.grid.SparseGridPortrayal2D;
import sim.portrayal.network.NetworkPortrayal2D;
import sim.portrayal.network.SimpleEdgePortrayal2D;
import sim.portrayal.network.SpatialNetwork2D;
import sim.portrayal.simple.CircledPortrayal2D;
import sim.portrayal.simple.LabelledPortrayal2D;
import sim.portrayal.simple.MovablePortrayal2D;
import sim.portrayal.simple.OvalPortrayal2D;
import socialNetwork.Relation;


public class MadamWorldWithUI extends GUIState{
	public Display2D display;
	public JFrame displayFrame;
	SparseGridPortrayal2D espacioPortrayal = new SparseGridPortrayal2D();
	NetworkPortrayal2D amistadesPortrayal = new NetworkPortrayal2D();
	NetworkPortrayal2D parejasPortrayal = new NetworkPortrayal2D();

	//display para las estadisticas
	public Display2D estadDisplay;
	public JFrame estadDisplayFrame;
	SparseGridPortrayal2D estadPortrayal = new SparseGridPortrayal2D();
	NetworkPortrayal2D estadNetPortrayal = new NetworkPortrayal2D();


	public static void main(String[] args){
		MadamWorldWithUI vid = new MadamWorldWithUI();
		Console c = new Console(vid);
		c.setVisible(true);
	}

	public MadamWorldWithUI(){
		super(new MadamWorld(System.currentTimeMillis())); 
	}
	public MadamWorldWithUI(SimState state){
		super(state); 
	}
	public static String getName(){
		return "Prueba"; 
	}

	public Object getSimulationInspectedObject(){return state;}

	public Inspector getInspector(){
		Inspector i = super.getInspector();
		i.setVolatile(true);
		return i;
	}

	public void start(){
		super.start();
		setupPortrayals();
	}

	public void load(SimState state){
		super.load(state);
		setupPortrayals();
	}

	public void setupPortrayals(){
		MadamWorld mundo = (MadamWorld) state;

		//2D
		//tell the portrayals what to portray and how to portray them
		espacioPortrayal.setField(mundo.getField().getGrid());
		espacioPortrayal.setPortrayalForAll(
				new MovablePortrayal2D(
						new CircledPortrayal2D(
								new LabelledPortrayal2D(
										new OvalPortrayal2D(){
											public void draw(Object object, Graphics2D graphics, DrawInfo2D info){
												paint = Color.blue;
												super.draw(object, graphics, info);
											}
										},
										5.0, null, Color.black, true),
										0, 5.0, Color.yellow, true))
		);
		SparseGrid2D mundoGrid = (SparseGrid2D) mundo.getField().getGrid();
		amistadesPortrayal.setField( new SpatialNetwork2D(mundoGrid, mundo.population.getNetwork()) );
		amistadesPortrayal.setPortrayalForAll(new SimpleEdgePortrayal2D());
		amistadesPortrayal.setPortrayalForObject(Relation.COUPLE, new SimpleEdgePortrayal2D(Color.red,null));
		

//		estadPortrayal.setField(mundo.estadGrid);
//		estadPortrayal.setPortrayalForAll(new MovablePortrayal2D(
//				new CircledPortrayal2D(new LabelledPortrayal2D(
//						new OvalPortrayal2D() {
//							public void draw(Object object,
//									Graphics2D graphics, DrawInfo2D info) {
//								paint = Color.blue;
//								super.draw(object, graphics, info);
//							}
//						}, 5.0, null, Color.black, true), 0, 5.0, Color.yellow,
//						true)));
//
//		estadNetPortrayal.setField( new SpatialNetwork2D( mundo.estadGrid, mundo.estadNet ) );
//		estadNetPortrayal.setPortrayalForAll(new SimpleEdgePortrayal2D(Color.red,null));


		//reschedule the displayer
		display.reset();
		display.setBackdrop(Color.white);

		estadDisplay.reset();
		estadDisplay.setBackdrop(Color.white);

		//redraw the display
		display.repaint();

		estadDisplay.repaint();

	}

	public void init(Controller c){
		super.init(c);
		//inicializacion de 2D
		display = new Display2D(600,600,this);
		display.setClipping(false);
		displayFrame = display.createFrame();
		displayFrame.setTitle("Modelo");
		c.registerFrame(displayFrame); // so the frame appears in the "Display" list
		displayFrame.setVisible(true);
		display.attach( amistadesPortrayal, "Amistades" );
		display.attach( parejasPortrayal, "Parejas");
		display.attach( espacioPortrayal, "Espacio" );

		estadDisplay = new Display2D(200,200,this);
		estadDisplay.setClipping(false);
		estadDisplayFrame = estadDisplay.createFrame();
		estadDisplayFrame.setTitle("Estadísticas");
		c.registerFrame(estadDisplayFrame);
		estadDisplayFrame.setVisible(true);
		estadDisplay.attach(estadPortrayal,"Estadisticas");
		estadDisplay.attach(estadNetPortrayal, ("AgenteEstadisticas"));
	}

	public void quit(){
		super.quit();

		//2D
		if (displayFrame != null) displayFrame.dispose();
		displayFrame = null;
		display = null;
	}

}

