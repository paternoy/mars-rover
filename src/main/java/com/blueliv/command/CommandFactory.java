package com.blueliv.command;

import com.blueliv.command.exception.CommandFormatException;

public abstract class CommandFactory{
	public abstract Command parseCommand(String command) throws CommandFormatException;
	public abstract Class<? extends Command> getFactoryClass();
}
