package agent.social;

public interface FamilyNetwork {
	
	public algo Parents();
	public algo Siblings();
	public algo Father();
	public algo Mother();
	public algo sons();
	//etcetera. problema que se me ocurre a priori, a la hora de añadir a un miembro, hay q override el metodo 
	//de AgentSocialNetwork, el problema es como meter el parentesco con la persona, ya se me ocurrira algo
	//otra cosa: la idea es que la clase con funcionalidad extienda ListNetwork e implemente a esta
}
