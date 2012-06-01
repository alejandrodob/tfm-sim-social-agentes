package agent.social;

import agent.DemographicItem;

public interface AgentSocialNetwork {
	
	public void addMember(DemographicItem member);
	public void removeMember(DemographicItem member);
	public boolean isEmpty();
}
