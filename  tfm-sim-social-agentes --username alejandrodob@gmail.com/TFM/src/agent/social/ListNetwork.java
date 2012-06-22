package agent.social;

import java.util.ArrayList;
import java.util.Iterator;

import agent.DemographicItem;
import agent.social.ListNetwork.ListElement;

public class ListNetwork implements AgentSocialNetwork {

	protected ArrayList<ListElement> network;

	protected class ListElement {
		DemographicItem agent;
		Object atribute;

		ListElement(DemographicItem agent, Object atribute) {
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
		addMember(member, null);
	}

	@Override
	public void addMember(DemographicItem member, Object atribute) {
		network.add(new ListElement(member, atribute));
	}

	@Override
	public void removeMember(DemographicItem member) { // este metodo hay que
														// probarlo
		Iterator<ListElement> it = network.iterator();
		boolean found = false;
		while (it.hasNext() && !found) {
			ListElement next = it.next();
			if (next.agent.equals(member)) {
				found = network.remove(next);
			}
		}
	}

	@Override
	public boolean isEmpty() {
		return network.isEmpty();
	}

	@Override
	public boolean containsMember(DemographicItem member) {
		Iterator<ListElement> it = network.iterator();
		boolean found = false;
		while (it.hasNext() && !found) {
			ListElement next = it.next();
			found = next.agent.equals(member);
		}
		return found;
	}

}
