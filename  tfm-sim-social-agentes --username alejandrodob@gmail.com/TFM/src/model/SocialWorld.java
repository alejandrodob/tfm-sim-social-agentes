package model;

import sim.util.Int2D;
import agent.DemographicItem;

public interface SocialWorld { //este nombre no me gusta nada, espero encontrar uno mas apropiado

	public void addIndividual(DemographicItem individual);
	public DemographicItem removeIndividual(DemographicItem individual);
	public void registerDeath(DemographicItem individual);
	public void registerBirth(DemographicItem newborn, DemographicItem mother);
	public void registerWedding(DemographicItem individual1, DemographicItem individual2);
	public void registerDivorce(DemographicItem individual1, DemographicItem individual2);
	public void registerMigration(DemographicItem individual, Int2D from, Int2D to);
	public void addFamilyLink(DemographicItem individual1, DemographicItem individual2);
	public void addFriendshipLink(DemographicItem individual1, DemographicItem individual2);
}
