package assgn3;


public class UnionFind {
	
	private int[] set;
	
	public UnionFind(int numNodes) {
		makeSet(numNodes);
	}
	
	private void makeSet(int numNodes) {
		set = new int[numNodes+1];
		for (int i = 1; i < set.length; i++)
			set[i] = -1;
	}
	
	public int find(int x) {
		int temp = set[x];
		if (temp < 0)
			return x;
		else return find(temp);
	}
	
	public void union(int i, int j) {
		int first = find(i);
		int second = find(j);
		if (set[first] <= set[second]) {//more negative i.e. bigger
			set[first] += set[second];//add the sizes together
			set[second] = first;//second root becomes parent of first root
		}
		else if (set[second] < set[first]) {
			set[second] += set[first];
			set[first] = second;
		}
	}
}


