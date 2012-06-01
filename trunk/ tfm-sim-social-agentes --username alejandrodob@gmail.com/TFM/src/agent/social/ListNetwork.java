package agent.social;

import java.util.ArrayList;

import agent.DemographicItem;

public class ListNetwork implements AgentSocialNetwork {

	private ArrayList<DemographicItem> network;
	
	public ListNetwork() {
		network = new ArrayList<DemographicItem>();
	}
	
	public ListNetwork(ArrayList<DemographicItem> network) {
		this.network = network;
	}

	@Override
	public void addMember(DemographicItem member) {
		network.add(member);
	}

	@Override
	public void removeMember(DemographicItem member) {
		network.remove(member);
	}

	@Override
	public boolean isEmpty() {
		return network.isEmpty();
	}
	
	
}
