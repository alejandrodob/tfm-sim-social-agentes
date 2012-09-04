package model.demography;

import agent.DemographicItem;

public interface Nuptiality {
	
	public double crudeNuptialityRate();
	
	public double generalNuptialityRate();
	
	public double ageSpecificNuptialityRate(int age);
	
	public int meanAgeAtFirstMarriage();
	
	public double divorceRate();

	public double weddingProbability(DemographicItem man, DemographicItem woman);

	public double divorceProbability(DemographicItem man, DemographicItem woman);

}
