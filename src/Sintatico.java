import java.util.ArrayList;

public class Sintatico {
	ArrayList<String[]> palavras;

	Sintatico(ArrayList<String[]> palavras) {
		this.palavras = palavras;
	}

	void match(String t) {
		if (palavras.get(0)[0].equals(t)) {
			palavras.remove(0);
		} else {
			throw new Error("Erro!");
		}
	}

	void matchClasse(String t) {
		if (palavras.get(0)[1].equals(t)) {
			palavras.remove(0);
		} else {
			throw new Error("Erro!");
		}
	}

	void programa() {
		eSegComando();
		match("END");
	}

	void eSegComando() {
		eComando();
		eLinhaSegComando();
	}

	void eLinhaSegComando() {
		if (palavras.get(0)[0].equals(";")) {
			match(";");
			eComando();
			eLinhaSegComando();
		}

	}

	void eComando() {
		if (palavras.get(0)[0].equals("LET")) {
			match("LET");
			matchClasse("IDENTIFICADOR");
			match(":");
			match("=");
			eExpressao();
		} else if (palavras.get(0)[0].equals("GO")) {
			match("GO");
			match("TO");
			// FAZER AINDA O DESVIO
		} else if (palavras.get(0)[0].equals("READ")) {
			match("READ");
			eListaIdentificadores();

		} else if (palavras.get(0)[0].equals("PRINT")) {
			match("PRINT");
			eListaExpressoes();
		} else if (palavras.get(0)[0].equals("IF")) {
			match("IF");
			eExpressao();

			if (palavras.get(0)[0].equals(">")) {
				match(">");
			} else if (palavras.get(0)[0].equals("<")) {
				match("<");
			} else {
				match("=");
			}
			eExpressao();
			match("THEN");
			eComando();
			match("ELSE");
			eComando();
		}
		eLinhaComando();

	}

	void eLinhaComando() {
		if (palavras.get(0)[1].equals("IDENTIFICADOR")) {
			matchClasse("IDENTIFICADOR");
			match(":");
			eLinhaComando();

		}
	}

	void eExpressao() {
		eTermo();
		eLinhaExpressao();
	}

	void eLinhaExpressao() {
		if (palavras.get(0)[0].equals("+")) {
			match("+");
			eTermo();
			eLinhaExpressao();

		} else if (palavras.get(0)[0].equals("-")) {
			match("-");
			eTermo();
			eLinhaExpressao();
		}
	}

	void eTermo() {

		if (palavras.get(0)[1] == "IDENTIFICADOR") {
			matchClasse("IDENTIFICADOR");
		} else if (palavras.get(0)[1] == "NÚMERO") {
			matchClasse("NÚMERO");
		} else if (palavras.get(0)[0] == "(") {
			match("(");
			eExpressao();
			// if (palavras.get(0)[0] == ")") {
			match(")");
			// }
		}
		eLinhaTermo();

	}

	void eLinhaTermo() {

		if (palavras.get(0)[0].equals("*")) {
			match("*");

			if (palavras.get(0)[1] == "IDENTIFICADOR") {
				matchClasse("IDENTIFICADOR");
			} else if (palavras.get(0)[1] == "NÚMERO") {
				matchClasse("NÚMERO");
			} else {
				match("(");
				eExpressao();
				// if (palavras.get(0)[0] == ")") {
				match(")");
				// }
			}
			eLinhaTermo();

		} else if (palavras.get(0)[0].equals("/")) {
			match("/");
			if (palavras.get(0)[1] == "IDENTIFICADOR") {
				matchClasse("IDENTIFICADOR");
			} else if (palavras.get(0)[1] == "NÚMERO") {
				matchClasse("NÚMERO");
			} else {
				match("(");
				eExpressao();
				// if (palavras.get(0)[0] == ")") {
				match(")");
				// }
			}
			eLinhaTermo();
		}
	}

	void eListaRotulos() {
		if (palavras.get(0)[1] == "IDENTIFICADOR") {
			matchClasse("IDENTIFICADOR");
		}
		eLinhaListaRotulos();

	}

	void eLinhaListaRotulos() {
		if (palavras.get(0)[0].equals(",")) {
			match(",");
			matchClasse("IDENTIFICADOR");
			eLinhaListaRotulos();
		}
	}

	void eListaIdentificadores() {
		eLinhaListaIdentificadores();
	}

	void eLinhaListaIdentificadores() {
		if (palavras.get(0)[1] == "IDENTIFICADOR") {
			matchClasse("IDENTIFICADOR");
			match(",");
			eLinhaListaIdentificadores();
		}
	}

	void eListaExpressoes() {
		eLinhaListaExpressoes();
	}

	void eLinhaListaExpressoes() {
		eExpressao();
		if (palavras.get(0)[0].equals(",")) {
			match(",");
			eLinhaExpressao();
		}
	}

}
