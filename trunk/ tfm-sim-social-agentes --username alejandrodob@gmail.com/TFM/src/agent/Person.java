package agent;

import java.util.ArrayList;

import agent.behavior.Behavior;
import agent.social.AgentSocialNetwork;
import agent.social.FamilyListNetwork;
import agent.social.FriendsListNetwork;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.util.MutableInt2D;
import household.Household;

public abstract class Person extends DemographicItem implements Socializable{
	
	protected int age;
	protected boolean coupled;
	protected SocioeconomicLevel socLevel;
	protected Education education;
	protected Household household;
	protected AgentSocialNetwork family;
	protected AgentSocialNetwork friends;
	protected static int stepsPerYear = 50;
	
	public Person() {
		super();
	}
	
	public Person(int age, boolean coupled, SocioeconomicLevel socLevel,
			Education education) {
		super();
		this.age = age;
		this.coupled = coupled;
		this.socLevel = socLevel;
		this.education = education;
		family = new FamilyListNetwork();
		friends = new FriendsListNetwork();
	}

	public Person(MutableInt2D location, Behavior behavior, int age, boolean coupled, SocioeconomicLevel socLevel,
			Education education) {
		super(location, behavior);
		this.age = age;
		this.coupled = coupled;
		this.socLevel = socLevel;
		this.education = education;
		family = new FamilyListNetwork();
		friends = new FriendsListNetwork();
	}
	
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public boolean isCoupled() {
		return coupled;
	}
	public void setCoupled(boolean coupled) {
		this.coupled = coupled;
	}
	public SocioeconomicLevel getSocLevel() {
		return socLevel;
	}
	public void setSocLevel(SocioeconomicLevel socLevel) {
		this.socLevel = socLevel;
	}
	public Education getEducation() {
		return education;
	}
	public void setEducation(Education education) {
		this.education = education;
	}
	public Household getHousehold() {
		return household;
	}
	public void setHousehold(Household household) {
		this.household = household;
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
