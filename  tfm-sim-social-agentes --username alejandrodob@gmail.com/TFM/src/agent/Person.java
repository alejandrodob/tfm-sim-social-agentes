package agent;

import household.Household;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import sim.util.Bag;
import sim.util.Int2D;
import agent.behavior.BasicSocialBehavior;
import agent.behavior.Behavior;
import agent.behavior.BehaviorModule;
import agent.behavior.ListBehavior;
import agent.social.FamilyListNetwork;
import agent.social.FriendsListNetwork;

public abstract class Person extends DemographicItem implements Socializable {
	
	///////////////////////////////////////////////////////
	//esto de que sea abstract, no sé yo, igual lo cambio//
	///////////////////////////////////////////////////////
	
	protected int ageInYears;
	protected int ageInSimulationSteps;
	protected final static int stepsPerYear = 50;//maybe should be in the class World, as it is a general parameter of the simulation
	protected boolean coupled;
	protected SocioeconomicLevel socLevel;
	protected Education education;
	protected Household household;
	protected FamilyListNetwork family;
	protected FriendsListNetwork friends;

	public Person() {
		super();
	}

	public Person(int age, boolean coupled, SocioeconomicLevel socLevel,
			Education education) {
		super();
		this.ageInYears = age;
		this.ageInSimulationSteps = ageInYears * stepsPerYear;
		this.coupled = coupled;
		this.socLevel = socLevel;
		this.education = education;
		family = new FamilyListNetwork();
		friends = new FriendsListNetwork();
		behavior = new ListBehavior();
	}

	public Person(Int2D location, Behavior behavior, int age, boolean coupled,
			SocioeconomicLevel socLevel, Education education) {
		super(location, behavior);
		this.ageInYears = age;
		this.ageInSimulationSteps = ageInYears * stepsPerYear;
		this.coupled = coupled;
		this.socLevel = socLevel;
		this.education = education;
		family = new FamilyListNetwork();
		friends = new FriendsListNetwork();
	}

	public int getAge() {
		return ageInYears;
	}

	public void setAge(int age) {
		ageInYears = age;
	}
	
	public int getSteps() {
		return ageInSimulationSteps;
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

	public FamilyListNetwork getFamily() {
		return family;
	}

	public void setFamily(FamilyListNetwork family) {
		this.family = family;
	}

	public FriendsListNetwork getFriends() {
		return friends;
	}

	public void setFriends(FriendsListNetwork friends) {
		this.friends = friends;
	}

	@Override
	public void addFriend(DemographicItem friend, Object atribute) {
		friends.addMember(friend);
		//cuando se defina bien el atributo de amistad (o una interfaz para el mismo) se tendra
		//en cuenta
	}

	@Override
	public void removeFriend(DemographicItem friend) {
		friends.removeMember(friend);
	}

	@Override
	public void addFamilyMember(DemographicItem famMem, Object kinship) {
		family.addMember(famMem, kinship);
	}

	@Override
	public void removeFamilyMember(DemographicItem famMem) {
		family.removeMember(famMem);
	}

	@Override
	public boolean isFriend(DemographicItem person) {
		return friends.isFriend((Person) person);
	}

	@Override
	public Collection<DemographicItem> peopleAround(int radius) {
		Bag people = new Bag();
		ArrayList<DemographicItem> peopleArray = new ArrayList<DemographicItem>();
		people = field.getNeighborsMaxDistance(location.getX(),location.getY(),radius,false,people,null,null);
		for (Object p:people) {
			peopleArray.add((DemographicItem) p);
		}
		return peopleArray;
	}

	@Override
	public boolean acceptFriendshipProposal(Person friend) {
		//search the behavior that handles this. it must be a subclass of BasicSocialBehavior
		BehaviorModule social = null;
		boolean found = false;
		Iterator<BehaviorModule> it = behavior.getBehaviors().iterator();
		while (!found && it.hasNext()) {
			social = it.next();
			found = (social instanceof BasicSocialBehavior);
		}
		return found && ((BasicSocialBehavior)social).acceptFriend(friend,this);
	}

	@Override
	public boolean acceptMarriageProposal(Person candidate) {
		//search the behavior that handles this. it must be a subclass of BasicSocialBehavior
		BehaviorModule social = null;
		boolean found = false;
		Iterator<BehaviorModule> it = behavior.getBehaviors().iterator();
		while (!found && it.hasNext()) {
			social = it.next();
			found = (social instanceof BasicSocialBehavior);
		}
		return found && ((BasicSocialBehavior)social).acceptMarriage(candidate,this);
	}

}
