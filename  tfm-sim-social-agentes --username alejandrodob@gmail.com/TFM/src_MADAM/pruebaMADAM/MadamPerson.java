package pruebaMADAM;

import sim.util.Int2D;
import ec.util.MersenneTwisterFast;
import agent.Person;
import agent.behavior.Behavior;
import agent.behavior.PriorityBehavior;

public abstract class MadamPerson extends Person {

	protected Caracteristicas caracteristicas;
	protected int nivelExigencia;
	protected int edadMuerte;
	protected int stepsPerYear = 50;
	protected int steps;
	private static MersenneTwisterFast random = new MersenneTwisterFast();
	
	public MadamPerson(Int2D location, int age, boolean coupled, boolean female, int totalCaract, int numCarac) {
		super(location,age,coupled,female);
		caracteristicas = new Caracteristicas(totalCaract,numCarac);
		behavior = new PriorityBehavior();
		if (age < 16) behavior.addBehaviorMod(ChildSocialBehavior.getInstance(),0);
		else if (coupled) behavior.addBehaviorMod(MarriedSocialBehavior.getInstance(),0);
		else behavior.addBehaviorMod(SingleSocialBehavior.getInstance(),0);
		nivelExigencia = caracteristicas.numCaract-1;
		edadMuerte = 100;
		steps = age * stepsPerYear + random.nextInt(stepsPerYear);
	}
	
	public MadamPerson(Int2D location, int age, boolean coupled, boolean female, Caracteristicas caract) {
		super(location,age,coupled,female);
		caracteristicas = caract;
		behavior = new PriorityBehavior();
		if (age < 16) behavior.addBehaviorMod(ChildSocialBehavior.getInstance(),0);
		else if (coupled) behavior.addBehaviorMod(MarriedSocialBehavior.getInstance(),0);
		else behavior.addBehaviorMod(SingleSocialBehavior.getInstance(),0);
		nivelExigencia = caracteristicas.numCaract-1;
		edadMuerte = 100;
		steps = age * stepsPerYear + random.nextInt(stepsPerYear);
	}
	
	public class Caracteristicas {
		
		int totalCaract;
		int numCaract; //numCaract<=totalCaract
		private boolean[] cuales; //representamos cuales de entre todas las caracteristicas posee
		
		public Caracteristicas(){}
		
		public Caracteristicas(int total, int num) throws IllegalArgumentException{
			if (total >= num) {
				totalCaract = total;
				numCaract = num;
				cuales = new boolean[total];
				for (int i=1;i<total;i++){
					cuales[i] = false;
				}
				int llenar = 0;
				while (llenar < num){
					int i = random.nextInt(total);
					if (!cuales[i]){
						cuales[i] = true;
						llenar++;
					}
				}
			}
			else throw new IllegalArgumentException("el numero de caracteristicas que tiene cada individuo no puede superar al total");
		}
		
		public boolean[] getCaracteristicas(){
			return cuales;
		}
	}

	public Caracteristicas getCaracteristicas() {
		return caracteristicas;
	}

	public void setCaracteristicas(Caracteristicas caracteristicas) {
		this.caracteristicas = caracteristicas;
	}

	public int getNivelExigencia() {
		return nivelExigencia;
	}

	public void setNivelExigencia(int nivelExigencia) {
		this.nivelExigencia = nivelExigencia;
	}

	public int getEdadMuerte() {
		return edadMuerte;
	}
	
	public void setEdadMuerte(int edadMuerte) {
		this.edadMuerte = edadMuerte;
	}
	
	public void notifyDeath() {
		//notify your death to your family
		for (Person relative : getFamily().members()) {
			((MadamPerson) relative).relativeDeath(this);
		}
		//notify your friends
		for (Person friend : getFriends().friends()) {
			((MadamPerson) friend).friendDeath(this);
		}
	}
	
	public void friendDeath(MadamPerson friend) {
		removeFriend(friend);
	}
	
	public void relativeDeath(MadamPerson relative) {
		if (family.couple().equals(relative)) {
			setCoupled(false);
		}
		removeFamilyMember(relative);
	}
	
	public void askForDivorce() {
		family.couple().divorce();
	}
	
	public void die() {
		notifyDeath();
		stop.stop();
	}
	
	public void incSteps() {
		steps++;
	}
	
	public int steps() {
		return steps;
	}
	
	@Override
	public int getAge() {
		return steps/stepsPerYear;
	}
	
}
