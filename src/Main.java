import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		Scanner prompt = new Scanner(new FileReader("./src/teste.txt"));
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

		ArrayList<String[]> guardarPalavras = new ArrayList<>();

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

		System.out.println("Entrada digitada está no arquivo .txt:");
		System.out.println("Olhe o arquivo");

		System.out.println();
		System.out.println("Saída:");

		String auxCadeia;
		int indice = 0;

		int origem = 1;
		int destino = 1;

		int numToken = 0;

		while (prompt.hasNextLine()) {
			entradaUsuario = prompt.nextLine();
			indice = 0;
			origem = 1;
			destino = 1;
			while (indice < entradaUsuario.length()) {

				caractere = entradaUsuario.charAt(indice);
				origem = destino;

				if (caractere == ' ' | caractere == '\n') {

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

					String vetor[] = { auxCadeia, "OUTRO" };
					guardarPalavras.add(vetor);
					System.out.println("VALOR: " + auxCadeia + " CLASSE: OUTRO CARACTERE (TOKEN " + numToken + ")");

					numToken++;

				} else if (caractere == ':') {

					String vetor[] = { Character.toString(caractere), "OUTRO" };
					guardarPalavras.add(vetor);
					System.out.println("VALOR: " + caractere + " CLASSE: OUTRO CARACTERE (TOKEN " + numToken + ")");
					numToken++;
					indice++;
					destino = 4;

					if (indice < entradaUsuario.length()) {
						caractere = entradaUsuario.charAt(indice);
						if (caractere == '=') {
							String vetor2[] = { Character.toString(caractere), "OUTRO" };
							guardarPalavras.add(vetor2);
							System.out.println(
									"VALOR: " + caractere + " CLASSE: OUTRO CARACTERE (TOKEN " + numToken + ")");
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

					String vetor[] = { auxCadeia, "NÚMERO" };
					guardarPalavras.add(vetor);
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
						String vetor[] = { auxCadeia, "PALAVRA RESERVADA" };
						guardarPalavras.add(vetor);
						System.out
								.println("VALOR: " + auxCadeia + " CLASSE: PALAVRA RESERVADA (TOKEN " + numToken + ")");
					} else {
						String vetor[] = { auxCadeia, "IDENTIFICADOR" };
						guardarPalavras.add(vetor);
						System.out.println("VALOR: " + auxCadeia + " CLASSE: IDENTIFICADOR (TOKEN " + numToken + ")");
					}
					numToken++;

				} else if (outrosCaracteres.contains(caractere)) {
					caractere = entradaUsuario.charAt(indice);

					String vetor[] = { Character.toString(caractere), "OUTRO" };
					guardarPalavras.add(vetor);
					System.out.println("VALOR: " + caractere + " CLASSE: OUTRO CARACTERE (TOKEN " + numToken + ")");

					numToken++;
					indice++;
				} else {
					System.out.println("Erro, caractere não reconhecido: " + caractere);
					System.exit(1);
				}

			}
		}

		System.out.println();
		System.out.println();
		System.out.println("Análise sintática:");

		// Analisador sintático

//		for (int i = 0; i < guardarPalavras.size(); i++) {
//			System.out.println(guardarPalavras.get(i)[0] + " " + guardarPalavras.get(i)[1]);
//		}

		Sintatico analisadorSintatico = new Sintatico(guardarPalavras);
		try {
			analisadorSintatico.programa();
			System.out.println("Tudo certo");
		} catch (Error err) {
			System.out.println(err);
		}
	}

}
