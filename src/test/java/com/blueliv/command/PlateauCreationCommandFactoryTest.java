package com.blueliv.command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.blueliv.TestConfiguration;
import com.blueliv.controller.command.Command;
import com.blueliv.controller.command.PlateauCreationCommand;
import com.blueliv.controller.command.PlateauCreationCommand.PlateauCreationCommandFactory;
import com.blueliv.controller.command.exception.CommandFormatException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestConfiguration.class)
public class PlateauCreationCommandFactoryTest {

	private static final String EMPTY_COMMAND = "";
	private static final String LONG_COMMAND = "5 2 3";
	private static final String RIGHT_COMMAND = "5 2";
	private static final String NEGATIVE_COMMAND = "-5 2";
	@Autowired
	PlateauCreationCommandFactory factory;

	@Test
	public void testRightCommandOK() {
		try {
			PlateauCreationCommand command = (PlateauCreationCommand) factory.parseCommand(RIGHT_COMMAND);
			assertEquals(5, command.getX());
			assertEquals(2, command.getY());
		} catch (CommandFormatException e) {
			fail("CommandFormatException not expected");
		}
	}

	@Test
	public void testNegativeCommandKO() {
		try {
			Command command = factory.parseCommand(NEGATIVE_COMMAND);
			fail("Method call should should throw a CommandFormatException");
		} catch (CommandFormatException e) {
		}
	}

	@Test
	public void testLongCommandKO() {
		try {
			Command command = factory.parseCommand(LONG_COMMAND);
			fail("Method call should should throw a CommandFormatException");
		} catch (CommandFormatException e) {
		}
	}

	@Test
	public void testEmptyCommandKO() {
		try {
			Command command = factory.parseCommand(EMPTY_COMMAND);
			fail("Method call should should throw a CommandFormatException");
		} catch (CommandFormatException e) {
		}
	}

}
