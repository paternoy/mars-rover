package com.blueliv.controller.command;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blueliv.controller.command.exception.CommandFormatException;

/**
 * This class is a representation actual expected command for this application.
 * This command is composite with one PlateauCreationCommand followed by one or
 * more pairs of RoverCreationCommand and RoverInstructionsCommand.
 * 
 * @author Jaume Paternoy
 *
 */
public class CompositeCommand extends Command {
	PlateauCreationCommand plateauCreationCommand;
	List<RoverCreationCommand> roverCreationCommands = new ArrayList<>();
	List<RoverInstructionsCommand> instructionsCommands = new ArrayList<>();

	public PlateauCreationCommand getPlateauCreationCommand() {
		return plateauCreationCommand;
	}

	public void setPlateauCreationCommand(PlateauCreationCommand plateauCreationCommand) {
		this.plateauCreationCommand = plateauCreationCommand;
	}

	public List<RoverCreationCommand> getRoverCreationCommands() {
		return roverCreationCommands;
	}

	public void setRoverCreationCommands(List<RoverCreationCommand> roverCreationCommands) {
		this.roverCreationCommands = roverCreationCommands;
	}

	public List<RoverInstructionsCommand> getInstructionsCommands() {
		return instructionsCommands;
	}

	public void setInstructionCommands(List<RoverInstructionsCommand> instructionCommands) {
		this.instructionsCommands = instructionCommands;
	}

	@Component
	public static class CompositeCommandFactory extends CommandFactory {

		@Autowired
		CommandFactoryProvider commandFactoryProvider;

		@Override
		public Command parseCommand(String command) throws CommandFormatException {
			String[] commands = command.split("\n");
			if (commands.length < 3 || commands.length % 2 == 0) {
				throw new CommandFormatException("Wrong number of input lines, must be odd");
			}
			CompositeCommand result = new CompositeCommand();
			PlateauCreationCommand plateauCreationCommand = (PlateauCreationCommand) commandFactoryProvider
					.parseCommand(PlateauCreationCommand.class, commands[0]);
			result.setPlateauCreationCommand(plateauCreationCommand);
			int i = 1;
			while (i < commands.length - 1) {
				RoverCreationCommand roverCreationCommand = (RoverCreationCommand) commandFactoryProvider
						.parseCommand(RoverCreationCommand.class, commands[i]);
				result.getRoverCreationCommands().add(roverCreationCommand);
				RoverInstructionsCommand roverInstructionsCommand = (RoverInstructionsCommand) commandFactoryProvider
						.parseCommand(RoverInstructionsCommand.class, commands[i + 1]);
				result.getInstructionsCommands().add(roverInstructionsCommand);
				i += 2;
			}
			return result;
		}

		@Override
		public Class<? extends Command> getFactoryClass() {
			return CompositeCommand.class;
		}

	}
}
