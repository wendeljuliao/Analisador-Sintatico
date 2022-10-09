import java.util.ArrayList;
import java.util.HashMap;

class Grafo {
	HashMap<Integer, ArrayList<Edge>> map = new HashMap<>();

	public void addEdge(int origem, int destino, char peso) {
		if (!hasVertex(origem)) {
			addVertex(origem);
		}

		if (!hasVertex(destino)) {
			addVertex(destino);
		}

		map.get(origem).add(new Edge(origem, destino, peso));

	}

	public boolean hasVertex(int no) {

		if (map.containsKey(no)) {

			return true;
		}
		return false;
	}

	public void addVertex(int vertex) {
		map.put(vertex, new ArrayList<Edge>());
	}

	public boolean hasEdge(int origem, int destino, char peso) {
		if (hasVertex(origem)) {
			Edge edge = new Edge(origem, destino, peso);

			for (int i = 0; i < map.get(origem).size(); i++) {
				if (map.get(origem).get(i).equals(edge)) {
					return true;
				}
			}

			return false;

		}
		return false;
	}

	public ArrayList<Edge> getEdges(int vertex) {
		return map.get(vertex);
	}

}