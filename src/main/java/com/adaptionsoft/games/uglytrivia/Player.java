package com.adaptionsoft.games.uglytrivia;

public class Player {
    String name;
	private int gameboardPositions;
	private int purses;
	private boolean penaltyBox;
	private boolean isGettingOutOfPenaltyBox;
    
    public String getName() {
		return name;
	}

	public Player(String playerName) {
		this.name = playerName;
		
	    this.setGameboardPositions(0);
	    this.setPurses(0);
	    this.setPenaltyBox(false);
	    System.out.println(playerName + " was added");
	}

	@Override
	public String toString() {
		return name;
	}

	public int getGameboardPositions() {
		return gameboardPositions;
	}

	public void setGameboardPositions(int gameboardPositions) {
		this.gameboardPositions = gameboardPositions;
	}

	public int getPurses() {
		return purses;
	}

	public void setPurses(int purses) {
		this.purses = purses;
	}

	public boolean isPenaltyBox() {
		return penaltyBox;
	}

	public void setPenaltyBox(boolean penaltyBox) {
		this.penaltyBox = penaltyBox;
	}

	public boolean isGettingOutOfPenaltyBox() {
		return isGettingOutOfPenaltyBox;
	}

	public void setGettingOutOfPenaltyBox(boolean isGettingOutOfPenaltyBox) {
		this.isGettingOutOfPenaltyBox = isGettingOutOfPenaltyBox;
	}

	public void addCoin() {
		purses++;
	}

}
