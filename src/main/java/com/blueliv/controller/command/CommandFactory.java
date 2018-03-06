package com.blueliv.controller.command;

import com.blueliv.controller.command.exception.CommandFormatException;

public abstract class CommandFactory{
	public abstract Command parseCommand(String command) throws CommandFormatException;
	public abstract Class<? extends Command> getFactoryClass();
}
