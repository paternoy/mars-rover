package com.blueliv.command;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.blueliv.command.exception.CommandFormatException;

public class PlateauCreationCommand extends Command{
	private static final Pattern commandPattern = Pattern.compile("(\\d+)\\s(\\d+)");
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

	public static PlateauCreationCommand parseCommand(String command) throws CommandFormatException {
		Matcher matcher = commandPattern.matcher(command);
		if(!matcher.matches()) {
			throw new CommandFormatException(String.format("Wrong plateau creation format for \"%s\"", command));
		}
		
		int x = Integer.parseInt(matcher.group(1));
		int y = Integer.parseInt(matcher.group(2));
		return new PlateauCreationCommand(x,y);
	}
}
