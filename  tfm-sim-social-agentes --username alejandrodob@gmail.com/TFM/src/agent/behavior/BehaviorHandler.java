package agent.behavior;

public interface BehaviorHandler {
//	Esta clase de momento no la uso, no se muy bien si hacer que un agente tenga que implementar esto
//	por narices o simplemente que implemente los metodos que aqui se definen sin implementar esta interface
	public void addBehaviorModule(BehaviorModule behavior);
	public BehaviorModule removeBehaviorModule(BehaviorModule behavior);

}
