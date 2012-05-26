package socialNetwork;

import java.util.Iterator;

import sim.field.network.Edge;
import sim.field.network.Network;
import sim.util.Bag;

public class SocialNetwork {
	//aunque de momento se quede asi, me gustaria que esta clase fuera una capa de abstraccion 
	//superior (o inferior, segun se mire) sobre Network, que tuviera metodos mas propios del problema en cuestion, como
	//añadir una amistad, eliminarla, añadir personas, sacar estadisticas, etc, en lugar de 
	//trabajar con nodos y aristas que queda un poco feo
	private Network network;
	
	private enum relation {
		FAMILY, FRIEND
	}

	public SocialNetwork() {
		super();
		network = new Network(false);
	}

	public void reset(boolean directed) {
		network.reset(directed);
	}

	public Edge[][][] getMultigraphAdjacencyMatrix() {
		return network.getMultigraphAdjacencyMatrix();
	}

	public void addNode(Object node) {
		network.addNode(node);
	}

	public void addEdge(Object from, Object to, Object info) {
		network.addEdge(from, to, info);
	}

	public void addEdge(Edge edge) {
		network.addEdge(edge);
	}

	public Edge removeEdge(Edge edge) {
		return network.removeEdge(edge);
	}

	public Object removeNode(Object node) {
		return network.removeNode(node);
	}

	public Bag clear() {
		return network.clear();
	}

	public Bag removeAllNodes() {
		return network.removeAllNodes();
	}

	public int getNodeIndex(Object node) {
		return network.getNodeIndex(node);
	}

	@SuppressWarnings("deprecation")
	public Network cloneGraph() {
		return network.cloneGraph();
	}

	public boolean equals(Object obj) {
		return network.equals(obj);
	}

	public Edge[][] getAdjacencyList(boolean outEdges) {
		return network.getAdjacencyList(outEdges);
	}

	public Edge[][] getAdjacencyMatrix() {
		return network.getAdjacencyMatrix();
	}

	public Bag getEdgesOut(Object node) {
		return network.getEdgesOut(node);
	}

	public Bag getEdgesIn(Object node) {
		return network.getEdgesIn(node);
	}

	public Bag getEdges(Object node, Bag bag) {
		return network.getEdges(node, bag);
	}

	public Bag getAllNodes() {
		return network.getAllNodes();
	}

	public Network getGraphComplement(boolean allowSelfLoops) {
		return network.getGraphComplement(allowSelfLoops);
	}

	public int hashCode() {
		return network.hashCode();
	}

	public boolean isDirected() {
		return network.isDirected();
	}

	public Iterator iterator() {
		return network.iterator();
	}

	public boolean nodeExists(Object node) {
		return network.nodeExists(node);
	}

	public void reverseAllEdges() {
		network.reverseAllEdges();
	}

	public String toString() {
		return network.toString();
	}

	public Edge updateEdge(Edge edge, Object from, Object to, Object info) {
		return network.updateEdge(edge, from, to, info);
	}
	
	

}
