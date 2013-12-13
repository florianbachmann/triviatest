package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;

public class Game {
    private static final String CATEGORY_ROCK = "Rock";
	private static final String CATEGORY_SPORTS = "Sports";
	private static final String CATEGORY_POP = "Pop";
	private static final String CATEGORY_SCIENCE = "Science";
	
	
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



	private void askQuestion() {
		String currentCategory = determineCurrentCategoryDependingOnGameboardPosition();
		
		System.out.println("The category is " + currentCategory);

		if (currentCategory == CATEGORY_POP) {
			System.out.println(questionsPop.removeFirst());
		}
		else if (currentCategory == CATEGORY_SCIENCE) {
			System.out.println(questionsScience.removeFirst());
		}
		else if (currentCategory == CATEGORY_SPORTS) {
			System.out.println(questionsSports.removeFirst());
		}
		else if (currentCategory == CATEGORY_ROCK) {
			System.out.println(questionsRock.removeFirst());	
		}	
	}
	
	
	private String determineCurrentCategoryDependingOnGameboardPosition() {
		if (playerGameboardPositions[currentPlayer] == 0) return CATEGORY_POP;
		if (playerGameboardPositions[currentPlayer] == 4) return CATEGORY_POP;
		if (playerGameboardPositions[currentPlayer] == 8) return CATEGORY_POP;
		if (playerGameboardPositions[currentPlayer] == 1) return CATEGORY_SCIENCE;
		if (playerGameboardPositions[currentPlayer] == 5) return CATEGORY_SCIENCE;
		if (playerGameboardPositions[currentPlayer] == 9) return CATEGORY_SCIENCE;
		if (playerGameboardPositions[currentPlayer] == 2) return CATEGORY_SPORTS;
		if (playerGameboardPositions[currentPlayer] == 6) return CATEGORY_SPORTS;
		if (playerGameboardPositions[currentPlayer] == 10) return CATEGORY_SPORTS;
		return CATEGORY_ROCK;
	}

	public boolean answerIsCorrectPlayerGetsGoldIfGettingOutOfPenaltyOrNotInPenaltyElseNothingAndGameStateChangesToNextPlayer() {
		/*
		if (playerInPenaltyBox[currentPlayer] && !isCurrentPlayerGettingOutOfPenaltyBox){	
			nextPlayer();
			return true;
		} else {
			answerIsCorrentGetGold();
			boolean notAWinner = isNotAWinner();
			nextPlayer();
			return notAWinner;
		}
		*/
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


	

	private void movePlayerToNewBoardPosition(int rollResult) {
		int newPosition = playerGameboardPositions[currentPlayer] + rollResult;
		int maxPosition = 12;
		playerGameboardPositions[currentPlayer] = circularMove(newPosition, maxPosition);
		
		System.out.println(playerNames.get(currentPlayer) 
				+ "'s new location is " 
				+ playerGameboardPositions[currentPlayer]);
	}
	
	private void nextPlayer() {
		currentPlayer = circularMove(currentPlayer + 1, playerNames.size());
	}
	
	private int circularMove(int newValue, int maxValue) {
		return newValue % maxValue;
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
