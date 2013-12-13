package com.adaptionsoft.games.trivia;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import com.adaptionsoft.games.uglytrivia.Player;

public class CheckPlayerNames {
	ArrayList<Player> playerNames = new ArrayList<Player>();
	
	
	@Test
	public void getPlayerForName() {
		

	    playerNames.add(new Player("Chet"));
	    playerNames.add(new Player("Sue"));
	    playerNames.add(new Player("Pat"));
	    
	    
	    Player player = playerNames.get(0);
	    
	    assertEquals(player.getName(), "Chet");
	    
	    
	    assertEquals(player.toString(), "Chet");
	    
	}
}
