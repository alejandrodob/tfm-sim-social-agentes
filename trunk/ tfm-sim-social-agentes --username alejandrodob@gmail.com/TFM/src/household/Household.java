package household;

import java.util.ArrayList;

import agent.DemographicItem;
import agent.Location;
import agent.SocioeconomicLevel;
import agent.Person;
import sim.engine.SimState;
import sim.engine.Steppable;

public class Household extends DemographicItem {

	private Location location;
	private HouseData characteristics;
	private ArrayList<Person> householdMembers;
	private SocioeconomicLevel socLevel;
	

}
