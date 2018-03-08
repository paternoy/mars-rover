package com.blueliv.controller.command;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blueliv.controller.command.exception.CommandFormatException;

/**
 * This class is a representation of the Plateau creation command. This command
 * has the following syntax:
 * "X Y"
 * where X and Y are two positive integers that
 * define Plateau's dimensions.
 * 
 * @author Jaume Paternoy
 *
 */
public class PlateauCreationCommand extends Command {
	int x;
	int y;

	public PlateauCreationCommand(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Component
	public static class PlateauCreationCommandFactory extends CommandFactory {
		private static final Pattern commandPattern = Pattern.compile("(\\d+)\\s(\\d+)");

		@Autowired
		CommandFactoryProvider commandFactoryProvider;

		@Override
		public Command parseCommand(String command) throws CommandFormatException {
			Matcher matcher = commandPattern.matcher(command);
			if (!matcher.matches()) {
				throw new CommandFormatException(String.format("Wrong plateau creation command for \"%s\"", command));
			}

			int x = Integer.parseInt(matcher.group(1));
			int y = Integer.parseInt(matcher.group(2));
			return new PlateauCreationCommand(x, y);
		}

		@Override
		public Class<? extends Command> getFactoryClass() {
			return PlateauCreationCommand.class;
		}

	}
}
