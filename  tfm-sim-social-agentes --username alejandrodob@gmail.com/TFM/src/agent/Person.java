package agent;

import java.util.ArrayList;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.util.MutableInt2D;
import household.Household;

public abstract class Person extends DemographicItem implements Socializable{
	
	private int age;
	private boolean coupled;
	private SocioeconomicLevel socLevel;
	private Education education;
	private Household household;
	private ArrayList<Person> family;
	private ArrayList<Person> friends;
	
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
		family = new ArrayList<Person>();
		friends = new ArrayList<Person>();
	}

	public Person(MutableInt2D location, Behavior behavior, int age, boolean coupled, SocioeconomicLevel socLevel,
			Education education) {
		super(location, behavior);
		this.age = age;
		this.coupled = coupled;
		this.socLevel = socLevel;
		this.education = education;
		family = new ArrayList<Person>();
		friends = new ArrayList<Person>();
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
	

}
