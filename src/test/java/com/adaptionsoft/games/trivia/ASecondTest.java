package com.adaptionsoft.games.trivia;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

import com.adaptionsoft.games.trivia.runner.GameRunner;

public class ASecondTest {
	int numberOfTestsToRun = 12;

	@Before
	public void setup() throws IOException {
		
		for (int seed = 0; seed < numberOfTestsToRun; seed++) {
			long seedToUse = GameRunner.RANDOM_SEED + seed;
			String masterOut = "seed_"+seedToUse+".txt";
			//GameRunner.runGame(seedToUse,masterOut);
		}
	}
	
	
	@Test
	public void compareCurrentRunWithGM() throws Exception {
		
		
		for (int seed = 0; seed < numberOfTestsToRun; seed++) {
			long seedToUse = GameRunner.RANDOM_SEED + seed;
			String masterOut = "seed_"+seedToUse+".txt";
			
			String fileContentExpected = new String(Files.readAllBytes(Paths.get(masterOut)));
			
			String testOut = "seed_"+seedToUse+"_test.txt";
			GameRunner.runGame(seedToUse, testOut);
			String testContentActual = new String(Files.readAllBytes(Paths.get(testOut)));
		
			assertEquals(fileContentExpected, testContentActual);
		}
		
	}
}
