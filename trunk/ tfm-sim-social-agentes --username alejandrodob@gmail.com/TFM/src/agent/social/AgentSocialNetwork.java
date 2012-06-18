package agent.social;

import agent.DemographicItem;

public interface AgentSocialNetwork {
	
	public void addMember(DemographicItem member);
	public void addMember(DemographicItem member, Object atribute);
	public void removeMember(DemographicItem member);
	public boolean isEmpty();
}
