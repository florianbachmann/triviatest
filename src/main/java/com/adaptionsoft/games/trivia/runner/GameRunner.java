
package com.adaptionsoft.games.trivia.runner;
import java.util.Random;

import com.adaptionsoft.games.uglytrivia.Game;

/*
 * Wieviele Spieler: aktuell Spielen 3 Spieler mit, maximal sind 6 Spieler erlaubt
 * 
 * Welche Regeln:
 * Das Spiel wird mit genau 50 Fragen pro Kategorie initialisiert. Es gibt 4 Kategorien (Pop, Science, Sport & Rock).
 * 
 * Spieler werden der Reihe nach hinzugefügt
 * Der erste Spieler beginnt (currentPlayer = 0)
 * 
 * Danach läuft das Spiel solange bis ein Spieler durch das korrekte beantworten von Zufallszahlen 6 Fragen korrekt hat
 * (in diesem Fall Goldmünzen, 1 Antwort gibt eine Münze).
 * Das Spiel wird gewonnen, sobald ein Spieler 6 Goldmünzen bekommt, diese Überprüfung findet nach jeder Gold-Transaktion statt
 * 
 * Der aktuelle Spieler würfelt (zwischen 1 und 6) und rückt dem Würfel-Ergebniss entsprechend viele Felder vor.
 * Wichtig, das Spielfeld besteht nur aus 12 ringförmiug angeordneten Feldern (quasi modulo)
 * Eine Frage wird aufgrund des Spieler-Platzes (places) aufgrund festdefinierter Werte ausgewählt (z.b. Platz Nummer 9 ist science).
 * Jede Frage kann nur einmal gestellt werden und wird dann aus dem Spiel genommen
 * Anmerkung: werden mehr als 50 Fragen aus einer Kategorie gestellt, wird eine NullPointerException geworfen
 * 
 * Wird die Frage korrekt beantwortet, bekommt der Spieler eine Goldmünze (danach Siegbedingung prüfen)
 * Beantwortet  der Spieler eine Frage falsch, kommt er in die penalty-Box. 
 * Die Penalty-Box kann er wieder verlassen, wenn er in der nächsten Runde eine ungerade Zahl würfelt und die neue Frage korrekt beantwortet.
 * 
 * 
 * 
 */


public class GameRunner {

	private static boolean notAWinner;

	public static void main(String[] args) {
		Game aGame = new Game();
		
		aGame.add("Chet");
		aGame.add("Pat");
		aGame.add("Sue");
		
		Random rand = new Random();
	
		do {
			
			aGame.roll(rand.nextInt(5) + 1);
			
			if (rand.nextInt(9) == 7) {
				notAWinner = aGame.wrongAnswer();
			} else {
				notAWinner = aGame.wasCorrectlyAnswered();
			}
			
			
			
		} while (notAWinner);
		
	}
}
