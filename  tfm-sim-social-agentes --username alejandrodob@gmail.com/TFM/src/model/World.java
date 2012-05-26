package model;

import agent.DemographicItem;
import environment.*;
import sim.engine.SimState;
import sim.field.grid.SparseGrid2D;
import sim.field.network.Network;
import sim.util.Int2D;
import socialNetwork.SocialNetwork;

public class World extends SimState implements BasicDemoWorld{
	
	public SocialNetwork population;
	public SparseGrid2D field;
	
	public Natality natality;
	public Mortality mortality;
	public Mobility mobility;
	public Nuptiality nuptiality;
	
	
	public World(long seed,int height, int width) {
		super(seed);
		population = new SocialNetwork();
		field = new SparseGrid2D(height, width);
	}
	@Override
	public void addIndividual(DemographicItem individual) {
		population.addNode(individual);
	}

	@Override
	public DemographicItem removeIndividual(DemographicItem individual) {
		return (DemographicItem) population.removeNode(individual);
	}

	@Override
	public void registerDeath(DemographicItem individual) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerBirth(DemographicItem newborn, DemographicItem mother) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerWedding(DemographicItem individual1,
			DemographicItem individual2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerDivorce(DemographicItem individual1,
			DemographicItem individual2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerMigration(DemographicItem individual, Int2D from,
			Int2D to) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void addFamilyLink(DemographicItem individual1,
			DemographicItem individual2) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void addFriendshipLink(DemographicItem individual1,
			DemographicItem individual2) {
		// TODO Auto-generated method stub
		
	}

}
