package com.blueliv.controller.command;

import com.blueliv.controller.command.exception.CommandFormatException;

/**
 * A CommandFactory creates and initializes new Command objects by parsing
 * String representations of them.
 * 
 * @author Jaume Paternoy
 *
 */
public abstract class CommandFactory {
	/**
	 * Creates a new command.
	 * 
	 * @param command
	 * @return Newly created command from specified command string argument
	 * @throws CommandFormatException
	 */
	public abstract Command parseCommand(String command) throws CommandFormatException;

	/**
	 * This method must provides the key to register this CommandFactory in
	 * CommandFactoryProvider
	 * 
	 * @return the class of Command this factory can create.
	 */
	public abstract Class<? extends Command> getFactoryClass();
}
