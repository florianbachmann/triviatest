package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;

public class Game {
    ArrayList playerNames = new ArrayList();
    int[] playerGameboardPositions = new int[6];
    int[] playerPurses  = new int[6];
    boolean[] playerInPenaltyBox  = new boolean[6];
    
    LinkedList questionsPop = new LinkedList();
    LinkedList questionsScience = new LinkedList();
    LinkedList questionsSports = new LinkedList();
    LinkedList questionsRock = new LinkedList();
    
    int currentPlayer = 0;
    boolean isCurrentPlayerGettingOutOfPenaltyBox;
    
    public Game() {
    	initializeGameQuestions();
    }

	private void initializeGameQuestions() {
		for (int i = 0; i < 50; i++) {
			questionsPop.addLast("Pop Question " + i);
			questionsScience.addLast(("Science Question " + i));
			questionsSports.addLast(("Sports Question " + i));
			questionsRock.addLast("Rock Question " +i);
    	}
    	System.out.println(">> Game-Vorbereitungen fertig");
	}


	public boolean isPlayable() {
		return (howManyPlayers() >= 2);
	}

	public boolean addPlayer(String playerName) {
	    playerNames.add(playerName);
	    playerGameboardPositions[howManyPlayers()] = 0;
	    playerPurses[howManyPlayers()] = 0;
	    playerInPenaltyBox[howManyPlayers()] = false;
	    
	    System.out.println(playerName + " was added");
	    System.out.println("They are player number " + playerNames.size());
		return true;
	}
	
	//FIXME: 
	public int howManyPlayers() {
		return playerNames.size();
	}

	public void movePlayerToNewBoardPositionIfNotInPenaltyBoxThenTryToGetOutAndAskQuestionElseAskQuestion(int rollResult) {
		System.out.println("\n"+playerNames.get(currentPlayer) + " is the current player");
		System.out.println("They have rolled a " + rollResult);
		
		if (playerInPenaltyBox[currentPlayer]) {
			if (rollResult % 2 != 0) {
				//der Fall, der prüft ob ein Spieler aus der Penalty-Box rauskommt
				//Spieler kommt nur raus, wenn er eine ungerade Zahl würfelt
				isCurrentPlayerGettingOutOfPenaltyBox = true;
				
				System.out.println(playerNames.get(currentPlayer) + " is getting out of the penalty box");
				
				movePlayerToNewBoardPosition(rollResult);
				askQuestion();
			} else {
				System.out.println(playerNames.get(currentPlayer) + " is not getting out of the penalty box");
				isCurrentPlayerGettingOutOfPenaltyBox = false;
			}
			
		} else {
			movePlayerToNewBoardPosition(rollResult);
			askQuestion();
		}
		
	}


	private void movePlayerToNewBoardPosition(int rollResult) {
		playerGameboardPositions[currentPlayer] = playerGameboardPositions[currentPlayer] + rollResult;
		if (playerGameboardPositions[currentPlayer] > 11) playerGameboardPositions[currentPlayer] = playerGameboardPositions[currentPlayer] - 12;
		
		System.out.println(playerNames.get(currentPlayer) 
				+ "'s new location is " 
				+ playerGameboardPositions[currentPlayer]);
	}

	private void askQuestion() {
		String currentCategory = determineCurrentCategoryDependingOnGameboardPosition();
		
		System.out.println("The category is " + currentCategory);

		if (currentCategory == "Pop")
			System.out.println(questionsPop.removeFirst());
		if (currentCategory == "Science")
			System.out.println(questionsScience.removeFirst());
		if (currentCategory == "Sports")
			System.out.println(questionsSports.removeFirst());
		if (currentCategory == "Rock")
			System.out.println(questionsRock.removeFirst());		
	}
	
	
	private String determineCurrentCategoryDependingOnGameboardPosition() {
		if (playerGameboardPositions[currentPlayer] == 0) return "Pop";
		if (playerGameboardPositions[currentPlayer] == 4) return "Pop";
		if (playerGameboardPositions[currentPlayer] == 8) return "Pop";
		if (playerGameboardPositions[currentPlayer] == 1) return "Science";
		if (playerGameboardPositions[currentPlayer] == 5) return "Science";
		if (playerGameboardPositions[currentPlayer] == 9) return "Science";
		if (playerGameboardPositions[currentPlayer] == 2) return "Sports";
		if (playerGameboardPositions[currentPlayer] == 6) return "Sports";
		if (playerGameboardPositions[currentPlayer] == 10) return "Sports";
		return "Rock";
	}

	public boolean answerIsCorrectPlayerGetsGoldIfGettingOutOfPenaltyOrNotInPenaltyElseNothingAndGameStateChangesToNextPlayer() {
		if (playerInPenaltyBox[currentPlayer]){
			if (isCurrentPlayerGettingOutOfPenaltyBox) {
				answerIsCorrectGetGold();
				
				//FIXME: spieler muss vermutlich auch wieder aus penalty box geholt werden
				//inPenaltyBox[currentPlayer] = false;
				boolean notAWinner = isNotAWinner();
				nextPlayer();
				return notAWinner;
			} else {
				nextPlayer();
				return true;
			}
			
		} else {
			//FIXME: need to be merged at some point with answerIsCorrectGetGold()
			answerIsCorrentGetGold();
			
			boolean notAWinner = isNotAWinner();
			nextPlayer();
			
			return notAWinner;
		}
	}


	private void answerIsCorrentGetGold() {
		System.out.println("Answer was corrent!!!!");
		playerPurses[currentPlayer]++;
		System.out.println(playerNames.get(currentPlayer) 
				+ " now has "
				+ playerPurses[currentPlayer]
				+ " Gold Coins.");
	}


	private void answerIsCorrectGetGold() {
		System.out.println("Answer was correct!!!!");
		playerPurses[currentPlayer]++;
		System.out.println(playerNames.get(currentPlayer) 
				+ " now has "
				+ playerPurses[currentPlayer]
				+ " Gold Coins.");
	}


	private void nextPlayer() {
		currentPlayer++;
		if (currentPlayer == playerNames.size()) currentPlayer = 0;
	}
	
	public boolean answerIsWrongPlayerIsMovedToPenaltyBox(){
		System.out.println("Question was incorrectly answered");
		System.out.println(playerNames.get(currentPlayer)+ " was sent to the penalty box");
		playerInPenaltyBox[currentPlayer] = true;
		
		nextPlayer();
		return true;
	}
	
	/*
	 * ist invertiert wegen der allumfassenden while schleife, die true braucht um durchlaufen zu können
	 */
	private boolean isNotAWinner() {
		return !(playerPurses[currentPlayer] == 6);
	}
}
