package agent.behavior;

import model.SimpleWorld;
import agent.DemographicItem;
import agent.Socializable;

public abstract class BasicSocialBehavior implements BehaviorModule {

	@Override
	public void behave(DemographicItem me, SimpleWorld environment) {
		meetPeople((Socializable) me);
		searchForMate((Socializable) me);
	}

	protected abstract void meetPeople(Socializable me);

	protected abstract void makeNewFriend(Socializable friend, Socializable me);

	protected abstract void searchForMate(Socializable me);

	protected abstract void marry(Socializable partner, Socializable me);
	
	public abstract boolean acceptFriend(Socializable friend, Socializable me);
	
	public abstract boolean acceptMarriage(Socializable candidate, Socializable me);

}
