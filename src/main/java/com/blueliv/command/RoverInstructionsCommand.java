package com.blueliv.command;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blueliv.command.exception.CommandFormatException;
import com.blueliv.model.Instruction;

public class RoverInstructionsCommand extends Command {
	Instruction[] instructions;

	public RoverInstructionsCommand(Instruction[] instructions) {
		super();
		this.instructions = instructions;
	}

	public Instruction[] getInstructions() {
		return instructions;
	}

	@Component
	public static class RoverInstructionsCommandFactory extends CommandFactory {
		private static final Pattern commandPattern = Pattern.compile("([MRL])+");

		@Autowired
		CommandFactoryProvider commandFactoryProvider;

		@Override
		public Command parseCommand(String command) throws CommandFormatException {
			Matcher matcher = commandPattern.matcher(command);
			if (!matcher.matches()) {
				throw new CommandFormatException(String.format("Wrong rover instruction format for \"%s\"", command));
			}
			Instruction[] instructionArray = new Instruction[command.length()];
			for (int i = 0; i < instructionArray.length; i++) {
				instructionArray[i] = Instruction.valueOf(String.valueOf(command.charAt(i)));

			}
			return new RoverInstructionsCommand(instructionArray);
		}

		@Override
		public Class<? extends Command> getFactoryClass() {
			return RoverInstructionsCommand.class;
		}

	}
}
