package com.blueliv.controller.command;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blueliv.controller.command.exception.CommandFormatException;
import com.blueliv.model.Coordinates;
import com.blueliv.model.Orientation;

/**
 * This class is a representation of the Rover creation command. This command
 * has the following syntax: 
 * "X Y O"
 * where X and Y are two positive integers for the Rover's initial position within the Plateau, and 'O' is the cardinal
 * orientation.
 * 
 * @author Jaume Paternoy
 *
 */
public class RoverCreationCommand extends Command {
	Coordinates position;
	Orientation orientation;

	public RoverCreationCommand(int x, int y, Orientation orientation) {
		super();
		this.position = new Coordinates(x, y);
		this.orientation = orientation;
	}

	public Coordinates getPosition() {
		return position;
	}

	public Orientation getOrientation() {
		return orientation;
	}

	@Component
	public static class RoverCreationCommandFactory extends CommandFactory {
		private static final Pattern commandPattern = Pattern.compile("(\\d+)\\s(\\d+)\\s([NESW])");

		@Autowired
		CommandFactoryProvider commandFactoryProvider;

		@Override
		public Command parseCommand(String command) throws CommandFormatException {
			Matcher matcher = commandPattern.matcher(command);
			if (!matcher.matches()) {
				throw new CommandFormatException(String.format("Wrong rover creation format for \"%s\"", command));
			}

			int x = Integer.parseInt(matcher.group(1));
			int y = Integer.parseInt(matcher.group(2));
			Orientation orientation = Orientation.valueOf(matcher.group(3));
			return new RoverCreationCommand(x, y, orientation);
		}

		@Override
		public Class<? extends Command> getFactoryClass() {
			return RoverCreationCommand.class;
		}

	}
}
