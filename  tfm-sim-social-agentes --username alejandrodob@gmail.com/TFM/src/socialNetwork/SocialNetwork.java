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

	private void reset(boolean directed) {
		network.reset(directed);
	}

	private Edge[][][] getMultigraphAdjacencyMatrix() {
		return network.getMultigraphAdjacencyMatrix();
	}

	public void addNode(Object node) {
		network.addNode(node);
	}

	private void addEdge(Object from, Object to, Object info) {
		network.addEdge(from, to, info);
	}

	private void addEdge(Edge edge) {
		network.addEdge(edge);
	}

	private Edge removeEdge(Edge edge) {
		return network.removeEdge(edge);
	}

	public Object removeNode(Object node) {
		return network.removeNode(node);
	}

	private Bag clear() {
		return network.clear();
	}

	private Bag removeAllNodes() {
		return network.removeAllNodes();
	}

	private int getNodeIndex(Object node) {
		return network.getNodeIndex(node);
	}

	private Network cloneGraph() {
		return network.cloneGraph();
	}

	private Edge[][] getAdjacencyList(boolean outEdges) {
		return network.getAdjacencyList(outEdges);
	}

	private Edge[][] getAdjacencyMatrix() {
		return network.getAdjacencyMatrix();
	}

	private Bag getEdgesOut(Object node) {
		return network.getEdgesOut(node);
	}

	private Bag getEdgesIn(Object node) {
		return network.getEdgesIn(node);
	}

	private Bag getEdges(Object node, Bag bag) {
		return network.getEdges(node, bag);
	}

	private Bag getAllNodes() {
		return network.getAllNodes();
	}

	private Network getGraphComplement(boolean allowSelfLoops) {
		return network.getGraphComplement(allowSelfLoops);
	}

	private boolean isDirected() {
		return network.isDirected();
	}

	private Iterator iterator() {
		return network.iterator();
	}

	private boolean nodeExists(Object node) {
		return network.nodeExists(node);
	}

	private void reverseAllEdges() {
		network.reverseAllEdges();
	}

	private Edge updateEdge(Edge edge, Object from, Object to, Object info) {
		return network.updateEdge(edge, from, to, info);
	}


}
