package agent.social;

import java.util.ArrayList;

import agent.DemographicItem;

public class ListNetwork implements AgentSocialNetwork {

	protected ArrayList<ListElement> network;
	
	protected class ListElement {
		DemographicItem agent;
		Object atribute;
		ListElement(DemographicItem agent,Object atribute) {
			this.agent = agent;
			this.atribute = atribute;
		}
	}
	
	public ListNetwork() {
		network = new ArrayList<ListElement>();
	}
	
	public ListNetwork(ArrayList<ListElement> network) {
		this.network = network;
	}

	@Override
	public void addMember(DemographicItem member) {
		network.add(new ListElement(member,null));
	}

	@Override
	public void removeMember(DemographicItem member) {
		//hay que modificarlo por los cambios de ListElement, para que encuentre el que corresponde
		//al DemographicItem member
		network.remove(member);
	}

	@Override
	public boolean isEmpty() {
		return network.isEmpty();
	}
	
	
}
