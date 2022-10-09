class Edge {
	int origem;
	int destino;
	char weight;

	public Edge(int origem, int destino, char weight) {
		this.origem = origem;
		this.destino = destino;
		this.weight = weight;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Edge edge = (Edge) o;
		return origem == edge.origem && destino == edge.destino && weight == edge.weight;
	}
}