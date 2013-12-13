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
		Player player = currentPlayer;
		
		System.out.println("\n"+player + " is the current player");
		System.out.println("They have rolled a " + rollResult);
		
		if (player.isPenaltyBox()) {
			if (rollResult % 2 != 0) {
				//der Fall, der prüft ob ein Spieler aus der Penalty-Box rauskommt
				//Spieler kommt nur raus, wenn er eine ungerade Zahl würfelt
				player.setGettingOutOfPenaltyBox(true);
				System.out.println(player + " is getting out of the penalty box");
				
				movePlayerToNewBoardPosition(rollResult);
				askQuestion();
			} else {
				System.out.println(player + " is not getting out of the penalty box");
				player.setGettingOutOfPenaltyBox(false);
				
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
		Player player = currentPlayer;
		int currentPos = player.getGameboardPositions();
		
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
		Player player = currentPlayer;
		if (player.isPenaltyBox()){
			if (player.isGettingOutOfPenaltyBox()) {
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
		Player player = currentPlayer;
		player.addCoin();
		System.out.println("Answer was corrent!!!!");
		System.out.println(player 
				+ " now has "
				+ player.getPurses()
				+ " Gold Coins.");
	}


	private void answerIsCorrectGetGold() {
		Player player = currentPlayer;
		player.addCoin();
		System.out.println("Answer was correct!!!!");
		System.out.println(player
				+ " now has "
				+ player.getPurses()
				+ " Gold Coins.");
	}


	

	private void movePlayerToNewBoardPosition(int rollResult) {
		Player player = currentPlayer;
		
		int newPosition = player.getGameboardPositions() + rollResult;
		int maxPosition = 12;
		newPosition = circularMove(newPosition, maxPosition);
		
		player.setGameboardPositions(newPosition);
		
		System.out.println(player 
				+ "'s new location is " 
				+ player.getGameboardPositions());
	}
	
	private void nextPlayer() {
		int maxPlayers = allPlayers.size();

		currentPlayerPosInArray = circularMove(currentPlayerPosInArray + 1, maxPlayers);
		currentPlayer = allPlayers.get(currentPlayerPosInArray);
	}
	
	private int circularMove(int newValue, int maxValue) {
		return newValue % maxValue;
	}
	
	public boolean answerIsWrongPlayerIsMovedToPenaltyBox(){
		Player player = currentPlayer;
		System.out.println("Question was incorrectly answered");
		System.out.println(player+ " was sent to the penalty box");
		player.setPenaltyBox(true);
		nextPlayer();
		return true;
	}
	
	/*
	 * ist invertiert wegen der allumfassenden while schleife, die true braucht um durchlaufen zu können
	 */
	private boolean isNotAWinner() {
		Player player = currentPlayer;
		return !(player.getPurses() == 6);
	}
}
