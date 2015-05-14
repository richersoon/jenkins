package com.padistutorial.LingoProject;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.padistutorial.LingoProject.classes.Datasource;
import com.padistutorial.LingoProject.classes.LingoGame;

@RunWith(MockitoJUnitRunner.class)
public class LingoGameTest {

	ApplicationContext ctx;

	private static ArrayList<String> strings;
	
	@Mock
	Datasource datasource;
	
	@BeforeClass
	public static void setUpGeneral() {
		strings = new ArrayList<String>();
		strings.add("tutorial");
		strings.add("padjis");
	}
	
	@Before
	public void setUp()  {
		ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		
		Mockito.when(datasource.getWordsFromPackedDataSource()).thenReturn(strings);
	}

	@Test
	public void test(){
		LingoGame lingoGame = (LingoGame) ctx.getBean("myLingoGame");
		lingoGame.startGame();
	}
	
	@Test
	public void testMockedMethod() {
		System.out.println(datasource.getWordsFromPackedDataSource());
	}
	
	//we are going to test the entire system of lingo game
	@Test
	public void testLingoGame() {
		//instantiate the lingoGame with the spring 
		LingoGame lingoGame = (LingoGame) ctx.getBean("myLingoGame");
		
		//display "the game has started"
		lingoGame.startGame();
		
		//we are going to use mockito in order to provide a fake set of words in which the playable one
		//will be choosen
		lingoGame.setWordsFromDataSource(datasource.getWordsFromPackedDataSource());
		
		//selects a word between all the words of the faked Datasource
		lingoGame.selectAWordFromTheFakedDataSource();
		
		//displays some of the letters of the selected word in the right order
		//i.e for the word "TUTORIAL", the display could be "T.T....."
		lingoGame.initLayout();
		
		//looping for the number of chances of the player
		for(int i=0; i<lingoGame.getNumberOfChanges();i++) {
			//wait for the user to seize his answer
			lingoGame.waitForAnswer();
			
			//checks whether the answer has correctly been seized
			//if a word is well located, it will appear in capital letter
			//if not, it will be in small letter 
			if(lingoGame.checkAnswer()) {
				
				//display "the game has been won"
				lingoGame.theGameIsWon();
				
				//reintantiates all the variables
				lingoGame.stopGame();
			}
		}
		

		//display "the game has been lost"
		lingoGame.theGameIsLost();
		
		//reintantiates all the variables
		lingoGame.stopGame();
	}
}
