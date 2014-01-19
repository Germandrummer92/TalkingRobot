package nlu;

/* BEISPIEL:
* Main Methode, die die Distanzberechnung testet.
* @param args Kommandozeilenparameter
*/
/* public static void main(String[] args) {
	Levenshtein L = new Levenshtein("halo" , "hello");
	System.out.println(L.getDistance()); //Output: 2
 }*/
/**
* Diese Klasse dient zur Berechnung der Levenshtein-Editier-Distanz.
* @author Daniel Draper
* @version 2.0
*/
//public da sonst die Klasse nicht sichtbar ist
	public class Levenshtein {
	
	//Initialisierung der PraefixMatrix und der Strings um diese zu speichern
	//private Attribute da bei Datenkapselung diese nur von getter Methoden aufgerufen werden sollen
	/**
	* Matrix in dem die Editierdistanzen gespeichert werden.
	*/
	private int m[][];
	/**
	* Wort 1 fuer das die Distanz berechnet werden soll.
	*/
	private String w1;
	/**
	* Wort 2 fuer das die Distanz berechnet werden soll.
	*/
	private String w2;
	
	/**
	* Konstruktor um eine Levenshtein Matrix zu erzeugen und die Editierdistanz zu berechnen.
	* @param word1 1. Wort fuer das die Distanz berechnet wird
	* @param word2 2. Wort in welches das 1. Wort "editiert" wird
	*/
	//public, da der Konstruktor auch in anderen Klassen genutzt werden koennen muss
    public Levenshtein(String word1, String word2) {
		m = new int[word1.length() + 1][word2.length() + 1];
		w1 = word1;
		w2 = word2;
		for (int i = 0; i <= w1.length(); i++) {
			m[i][0] = i;
		}
		for (int j = 1; j <= w2.length(); j++) {
			m[0][j] = j;
		}
		for (int i = 1; i <= word1.length(); i++) {
			for (int j = 1; j <= word2.length(); j++) {
				m[i][j] = min(i, j);
			}
		}
	}
	
	/**
	* Methode um das Minimum der anzuwenden Schritte um Praefix 1 in Praefix 2 zu uberfuehren herauszufinden.
	* @param i Stelle im Wort 2 die betrachtet wird
	* @param j Stelle im Wort 1 die betrachtet wird
	* @return gibt die minimalste anzahl der Schritte zurueck
	*/
	//private, da diese Funktion nur intern von der Klasse zum berechnen des Minimums verwendet wird
	private int min(int i, int j) {
		if (i == j && i == 0) {
			return 0;
		}
			int del = delete(i, j);
			int ins = insert(i, j);
			int rep = replace(i, j);
			int res;
			if (del <= ins && del <= rep) {
				res = del;
			}
			else {
				if (ins <= del && ins <= rep) {
					res = ins;
				}
				else {
					res = rep;
				}
			}
	return res;
    }

	//public, da andere Klassen auf diese Funktion zugreifen muessen 
	//koennen um die Levenshtein-Distanz zu erhalten
	/**
	* Methode um die finale Editierdistanz zurueckzugeben.
	* @return Wert der Distanz insgesamt
	*/
    public int getDistance() {
		return m[w1.length()][w2.length()];
    }

	//private, da diese Funktion nur von der Klasse selbst benoetigt wird
	/**
	* Berechnet die Kosten um das Praefix 1 durch entfernen von Buchstaben in Praefix 2 zu ueberfuehren.
	* @param i Stelle im Wort 2 die man betrachtet
	* @param j Stelle im Wort 1 die man betrachter
	* @return Kosten die die Operation entfernen verursachen wuerde
	*/
    private int delete(int i, int j) {
		//Mod. : Anlegen eines Char arrays zum besseren Ueberpruefen
		//char[] cost = {'a', 'e', 'i', 'o', 'u', 'ä', 'ö', 'ü', 'A', 'E', 'I', 'O', 'U', 'Ä', 'Ö', 'Ü'};
		
	/*	if (j < 1) {
			return Integer.MAX_VALUE;
		}*/
	/*	else {
			//Mod. : Ueberpruefen dass keine Ueberpruefung der Stirng Charaktere out of bounds liegt
			boolean run = j < w1.length()  && i < w2.length();
			if (run) {
				char cur = w1.charAt(j);
				//Mod. : Uberpruefen ob der zu untersuchende Buchstabe h ist
				if ((cur == 'h' || cur == 'H')) {
					//Mod. : Ueberpruefen ob der vorherige Buchstabe ein Vokal/Umlaut ist
					for (int x = 0; x < cost.length; x++) {
						if (w1.charAt(j - 1) == cost[x]) {
							//Mod. : Kosten sind die des vorherigen Praefixes
							return m[i][j - 1];
						}
					}
				}
			}
		}*/
		return m[i][j - 1] + 1;
    }

	//private, da diese Methode nur von der Klasse selbst benoetgigt wird
	/**
	* Berechnet die Kosten um das Praefix 1 durch einfuegen von Buchstaben in Praefix 2 zu ueberfuehren.
	* @param i Stelle im Wort 2 die man betrachtet
	* @param j Stelle im Wort 1 die man betrachter
	* @return Kosten die die Operation einfuegen verursachen wuerde
	*/
    private int insert(int i, int j) {
		//Mod. : Variable fuer das Zeichen vor der einzufuegenden Stelle und
		//Mod. :Char array um das Zeichen einfacher zu ueberpruefen
	//	char prev;
	//	char[] cost = {'a', 'e', 'i', 'o', 'u', 'ä', 'ö', 'ü', 'A', 'E', 'I', 'O', 'U', 'Ä', 'Ö', 'Ü'}; 
/*		if (i < 1) {
			return Integer.MAX_VALUE;
		}*/
	/*	else {
			//Mod. : sicherstellen das kein index groesser der Laenge des Wortes benutzt wird
			boolean run = j != 0 && j < w1.length() && i < w2.length() - 1;
			if (run) {
				//Mod. : Ueberpruefen ob der Buchstabe der eingesetzt wird h ist 
				//und ob der Buchstabe an der jetzigen Stelle ungleich h ist 
				//und ob der jetzige Buchstabe dem darauffolgen im zu erreichenden
				//Wort entspricht
				if ((w2.charAt(i) == 'h' || w2.charAt(i) == 'H') && w1.charAt(j) != 'h' 
				&& w1.charAt(j) == w2.charAt(i + 1)) {
					prev = w1.charAt(j - 1);
					//Mod. : Ueberpruefen ob der vorherige Buchstabe ein Vokal/Umlaut ist
					for (int x = 0; x < cost.length; x++) {
						if (prev == cost[x]) {
							//Mod. : Wert des vorherigen Praefixes zurueckgeben
							return m[i - 1][j];
						}
					}
				}
			}
		}*/
		return m[i - 1][j] + 1;
    }

	//private, da diese Methode nur von der Klasse selbst benoetigt wird
	/**
	* Berechnet die Kosten um das Praefix 1 durch ersetzen von Buchstaben in Praefix 2 zu ueberfuehren.
	* @param i Stelle im Wort 2 die man betrachtet
	* @param j Stelle im Wort 1 die man betrachter
	* @return Kosten die die Operation ersetzen verursachen wuerde
	*/
    private int replace(int i, int j) {
		/*if (i < 1 || j < 1) {
			return Integer.MAX_VALUE;
		}*/
			/*	if (j >= w1.length() || i >= w2.length()) {
					return m[i - 1][j - 1] + 1;
				}*/
				if (w1.charAt(i - 1) == w2.charAt(j - 1)) {
					return m[i - 1][j - 1];
				}
				else {
					return m[i - 1][j - 1] + 1;
				}
		}
	
}
				
		
    

