package agent.social;

import java.util.ArrayList;

import agent.DemographicItem;
import agent.Man;
import agent.Woman;

public class FriendsListNetwork extends ListNetwork implements FriendsNetwork {

	@Override
	public ArrayList<Woman> femaleFriends() {
		ArrayList<Woman> femFriends = new ArrayList<Woman>();
		for (ListElement f:network) {
			if (f.agent instanceof Woman) femFriends.add((Woman) f.agent);
		}
		return femFriends;
	}

	@Override
	public ArrayList<Man> maleFriends() {
		ArrayList<Man> masFriends = new ArrayList<Man>();
		for (ListElement f:network) {
			if (f.agent instanceof Man) masFriends.add((Man) f.agent);
		}
		return masFriends;
	}

}
