package assgn3;

public class Edge implements Comparable<Edge> {
	
	private int first;
	private int second;
	private int weight;

	public Edge(int first, int second, int weight) {
		this.first = first;
		this.second = second;
		this.weight = weight;
	}
	
	public int getFirst() {
		return first;
	}
	public int getSecond() {
		return second;
	}
	public int getWeight() {
		return weight;
	}
	
	public int compareTo(Edge e) {
		return this.weight - e.getWeight();
	}
	
	@Override
	public String toString() {
		return "("+first+", "+second+", "+weight+")";
	}

}
