package socialNetwork;

import java.util.Iterator;

import sim.field.network.Edge;
import sim.field.network.Network;
import sim.util.Bag;
import agent.Person;

public class SocialNetwork {

	private Network network;

	public SocialNetwork() {
		super();
		network = new Network(false);
	}

	public void addPerson(Person person) {
		addNode(person);
	}

	public void removePerson(Person person) {
		removeNode(person);
	}

	public void addRelation(Person p1, Person p2, Relation rel) {
		addEdge(p1, p2, rel);
	}

	public void removeRelation(Person p1, Person p2, Relation rel) { 
		Bag aux = new Bag();
		Edge link = null;
		aux.addAll(getEdgesIn(p1));
		for (Object o : aux) {// un while por favor
			Edge e = (Edge) o;
			if (e.getOtherNode(p1).equals(p2) && e.getInfo().equals(rel)) {
				link = e;
			}
		}
		if (rel != null) {
			removeEdge(link);
		}
	}

	private void reset(boolean directed) {
		network.reset(directed);
	}

	public Network getNetwork() {
		return network;
	}

	private Edge[][][] getMultigraphAdjacencyMatrix() {
		return network.getMultigraphAdjacencyMatrix();
	}

	private void addNode(Object node) {
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

	private Object removeNode(Object node) {
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

	public Bag getAllNodes() {
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
