package com.blueliv.command;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blueliv.command.exception.CommandFormatException;
import com.blueliv.model.Coordinates;
import com.blueliv.model.Orientation;

public class RoverCreationCommand extends Command {
	private static final Pattern commandPattern = Pattern.compile("(\\d+)\\s(\\d+)\\s([NESW])");
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
