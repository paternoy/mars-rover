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
import com.blueliv.controller.command.RoverInstructionsCommand;
import com.blueliv.controller.command.RoverInstructionsCommand.RoverInstructionsCommandFactory;
import com.blueliv.controller.command.exception.CommandFormatException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestConfiguration.class)
public class RoverInstructionsCommandFactoryTest {

	private static final String EMPTY_COMMAND = "";
	private static final String RIGHT_COMMAND = "MMRLRLM";
	private static final String WRONG_COMMAND = "MARLRLM";
	@Autowired
	RoverInstructionsCommandFactory factory;

	@Test
	public void testRightCommandOK() {
		try {
			RoverInstructionsCommand command = (RoverInstructionsCommand) factory.parseCommand(RIGHT_COMMAND);
			assertEquals(7, command.getInstructions().length);
		} catch (CommandFormatException e) {
			fail("CommandFormatException not expected");
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

	@Test
	public void testWrongCommandKO() {
		try {
			Command command = factory.parseCommand(WRONG_COMMAND);
			fail("Method call should should throw a CommandFormatException");
		} catch (CommandFormatException e) {
		}
	}

}
