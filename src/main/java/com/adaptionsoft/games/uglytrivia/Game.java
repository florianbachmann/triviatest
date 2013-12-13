package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;

public class Game {
    private static final String CATEGORY_ROCK = "Rock";
	private static final String CATEGORY_SPORTS = "Sports";
	private static final String CATEGORY_POP = "Pop";
	private static final String CATEGORY_SCIENCE = "Science";
	
	ArrayList<Player> allPlayers = new ArrayList<Player>();
    int currentPlayerPosInArray = 0;
    Player currentPlayer = null;
    
    
    LinkedList questionsPop = new LinkedList();
    LinkedList questionsScience = new LinkedList();
    LinkedList questionsSports = new LinkedList();
    LinkedList questionsRock = new LinkedList();
    

    
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
		Player p = new Player(playerName);
	    allPlayers.add(p);
	    
	    System.out.println("They are player number " + allPlayers.size());
		return true;
	}
	
	//FIXME: 
	public int howManyPlayers() {
		return allPlayers.size();
	}

	public void movePlayerToNewBoardPositionIfNotInPenaltyBoxThenTryToGetOutAndAskQuestionElseAskQuestion(int rollResult) {
		
		System.out.println("\n"+currentPlayer + " is the current player");
		System.out.println("They have rolled a " + rollResult);
		
		if (currentPlayer.isPenaltyBox()) {
			if (rollResult % 2 != 0) {
				//der Fall, der prüft ob ein Spieler aus der Penalty-Box rauskommt
				//Spieler kommt nur raus, wenn er eine ungerade Zahl würfelt
				currentPlayer.setGettingOutOfPenaltyBox(true);
				System.out.println(currentPlayer + " is getting out of the penalty box");
				
				movePlayerToNewBoardPosition(rollResult);
				askQuestion();
			} else {
				System.out.println(currentPlayer + " is not getting out of the penalty box");
				currentPlayer.setGettingOutOfPenaltyBox(false);
				
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
		int currentPos = currentPlayer.getGameboardPositions();
		
		if (currentPos == 0) return CATEGORY_POP;
		if (currentPos == 4) return CATEGORY_POP;
		if (currentPos == 8) return CATEGORY_POP;
		if (currentPos == 1) return CATEGORY_SCIENCE;
		if (currentPos == 5) return CATEGORY_SCIENCE;
		if (currentPos == 9) return CATEGORY_SCIENCE;
		if (currentPos == 2) return CATEGORY_SPORTS;
		if (currentPos == 6) return CATEGORY_SPORTS;
		if (currentPos == 10) return CATEGORY_SPORTS;
		return CATEGORY_ROCK;
	}

	public boolean answerIsCorrectPlayerGetsGoldIfGettingOutOfPenaltyOrNotInPenaltyElseNothingAndGameStateChangesToNextPlayer() {
		if (currentPlayer.isPenaltyBox()){
			if (currentPlayer.isGettingOutOfPenaltyBox()) {
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
		currentPlayer.addCoin();
		System.out.println("Answer was corrent!!!!");
		System.out.println(currentPlayer 
				+ " now has "
				+ currentPlayer.getPurses()
				+ " Gold Coins.");
	}


	private void answerIsCorrectGetGold() {
		currentPlayer.addCoin();
		System.out.println("Answer was correct!!!!");
		System.out.println(currentPlayer
				+ " now has "
				+ currentPlayer.getPurses()
				+ " Gold Coins.");
	}


	

	private void movePlayerToNewBoardPosition(int rollResult) {
		int newPosition = currentPlayer.getGameboardPositions() + rollResult;
		int maxPosition = 12;
		newPosition = circularMove(newPosition, maxPosition);
		
		currentPlayer.setGameboardPositions(newPosition);
		
		System.out.println(currentPlayer 
				+ "'s new location is " 
				+ currentPlayer.getGameboardPositions());
	}
	
	private void nextPlayer() {
		int maxPlayers = allPlayers.size();

		//allPlayers (0 "Pat", 1 "Sue",2 "Chat")
		currentPlayerPosInArray = circularMove(currentPlayerPosInArray + 1, maxPlayers);
		currentPlayer = allPlayers.get(currentPlayerPosInArray);
	}
	
	private int circularMove(int newValue, int maxValue) {
		return newValue % maxValue;
	}
	
	public boolean answerIsWrongPlayerIsMovedToPenaltyBox(){
		System.out.println("Question was incorrectly answered");
		System.out.println(currentPlayer+ " was sent to the penalty box");
		currentPlayer.setPenaltyBox(true);
		nextPlayer();
		return true;
	}
	
	/*
	 * ist invertiert wegen der allumfassenden while schleife, die true braucht um durchlaufen zu können
	 */
	private boolean isNotAWinner() {
		return !(currentPlayer.getPurses() == 6);
	}
}
