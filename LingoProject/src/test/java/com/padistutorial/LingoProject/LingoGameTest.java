package com.padistutorial.LingoProject;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.padistutorial.LingoProject.classes.LingoGame;

public class LingoGameTest {

	ApplicationContext ctx;
	
	@Before
	public void setUp()  {
		ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
	}

	@Test
	public void test(){
		LingoGame lingoGame = (LingoGame) ctx.getBean("myLingoGame");
		lingoGame.startGame();
	}
}
