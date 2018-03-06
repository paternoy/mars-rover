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
import com.blueliv.controller.command.RoverCreationCommand;
import com.blueliv.controller.command.RoverCreationCommand.RoverCreationCommandFactory;
import com.blueliv.controller.command.exception.CommandFormatException;
import com.blueliv.model.Orientation;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestConfiguration.class)
public class RoverCreationCommandFactoryTest {

	private static final String EMPTY_COMMAND = "";
	private static final String RIGHT_COMMAND = "1 3 N";
	private static final String INCOMPLETE_COMMAND = "1 3";
	private static final String WRONG_ORIENTATION_COMMAND = "1 3 R";
	private static final String NEGATIVE_COMMAND = "-5 2 N";
	@Autowired
	RoverCreationCommandFactory factory;

	@Test
	public void testRightCommandOK() {
		try {
			RoverCreationCommand command = (RoverCreationCommand) factory.parseCommand(RIGHT_COMMAND);
			assertEquals(1, command.getPosition().getX());
			assertEquals(3, command.getPosition().getY());
			assertEquals(Orientation.N, command.getOrientation());
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
	public void testIncompleteCommandKO() {
		try {
			Command command = factory.parseCommand(INCOMPLETE_COMMAND);
			fail("Method call should should throw a CommandFormatException");
		} catch (CommandFormatException e) {
		}
	}

	@Test
	public void testWrongOrientationCommandKO() {
		try {
			Command command = factory.parseCommand(WRONG_ORIENTATION_COMMAND);
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
