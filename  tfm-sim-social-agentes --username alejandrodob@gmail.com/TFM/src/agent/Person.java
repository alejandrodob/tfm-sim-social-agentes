package agent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import sim.util.Bag;
import sim.util.Int2D;
import agent.attributes.EconomicStatus;
import agent.attributes.EducationLevel;
import agent.attributes.SocialStatus;
import agent.behavior.BasicSocialBehavior;
import agent.behavior.Behavior;
import agent.behavior.BehaviorModule;
import agent.behavior.ListBehavior;
import agent.behavior.PriorityBehavior;
import agent.social.FamilyListNetwork;
import agent.social.FriendsListNetwork;
import agent.social.Kinship;

public class Person extends DemographicItem implements Socializable {
	
	protected enum Gender {
		FEMALE, MALE
	}
	
	protected boolean coupled;
	public final Gender gender;
	
	EconomicStatus economicStatus;
	EducationLevel educationLevel;
	SocialStatus socialStatus;

	protected FamilyListNetwork family;
	protected FriendsListNetwork friends;

	public Person() {
		super();
		gender = Gender.FEMALE; //default value
	}

	public Person(int age, boolean coupled, boolean female) {
		super();
		this.age = age;
		this.coupled = coupled;
		this.gender = female ? Gender.FEMALE : Gender.MALE;
		family = new FamilyListNetwork();
		friends = new FriendsListNetwork();
		behavior = new PriorityBehavior();
	}

	public Person(Int2D location, int age, boolean coupled, boolean female) {
		this(age,coupled,female);
		setLocation(location);
	}
	public Person(Int2D location, Behavior behavior, int age, boolean coupled, boolean female) {
		super(location, behavior);
		this.age = age;
		this.coupled = coupled;
		this.gender = female ? Gender.FEMALE : Gender.MALE;
		family = new FamilyListNetwork();
		friends = new FriendsListNetwork();
	}

	public boolean isCoupled() {
		return coupled;
	}

	public void setCoupled(boolean coupled) {
		this.coupled = coupled;
	}
	
	public boolean female() {
		return gender == Gender.FEMALE;
	}
	
	public boolean male() {
		return gender == Gender.MALE;
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
	public ArrayList<DemographicItem> peopleAround(int radius) {
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

	@Override
	public void divorce() {
		if (isCoupled()) {
			family.removeMember(family.couple());
			setCoupled(false);
		}	
	}

	@Override
	public void marry(Person partner) {
		if (!isCoupled()) {
			if (male()) {
				family.addMember(partner,Kinship.WIFE);
			} else {
				family.addMember(partner,Kinship.HUSBAND);
			}
			setCoupled(true);
		}
	}

}
