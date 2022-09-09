import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

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

public class Main {

	public static void main(String[] args) {
		Scanner prompt = new Scanner(System.in);
		String entradaUsuario;
		Grafo grafo = new Grafo();

		ArrayList<Character> identificadores = new ArrayList<Character>();
		ArrayList<Character> letras = new ArrayList<Character>();
		ArrayList<Character> numeros = new ArrayList<Character>();
		ArrayList<String> palavrasReservadas = new ArrayList<String>(
				Arrays.asList("END", "LET", "GO", "OF", "READ", "PRINT", "IF", "THEN", "ELSE", "TO"));
		ArrayList<Character> brancoseLinhas = new ArrayList<Character>(Arrays.asList(' ', '\n'));
		ArrayList<Character> outrosCaracteres = new ArrayList<Character>(
				Arrays.asList('+', '-', '*', '/', ':', '=', '>', '<', ',', '.', ';', '(', ')'));

		char caractere;

		// 1. INICIALIZAÇÃO CARACTERES

		// TABELA ASCII A-Z
		for (int i = 65; i <= 90; i++) {
			caractere = (char) i;
			identificadores.add(caractere);
			letras.add(caractere);
		}
		// TABELA ASCII a-z
//		for (int i = 97; i <= 122; i++) {
//			caractere = (char) i;
//			identificadores.add(caractere);
//			letras.add(caractere);
//		}	
		// TABELA ASCII 0-9
		for (int i = 48; i <= 57; i++) {
			caractere = (char) i;
			identificadores.add(caractere);
			numeros.add(caractere);
		}

		// 2. INICIALIZAÇÃO GRAFO
		for (Character identificador : identificadores) {
			grafo.addEdge(1, 2, identificador);
			grafo.addEdge(2, 2, identificador);
		}

		for (Character numero : numeros) {
			grafo.addEdge(1, 3, numero);
			grafo.addEdge(3, 3, numero);
		}

		grafo.addEdge(1, 4, ':');
		grafo.addEdge(4, 5, '=');

		grafo.addEdge(1, 6, '%');

		for (Character outro : outrosCaracteres) {
			if (outro != ':') {
				grafo.addEdge(1, 7, outro);
			}
		}
		// Colocando vertices 2 até o 7 de volta pro 1
//		for (int i = 2; i <= 7; i++) {
//			for (Character carac : brancoseLinhas) {
//
//				grafo.addEdge(i, 1, carac);
//				grafo.addEdge(i, 1, carac);
//			}
//		}

		System.out.println("Digite a entrada");
		entradaUsuario = prompt.nextLine();
		System.out.println("Entrada digitada: " + entradaUsuario);
		System.out.println();
		System.out.println("Saída:");

		String auxCadeia;
		int indice = 0;

		int origem = 1;
		int destino = 1;

		int numToken = 0;

		while (indice < entradaUsuario.length()) {

			caractere = entradaUsuario.charAt(indice);

			if (caractere == ' ' | caractere == '\n') {

				origem = destino;
				indice++;
			} else if (caractere == '%') {
				auxCadeia = "";

				destino = 6;

				while (indice < entradaUsuario.length()) {
					caractere = entradaUsuario.charAt(indice);
					if (caractere != '\n') {
						auxCadeia += caractere;

						origem = destino;
						indice++;

					} else {
						destino = 1;
						break;
					}
				}

				System.out.println("VALOR: " + auxCadeia + " CLASSE: OUTRO CARACTERE (TOKEN " + numToken + ")");

				numToken++;

			} else if (caractere == ':') {
				
				System.out.println("VALOR: " + caractere + " CLASSE: OUTRO CARACTERE (TOKEN " + numToken + ")");
				numToken++;
				indice++;
				destino = 4;
				
				if (indice < entradaUsuario.length()) {
					caractere = entradaUsuario.charAt(indice);
					if (caractere == '=') {
						System.out.println("VALOR: " + caractere + " CLASSE: OUTRO CARACTERE (TOKEN " + numToken + ")");
						numToken++;
						indice++;
						destino = 5;
					}
				}
				
				destino = 1;
				

			} else if (numeros.contains(caractere)) {

				auxCadeia = "";

				destino = 3;

				while (indice < entradaUsuario.length()) {
					caractere = entradaUsuario.charAt(indice);
					if (grafo.hasEdge(origem, destino, caractere)) {
						auxCadeia += caractere;

						origem = destino;
						indice++;

					} else {
						destino = 1;
						break;
					}
				}

				System.out.println("VALOR: " + auxCadeia + " CLASSE: NÚMERO (TOKEN " + numToken + ")");

				numToken++;
			} else if (letras.contains(caractere)) {
				auxCadeia = "";

				destino = 2;

				while (indice < entradaUsuario.length()) {
					caractere = entradaUsuario.charAt(indice);
					if (grafo.hasEdge(origem, destino, caractere)) {
						auxCadeia += caractere;

						origem = destino;
						indice++;

					} else {
						destino = 1;
						break;
					}
				}
				// System.out.println(auxCadeia);

				if (palavrasReservadas.contains(auxCadeia)) {
					System.out.println("VALOR: " + auxCadeia + " CLASSE: PALAVRA RESERVADA (TOKEN " + numToken + ")");
				} else {
					System.out.println("VALOR: " + auxCadeia + " CLASSE: IDENTIFICADOR (TOKEN " + numToken + ")");
				}
				numToken++;

			} else if (outrosCaracteres.contains(caractere)){
				caractere = entradaUsuario.charAt(indice);
				System.out.println("VALOR: " + caractere + " CLASSE: OUTRO CARACTERE (TOKEN " + numToken + ")");

				numToken++;
				indice++;
			} else {
				System.out.println("Erro, caractere não reconhecido: " + caractere);
				System.exit(1);
			}

		}

	}

}
