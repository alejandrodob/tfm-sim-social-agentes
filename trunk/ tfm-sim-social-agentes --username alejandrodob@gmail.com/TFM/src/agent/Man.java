package agent;

import sim.util.MutableInt2D;

public class Man extends Person {
	
	public Man() {
		super();
	}

	public Man(MutableInt2D location, int age, boolean coupled, SocioeconomicLevel socLevel,
			Education education) {
		super(location, new ListBehavior(), age, coupled, socLevel, education);
	}

	@Override
	public void addFriend(DemographicItem friend) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeFriend(DemographicItem friend) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addFamilyMember(DemographicItem famMem) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeFamilyMember(DemographicItem famMem) {
		// TODO Auto-generated method stub
		
	}
	
	

}
