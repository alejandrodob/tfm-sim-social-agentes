package agent.behavior;

import model.SimpleWorld;
import agent.DemographicItem;
import agent.Socializable;

public abstract class BasicSocialBehavior implements BehaviorModule {

	@Override
	public void behave(DemographicItem me, SimpleWorld environment) {
		meetPeople((Socializable) me, environment);
		searchForMate((Socializable) me, environment);
	}

	protected abstract void meetPeople(Socializable me,SimpleWorld environment);

	protected abstract void makeNewFriend(Socializable friend, Socializable me,SimpleWorld environment);

	protected abstract void searchForMate(Socializable me,SimpleWorld environment);

	protected abstract void marry(Socializable partner, Socializable me,SimpleWorld environment);
	
	public abstract boolean acceptFriend(Socializable friend, Socializable me);
	
	public abstract boolean acceptMarriage(Socializable candidate, Socializable me);

}
