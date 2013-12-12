package com.adaptionsoft.games.trivia;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

import com.adaptionsoft.games.trivia.runner.GameRunner;

public class SomeTest {
	String masterout = "masterout.txt";
	
	@Before
	public void setup() throws IOException {
		
		//GameRunner.runGame(GameRunner.RANDOM_SEED, masterout);
		//String testContentActual = new String(Files.readAllBytes(Paths.get(masterout)));
	
	}
	
	@Test
	public void true_is_true() throws Exception {
		assertTrue(true);
	}
	
	@Test
	public void compareCurrentRunWithGM() throws Exception {
		String fileContentExpected = new String(Files.readAllBytes(Paths.get(masterout)));
	
		String testOutput = "masteroutAsTest.txt";
		GameRunner.runGame(GameRunner.RANDOM_SEED, testOutput);
		String testContentActual = new String(Files.readAllBytes(Paths.get(testOutput)));
	
		assertEquals(fileContentExpected, testContentActual);
	}
	
	
}
